package modelo;
public class Empleado extends Persona {
    private final String cargo;
    public Empleado(int id, String nombre, String cargo) { super(id, nombre); this.cargo = cargo; }
    @Override public String mostrarInformacion() { return "Empleado #" + getId() + " - " + getNombre() + " (" + cargo + ")"; }
}
