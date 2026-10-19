package modelo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Pedido {
    public enum Estado { PRESUPUESTO, CONFIRMADO, EN_PRODUCCION, TERMINADO, ENTREGADO }
    private final int id;
    private final Cliente cliente;
    private final String descripcion;
    private final BigDecimal precioTotal;
    private Estado estado = Estado.PRESUPUESTO;
    private final List<Pago> pagos = new ArrayList<>();
    public Pedido(int id, Cliente cliente, String descripcion, BigDecimal precioTotal) {
        if (cliente == null || descripcion == null || descripcion.isBlank() || precioTotal == null || precioTotal.signum() <= 0)
            throw new IllegalArgumentException("Datos del pedido invalidos");
        this.id = id; this.cliente = cliente; this.descripcion = descripcion; this.precioTotal = precioTotal;
    }
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public String getDescripcion() { return descripcion; }
    public BigDecimal getPrecioTotal() { return precioTotal; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) {
        if (estado == null) throw new IllegalArgumentException("Estado invalido");
        this.estado = estado;
    }
    public List<Pago> getPagos() { return Collections.unmodifiableList(pagos); }
    public BigDecimal getTotalPagado() {
        return pagos.stream().map(Pago::getImporte).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public BigDecimal getSaldo() { return precioTotal.subtract(getTotalPagado()); }
    public void agregarPago(Pago pago) {
        if (pago == null || pago.getImporte().compareTo(getSaldo()) > 0)
            throw new IllegalArgumentException("El pago excede el saldo pendiente");
        pagos.add(pago);
    }
    @Override public String toString() {
        return "Pedido #" + id + " | " + cliente.getNombre() + " | " + descripcion + " | Total: $" + precioTotal +
            " | Pagado: $" + getTotalPagado() + " | Saldo: $" + getSaldo() + " | " + estado;
    }
}
