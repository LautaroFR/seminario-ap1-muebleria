package controlador;
import java.math.BigDecimal;
import modelo.*;
public class Prueba {
    public static void main(String[] args) {
        Gestion g = new Gestion();
        Cliente c = g.registrarCliente("Ana", "1112345678");
        if (g.listarClientes().size() != 1) throw new AssertionError("Registro cliente");
        Pedido p = g.registrarPedido(c.getId(), "Placard", new BigDecimal("100000"));
        g.registrarPago(p.getId(), new BigDecimal("30000"));
        if (p.getSaldo().compareTo(new BigDecimal("70000")) != 0) throw new AssertionError("Saldo");
        g.actualizarEstado(p.getId(), Pedido.Estado.EN_PRODUCCION);
        if (p.getEstado() != Pedido.Estado.EN_PRODUCCION) throw new AssertionError("Estado");
        boolean rechazo = false;
        try { g.registrarPago(p.getId(), new BigDecimal("80000")); }
        catch (IllegalArgumentException ex) { rechazo = true; }
        if (!rechazo || p.getPagos().size() != 1) throw new AssertionError("Pago excesivo");
        Persona persona = new Empleado(1, "Luis", "Produccion");
        if (!persona.mostrarInformacion().contains("Empleado")) throw new AssertionError("Polimorfismo");
        System.out.println("OK: pruebas de cliente, pedido, pago, saldo, estado, validacion y polimorfismo");
    }
}
