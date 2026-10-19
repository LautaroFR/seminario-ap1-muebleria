package util;
import java.sql.*;
public final class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/muebleria?connectionTimeZone=SERVER";
    private Conexion() {}
    public static Connection conectar() throws SQLException {
        String password = System.getenv("MUEBLERIA_DB_PASSWORD");
        if (password == null || password.isEmpty()) throw new SQLException("Configura MUEBLERIA_DB_PASSWORD en Git Bash");
        return DriverManager.getConnection(URL, "muebleria_app", password);
    }
}
