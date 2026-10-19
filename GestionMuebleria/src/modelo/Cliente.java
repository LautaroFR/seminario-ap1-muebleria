package modelo;
public class Cliente extends Persona {
    private String telefono;
    public Cliente(int id, String nombre, String telefono) {
        super(id, nombre); this.telefono = telefono == null ? "" : telefono;
    }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono == null ? "" : telefono; }
    @Override public String mostrarInformacion() { return "Cliente #" + getId() + " - " + getNombre() + " - Tel: " + telefono; }
}
