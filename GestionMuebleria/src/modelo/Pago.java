package modelo;
import java.math.BigDecimal;
import java.time.LocalDate;
public class Pago {
    private final int id;
    private final LocalDate fecha;
    private final BigDecimal importe;
    public Pago(int id, BigDecimal importe) {
        if (importe == null || importe.signum() <= 0) throw new IllegalArgumentException("El pago debe ser positivo");
        this.id = id; this.fecha = LocalDate.now(); this.importe = importe;
    }
    public int getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public BigDecimal getImporte() { return importe; }
}
