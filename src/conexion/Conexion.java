package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con Oracle SQL Plus 10g
 * Driver JDBC: ojdbc14.jar  (agregar al proyecto en NetBeans)
 */
public class Conexion {

    // ── Datos de conexión ──────────────────────────────────────────────────
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL    = "jdbc:oracle:thin:@192.168.254.215:1521:orcl";
    private static final String USUARIO = "parcial2dp";      // Cambia por tu usuario
    private static final String PASSWORD = "parcial2dp";     // Cambia por tu contraseña

    // ── Método para obtener la conexión ────────────────────────────────────
    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver Oracle no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con Oracle: " + e.getMessage());
        }
        return con;
    }

    // ── Método para cerrar la conexión ─────────────────────────────────────
    public static void cerrarConexion(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
