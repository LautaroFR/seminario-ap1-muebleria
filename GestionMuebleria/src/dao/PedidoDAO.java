package dao;
import modelo.*;
import util.Conexion;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
public class PedidoDAO {
    private final ClienteDAO clientes=new ClienteDAO();
    public Pedido registrar(int clienteId,String descripcion,BigDecimal precio) throws SQLException {
        Cliente cliente=clientes.buscar(clienteId);
        new Pedido(1,cliente,descripcion,precio);
        String sql="INSERT INTO pedido(id_cliente,fecha,descripcion,precio_total,estado) VALUES (?,CURRENT_DATE(),?,?,?)";
        try(Connection c=Conexion.conectar();PreparedStatement ps=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,clienteId);ps.setString(2,descripcion);ps.setBigDecimal(3,precio);ps.setString(4,"Presupuesto");ps.executeUpdate();
            try(ResultSet r=ps.getGeneratedKeys()){
                if(r.next())return buscar(r.getInt(1));
            }throw new SQLException("No se genero ID de pedido");
        }
    }
    private static Pedido.Estado estado(String s) throws SQLException {
        return switch(s){
            case "Presupuesto" -> Pedido.Estado.PRESUPUESTO;
            case "Confirmado" -> Pedido.Estado.CONFIRMADO;
            case "En producción", "En produccion" -> Pedido.Estado.EN_PRODUCCION;
            case "Terminado" -> Pedido.Estado.TERMINADO;
            case "Entregado" -> Pedido.Estado.ENTREGADO;
            default -> throw new SQLException("Estado desconocido en BD: "+s);
        };
    }
    private static String textoEstado(Pedido.Estado e){return switch(e){
        case PRESUPUESTO -> "Presupuesto";case CONFIRMADO -> "Confirmado";
        case EN_PRODUCCION -> "En producción";case TERMINADO -> "Terminado";case ENTREGADO -> "Entregado";};}
    private Pedido cargar(ResultSet r) throws SQLException {
        int id=r.getInt("id_pedido");
        Pedido p=new Pedido(id,clientes.buscar(r.getInt("id_cliente")),r.getString("descripcion"),r.getBigDecimal("precio_total"));
        p.setEstado(estado(r.getString("estado")));
        try(Connection c=Conexion.conectar();PreparedStatement ps=c.prepareStatement("SELECT id_pago,importe FROM pago WHERE id_pedido=? ORDER BY id_pago")){
            ps.setInt(1,id);try(ResultSet pagos=ps.executeQuery()){
                while(pagos.next())p.agregarPago(new Pago(pagos.getInt(1),pagos.getBigDecimal(2)));
            }
        }return p;
    }
    public Pedido buscar(int id) throws SQLException {
        try(Connection c=Conexion.conectar();PreparedStatement ps=c.prepareStatement("SELECT * FROM pedido WHERE id_pedido=?")){
            ps.setInt(1,id);try(ResultSet r=ps.executeQuery()){
                if(r.next())return cargar(r);
            }
        }throw new NoSuchElementException("Pedido inexistente: "+id);
    }
    public List<Pedido> listar() throws SQLException {
        List<Pedido> lista=new ArrayList<>();
        try(Connection c=Conexion.conectar();PreparedStatement ps=c.prepareStatement("SELECT id_pedido FROM pedido ORDER BY id_pedido");ResultSet r=ps.executeQuery()){
            while(r.next())lista.add(buscar(r.getInt(1)));
        }return lista;
    }
    public void actualizarEstado(int id,Pedido.Estado e) throws SQLException {
        if(e==null)throw new IllegalArgumentException("Estado invalido");
        try(Connection c=Conexion.conectar();PreparedStatement ps=c.prepareStatement("UPDATE pedido SET estado=?,fecha_entrega=CASE WHEN ?='Entregado' THEN COALESCE(fecha_entrega,CURRENT_DATE()) ELSE fecha_entrega END WHERE id_pedido=?")){
            String valor=textoEstado(e);ps.setString(1,valor);ps.setString(2,valor);ps.setInt(3,id);
            if(ps.executeUpdate()==0)throw new NoSuchElementException("Pedido inexistente: "+id);
        }
    }
}
