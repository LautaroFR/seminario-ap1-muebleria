package util;
import java.sql.*;
public class PruebaConexion {
    public static void main(String[] args) {
        try (Connection c = Conexion.conectar(); Statement s = c.createStatement(); ResultSet r = s.executeQuery("SELECT DATABASE()")) {
            if (r.next()) System.out.println("Conexion exitosa con MySQL. Base: " + r.getString(1));
        } catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }
}
