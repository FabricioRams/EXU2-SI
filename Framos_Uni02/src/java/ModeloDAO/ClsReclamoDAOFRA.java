package ModeloDAO;

import Config.ClsConexionFRA;
import Interfaces.CRUDReclamoFRA;
import Modelo.ClsCategoriaFRA;
import Modelo.ClsReclamoFRA;
import Modelo.ClsResumenFRA;
import Modelo.ClsSeguimientoFRA;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mi Equipo
 * Implementa las operaciones para la gestión de reclamos del sistema FRA.
 */
    
public class ClsReclamoDAOFRA implements CRUDReclamoFRA {

    private static final String SQL_LISTAR_ADMIN = "SELECT r.id_reclamo, r.codigo_reclamo, r.asunto, r.descripcion, r.fecha_registro, "
            + "r.fecha_actualizacion, r.estado, r.id_usuario, r.id_categoria, u.nombres, u.apellidos, c.nombre_categoria "
            + "FROM fra_reclamo r INNER JOIN fra_usuario u ON r.id_usuario = u.id_usuario "
            + "INNER JOIN fra_categoria c ON r.id_categoria = c.id_categoria ORDER BY r.fecha_registro DESC";

    private static final String SQL_LISTAR_USUARIO = "SELECT r.id_reclamo, r.codigo_reclamo, r.asunto, r.descripcion, r.fecha_registro, "
            + "r.fecha_actualizacion, r.estado, r.id_usuario, r.id_categoria, c.nombre_categoria "
            + "FROM fra_reclamo r INNER JOIN fra_categoria c ON r.id_categoria = c.id_categoria "
            + "WHERE r.id_usuario = ? ORDER BY r.fecha_registro DESC";

    private static final String SQL_OBTENER = "SELECT r.id_reclamo, r.codigo_reclamo, r.asunto, r.descripcion, r.fecha_registro, "
            + "r.fecha_actualizacion, r.estado, r.id_usuario, r.id_categoria, u.nombres, u.apellidos, c.nombre_categoria "
            + "FROM fra_reclamo r INNER JOIN fra_usuario u ON r.id_usuario = u.id_usuario "
            + "INNER JOIN fra_categoria c ON r.id_categoria = c.id_categoria WHERE r.id_reclamo = ?";

    private static final String SQL_INSERTAR = "INSERT INTO fra_reclamo(codigo_reclamo, asunto, descripcion, fecha_registro, fecha_actualizacion, estado, id_usuario, id_categoria) "
            + "VALUES(?,?,?,?,?,?,?,?)";

    private static final String SQL_ACTUALIZAR = "UPDATE fra_reclamo SET asunto = ?, descripcion = ?, id_categoria = ? WHERE id_reclamo = ?";

    private static final String SQL_SEGUIMIENTO_INSERT = "INSERT INTO fra_seguimiento(id_reclamo, fecha_movimiento, estado, comentario, usuario_registro) "
            + "VALUES(?,?,?,?,?)";

    private static final String SQL_SEGUIMIENTO_LISTAR = "SELECT id_seguimiento, id_reclamo, fecha_movimiento, estado, comentario, usuario_registro "
            + "FROM fra_seguimiento WHERE id_reclamo = ? ORDER BY fecha_movimiento DESC";

    private static final String SQL_ACTUALIZAR_ESTADO = "UPDATE fra_reclamo SET estado = ?, fecha_actualizacion = ? WHERE id_reclamo = ?";

    private static final String SQL_CATEGORIAS = "SELECT id_categoria, nombre_categoria, descripcion, estado FROM fra_categoria WHERE estado = 1 ORDER BY nombre_categoria";

