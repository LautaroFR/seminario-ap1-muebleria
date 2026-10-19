package dao;
import modelo.*;
import util.Conexion;
import java.math.BigDecimal;
import java.sql.*;
import java.util.NoSuchElementException;
public class PagoDAO {
    public Pago registrar(int pedidoId,BigDecimal importe) throws SQLException {
        if(importe==null || importe.signum()<=0)throw new IllegalArgumentException("El importe debe ser positivo");
        try(Connection c=Conexion.conectar()){
            c.setAutoCommit(false);
            try {
                BigDecimal precio;
                try(PreparedStatement ps=c.prepareStatement("SELECT precio_total FROM pedido WHERE id_pedido=? FOR UPDATE")){
                    ps.setInt(1,pedidoId);try(ResultSet r=ps.executeQuery()){
                        if(!r.next())throw new NoSuchElementException("Pedido inexistente: "+pedidoId);
                        precio=r.getBigDecimal(1);
                    }
                }
                BigDecimal abonado;
                try(PreparedStatement ps=c.prepareStatement("SELECT COALESCE(SUM(importe),0) FROM pago WHERE id_pedido=?")){
                    ps.setInt(1,pedidoId);try(ResultSet r=ps.executeQuery()){r.next();abonado=r.getBigDecimal(1);}
                }
                if(importe.compareTo(precio.subtract(abonado))>0)throw new IllegalArgumentException("El pago excede el saldo pendiente");
                try(PreparedStatement ps=c.prepareStatement("INSERT INTO pago(id_pedido,fecha,importe) VALUES (?,CURRENT_DATE(),?)",Statement.RETURN_GENERATED_KEYS)){
                    ps.setInt(1,pedidoId);ps.setBigDecimal(2,importe);ps.executeUpdate();
                    try(ResultSet r=ps.getGeneratedKeys()){
                        if(!r.next())throw new SQLException("No se genero ID de pago");
                        Pago pago=new Pago(r.getInt(1),importe);c.commit();return pago;
                    }
                }
            }catch(SQLException|RuntimeException e){c.rollback();throw e;}
        }
    }
}
