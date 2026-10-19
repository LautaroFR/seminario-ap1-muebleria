package dao;
import modelo.Cliente;
import util.Conexion;
import java.sql.*;
import java.util.*;
public class ClienteDAO {
    public Cliente registrar(String nombre, String telefono) throws SQLException {
        new Cliente(1, nombre, telefono); // valida antes de insertar
        String sql = "INSERT INTO cliente(nombre,telefono) VALUES (?,?)";
        try (Connection c=Conexion.conectar(); PreparedStatement ps=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,nombre.trim()); ps.setString(2,telefono); ps.executeUpdate();
            try(ResultSet r=ps.getGeneratedKeys()){if(r.next())return new Cliente(r.getInt(1),nombre,telefono);}
            throw new SQLException("No se genero ID de cliente");
        }
    }
    public List<Cliente> listar() throws SQLException {
        List<Cliente> lista=new ArrayList<>();
        try(Connection c=Conexion.conectar(); PreparedStatement ps=c.prepareStatement("SELECT id_cliente,nombre,telefono FROM cliente ORDER BY nombre,id_cliente");ResultSet r=ps.executeQuery()) {
            while(r.next())lista.add(new Cliente(r.getInt(1),r.getString(2),r.getString(3)));
        }return lista;
    }
    public Cliente buscar(int id) throws SQLException {
        try(Connection c=Conexion.conectar();PreparedStatement ps=c.prepareStatement("SELECT id_cliente,nombre,telefono FROM cliente WHERE id_cliente=?")){
            ps.setInt(1,id);try(ResultSet r=ps.executeQuery()){
                if(r.next())return new Cliente(r.getInt(1),r.getString(2),r.getString(3));
            }
        }throw new NoSuchElementException("Cliente inexistente: "+id);
    }
}
