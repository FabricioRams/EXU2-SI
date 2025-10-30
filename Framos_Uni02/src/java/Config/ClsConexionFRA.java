package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mi Equipo
 * Gestiona la conexión con la base de datos para la aplicación FRA.
 */
public class ClsConexionFRA {
    

    private static final String URL = "jdbc:mysql://localhost:3306/fra_reclamos?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String CLAVE = "root";

    /**
     * Obtiene una conexión nueva utilizando el controlador JDBC de MySQL.
     *
     * @return Conexión activa con la base de datos.
     * @throws SQLException si ocurre un problema al cargar el driver o crear la conexión.
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("No se pudo cargar el driver JDBC de MySQL", ex);
        }
    }
}