    private static final String SQL_RESUMEN = "SELECT "
            + "(SELECT COUNT(*) FROM fra_reclamo) AS total, "
            + "(SELECT COUNT(*) FROM fra_reclamo WHERE estado = 'Pendiente') AS pendientes, "
            + "(SELECT COUNT(*) FROM fra_reclamo WHERE estado = 'En Proceso') AS en_proceso, "
            + "(SELECT COUNT(*) FROM fra_reclamo WHERE estado = 'Resuelto') AS resueltos, "
            + "(SELECT COUNT(*) FROM fra_reclamo WHERE estado = 'Cerrado') AS cerrados";

    private final ClsConexionFRA conexion = new ClsConexionFRA();

    public List<ClsReclamoFRA> listarParaAdministracion() {
        List<ClsReclamoFRA> reclamos = new ArrayList<>();
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_LISTAR_ADMIN);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                reclamos.add(mapearReclamo(rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible listar los reclamos FRA", ex);
        }
        return reclamos;
    }

    public List<ClsReclamoFRA> listarPorUsuario(int idUsuario) {
        List<ClsReclamoFRA> reclamos = new ArrayList<>();
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_LISTAR_USUARIO)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClsReclamoFRA reclamo = new ClsReclamoFRA();
                    reclamo.setIdReclamo(rs.getInt("id_reclamo"));
                    reclamo.setCodigoReclamo(rs.getString("codigo_reclamo"));
                    reclamo.setAsunto(rs.getString("asunto"));
                    reclamo.setDescripcion(rs.getString("descripcion"));
                    reclamo.setFechaRegistro(rs.getDate("fecha_registro"));
                    reclamo.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
                    reclamo.setEstado(rs.getString("estado"));
                    reclamo.setIdUsuario(rs.getInt("id_usuario"));
                    reclamo.setIdCategoria(rs.getInt("id_categoria"));
                    reclamo.setNombreCategoria(rs.getString("nombre_categoria"));
                    reclamos.add(reclamo);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible listar los reclamos del usuario FRA", ex);
        }
        return reclamos;
    }

    public ClsReclamoFRA obtenerPorId(int idReclamo) {
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_OBTENER)) {
            ps.setInt(1, idReclamo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearReclamo(rs);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible obtener la información del reclamo FRA", ex);
        }
        return null;
    }

    public boolean registrar(ClsReclamoFRA reclamo) {
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_INSERTAR)) {
            ps.setString(1, reclamo.getCodigoReclamo());
            ps.setString(2, reclamo.getAsunto());
            ps.setString(3, reclamo.getDescripcion());
            ps.setDate(4, reclamo.getFechaRegistro());
            ps.setDate(5, reclamo.getFechaActualizacion());
            ps.setString(6, reclamo.getEstado());
            ps.setInt(7, reclamo.getIdUsuario());
            ps.setInt(8, reclamo.getIdCategoria());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible registrar el reclamo FRA", ex);
        }
    }

    public boolean actualizar(ClsReclamoFRA reclamo) {
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setString(1, reclamo.getAsunto());
            ps.setString(2, reclamo.getDescripcion());
            ps.setInt(3, reclamo.getIdCategoria());
            ps.setInt(4, reclamo.getIdReclamo());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible actualizar el reclamo FRA", ex);
        }
    }

    public boolean registrarSeguimiento(ClsSeguimientoFRA seguimiento) {
        try (Connection cn = conexion.getConnection()) {
            cn.setAutoCommit(false);
            try (PreparedStatement psSeguimiento = cn.prepareStatement(SQL_SEGUIMIENTO_INSERT);
                    PreparedStatement psEstado = cn.prepareStatement(SQL_ACTUALIZAR_ESTADO)) {

                Timestamp ahora = seguimiento.getFechaMovimiento() != null
                        ? seguimiento.getFechaMovimiento()
                        : Timestamp.valueOf(java.time.LocalDateTime.now());

                psSeguimiento.setInt(1, seguimiento.getIdReclamo());
                psSeguimiento.setTimestamp(2, ahora);
                psSeguimiento.setString(3, seguimiento.getEstado());
                psSeguimiento.setString(4, seguimiento.getComentario());
                psSeguimiento.setString(5, seguimiento.getUsuarioRegistro());
                psSeguimiento.executeUpdate();

                psEstado.setString(1, seguimiento.getEstado());
                psEstado.setDate(2, java.sql.Date.valueOf(ahora.toLocalDateTime().toLocalDate()));
                psEstado.setInt(3, seguimiento.getIdReclamo());
                psEstado.executeUpdate();

                cn.commit();
                return true;
            } catch (SQLException ex) {
                cn.rollback();
                throw ex;
            } finally {
                cn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible registrar el seguimiento FRA", ex);
        }
    }

    public List<ClsSeguimientoFRA> listarSeguimiento(int idReclamo) {
        List<ClsSeguimientoFRA> seguimientos = new ArrayList<>();
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_SEGUIMIENTO_LISTAR)) {
            ps.setInt(1, idReclamo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClsSeguimientoFRA seguimiento = new ClsSeguimientoFRA();
                    seguimiento.setIdSeguimiento(rs.getInt("id_seguimiento"));
                    seguimiento.setIdReclamo(rs.getInt("id_reclamo"));
                    seguimiento.setFechaMovimiento(rs.getTimestamp("fecha_movimiento"));
                    seguimiento.setEstado(rs.getString("estado"));
                    seguimiento.setComentario(rs.getString("comentario"));
                    seguimiento.setUsuarioRegistro(rs.getString("usuario_registro"));
                    seguimientos.add(seguimiento);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible listar el seguimiento del reclamo FRA", ex);
        }
        return seguimientos;
    }

    public List<ClsCategoriaFRA> listarCategoriasActivas() {
        List<ClsCategoriaFRA> categorias = new ArrayList<>();
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_CATEGORIAS);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ClsCategoriaFRA categoria = new ClsCategoriaFRA();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombreCategoria(rs.getString("nombre_categoria"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setEstado(rs.getBoolean("estado"));
                categorias.add(categoria);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible listar las categorías activas FRA", ex);
        }
        return categorias;
    }

    public ClsResumenFRA obtenerResumen() {
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_RESUMEN);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                ClsResumenFRA resumen = new ClsResumenFRA();
                resumen.setTotalReclamos(rs.getInt("total"));
                resumen.setPendientes(rs.getInt("pendientes"));
                resumen.setEnProceso(rs.getInt("en_proceso"));
                resumen.setResueltos(rs.getInt("resueltos"));
                resumen.setCerrados(rs.getInt("cerrados"));
                return resumen;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible obtener el resumen FRA", ex);
        }
        return new ClsResumenFRA();
    }

    private ClsReclamoFRA mapearReclamo(ResultSet rs) throws SQLException {
        ClsReclamoFRA reclamo = new ClsReclamoFRA();
        reclamo.setIdReclamo(rs.getInt("id_reclamo"));
        reclamo.setCodigoReclamo(rs.getString("codigo_reclamo"));
        reclamo.setAsunto(rs.getString("asunto"));
        reclamo.setDescripcion(rs.getString("descripcion"));
        reclamo.setFechaRegistro(rs.getDate("fecha_registro"));
        reclamo.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
        reclamo.setEstado(rs.getString("estado"));
        reclamo.setIdUsuario(rs.getInt("id_usuario"));
        reclamo.setIdCategoria(rs.getInt("id_categoria"));
        reclamo.setNombreCategoria(rs.getString("nombre_categoria"));
        String nombres = null;
        try {
            nombres = rs.getString("nombres");
        } catch (SQLException ignored) {
        }
        if (nombres != null) {
            reclamo.setNombreUsuario(nombres + " " + rs.getString("apellidos"));
        }
        return reclamo;
    }

    /**
     * Genera un código correlativo simple para los reclamos FRA.
     */
    public String generarCodigoReclamo() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");
        String prefijo = "FRA-" + LocalDate.now().format(formato);
        int correlativo = (int) (Math.random() * 900 + 100);
        return prefijo + "-" + correlativo;
    }
}