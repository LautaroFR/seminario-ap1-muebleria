package controlador;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import modelo.*;
public class Gestion {
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Pedido> pedidos = new ArrayList<>();
    private int siguienteCliente = 1, siguientePedido = 1, siguientePago = 1;
    public Cliente registrarCliente(String nombre, String telefono) {
        Cliente c = new Cliente(siguienteCliente++, nombre, telefono); clientes.add(c); return c;
    }
    public List<Cliente> listarClientes() {
        return clientes.stream().sorted(Comparator.comparing(Cliente::getNombre, String.CASE_INSENSITIVE_ORDER)).toList();
    }
    public Cliente buscarCliente(int id) {
        return clientes.stream().filter(c -> c.getId() == id).findFirst().orElseThrow(() -> new NoSuchElementException("Cliente inexistente"));
    }
    public Pedido registrarPedido(int clienteId, String descripcion, BigDecimal precio) {
        Pedido p = new Pedido(siguientePedido++, buscarCliente(clienteId), descripcion, precio); pedidos.add(p); return p;
    }
    public List<Pedido> listarPedidos() { return List.copyOf(pedidos); }
    public Pedido buscarPedido(int id) {
        return pedidos.stream().filter(p -> p.getId() == id).findFirst().orElseThrow(() -> new NoSuchElementException("Pedido inexistente"));
    }
    public Pago registrarPago(int pedidoId, BigDecimal importe) {
        Pedido pedido = buscarPedido(pedidoId);
        Pago pago = new Pago(siguientePago, importe);
        pedido.agregarPago(pago); siguientePago++; return pago;
    }
    public void actualizarEstado(int pedidoId, Pedido.Estado estado) { buscarPedido(pedidoId).setEstado(estado); }
}
