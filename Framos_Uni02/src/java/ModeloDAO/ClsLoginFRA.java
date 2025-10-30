package ModeloDAO;

import Config.ClsConexionFRA;
import Interfaces.CRUDLoginFRA;
import Modelo.ClsUsuarioFRA;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mi Equipo
 * Implementación del proceso de autenticación para el sistema FRA.
 */
    
public class ClsLoginFRA implements CRUDLoginFRA {

    private static final String SQL_LOGIN = "SELECT u.id_usuario, u.documento, u.nombres, u.apellidos, u.correo, "
            + "u.telefono, u.contrasena, u.estado, u.id_rol, r.nombre_rol "
            + "FROM fra_usuario u INNER JOIN fra_rol r ON u.id_rol = r.id_rol "
            + "WHERE u.correo = ? AND u.contrasena = ? AND u.estado = 1";

    private final ClsConexionFRA conexion = new ClsConexionFRA();

    public ClsUsuarioFRA iniciarSesion(String correo, String contrasena) {
        try (Connection cn = conexion.getConnection();
                PreparedStatement ps = cn.prepareStatement(SQL_LOGIN)) {
            ps.setString(1, correo);
            ps.setString(2, contrasena);
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
                    usuario.setNombreRol(rs.getString("nombre_rol"));
                    return usuario;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No fue posible validar las credenciales del usuario FRA", ex);
        }
        return null;
    }
}