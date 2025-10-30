package ModeloDAO;

import Config.ClsConexionFRA;
import Interfaces.CRUDUsuarioFRA;
import Modelo.ClsRolFRA;
import Modelo.ClsUsuarioFRA;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mi Equipo
 * Implementa las operaciones de acceso a datos para la gestión de usuarios FRA.
 */
  

    public class ClsUsuarioDAOFRA implements CRUDUsuarioFRA {

    private static final String SQL_LISTAR = "SELECT u.id_usuario, u.documento, u.nombres, u.apellidos, u.correo, "
            + "u.telefono, u.estado, u.id_rol, r.nombre_rol "
            + "FROM fra_usuario u INNER JOIN fra_rol r ON u.id_rol = r.id_rol ORDER BY u.nombres";

    private static final String SQL_OBTENER = "SELECT id_usuario, documento, nombres, apellidos, correo, telefono, "
            + "contrasena, estado, id_rol FROM fra_usuario WHERE id_usuario = ?";

    private static final String SQL_INSERTAR = "INSERT INTO fra_usuario(documento, nombres, apellidos, correo, telefono, contrasena, estado, id_rol) "
            + "VALUES(?,?,?,?,?,?,?,?)";

    private static final String SQL_ACTUALIZAR = "UPDATE fra_usuario SET documento = ?, nombres = ?, apellidos = ?, correo = ?, telefono = ?, "
            + "contrasena = ?, id_rol = ? WHERE id_usuario = ?";

    private static final String SQL_ESTADO = "UPDATE fra_usuario SET estado = ? WHERE id_usuario = ?";

    private static final String SQL_ROLES = "SELECT id_rol, nombre_rol, descripcion FROM fra_rol ORDER BY nombre_rol";

    private final ClsConexionFRA conexion = new ClsConexionFRA();

    public List<ClsUsuarioFRA> listarUsuarios() {
        List<ClsUsuarioFRA> usuarios = new ArrayList<>();
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_LISTAR);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ClsUsuarioFRA usuario = new ClsUsuarioFRA();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setDocumento(rs.getString("documento"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setEstado(rs.getBoolean("estado"));
                usuario.setIdRol(rs.getInt("id_rol"));
                usuario.setNombreRol(rs.getString("nombre_rol"));
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible listar los usuarios FRA", ex);
        }
        return usuarios;
    }

    public ClsUsuarioFRA obtenerPorId(int idUsuario) {
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_OBTENER)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClsUsuarioFRA usuario = new ClsUsuarioFRA();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setDocumento(rs.getString("documento"));
                    usuario.setNombres(rs.getString("nombres"));
                    usuario.setApellidos(rs.getString("apellidos"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    usuario.setEstado(rs.getBoolean("estado"));
                    usuario.setIdRol(rs.getInt("id_rol"));
                    return usuario;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible recuperar la información del usuario FRA", ex);
        }
        return null;
    }

    public boolean registrar(ClsUsuarioFRA usuario) {
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_INSERTAR)) {
            ps.setString(1, usuario.getDocumento());
            ps.setString(2, usuario.getNombres());
            ps.setString(3, usuario.getApellidos());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getTelefono());
            ps.setString(6, usuario.getContrasena());
            ps.setBoolean(7, usuario.isEstado());
            ps.setInt(8, usuario.getIdRol());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible registrar el nuevo usuario FRA", ex);
        }
    }

    public boolean actualizar(ClsUsuarioFRA usuario) {
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setString(1, usuario.getDocumento());
            ps.setString(2, usuario.getNombres());
            ps.setString(3, usuario.getApellidos());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getTelefono());
            ps.setString(6, usuario.getContrasena());
            ps.setInt(7, usuario.getIdRol());
            ps.setInt(8, usuario.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible actualizar los datos del usuario FRA", ex);
        }
    }

    public boolean actualizarEstado(int idUsuario, boolean activo) {
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_ESTADO)) {
            ps.setBoolean(1, activo);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible actualizar el estado del usuario FRA", ex);
        }
    }

    public List<ClsRolFRA> listarRoles() {
        List<ClsRolFRA> roles = new ArrayList<>();
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_ROLES);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ClsRolFRA rol = new ClsRolFRA();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombreRol(rs.getString("nombre_rol"));
                rol.setDescripcion(rs.getString("descripcion"));
                roles.add(rol);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible recuperar los roles disponibles FRA", ex);
        }
        return roles;
    }
}
