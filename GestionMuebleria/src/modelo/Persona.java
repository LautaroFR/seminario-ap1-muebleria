package modelo;
public abstract class Persona {
    private final int id;
    private String nombre;
    public Persona(int id, String nombre) {
        if (id <= 0 || nombre == null || nombre.isBlank()) throw new IllegalArgumentException("ID y nombre obligatorios");
        this.id = id; this.nombre = nombre.trim();
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre obligatorio");
        this.nombre = nombre.trim();
    }
    public abstract String mostrarInformacion();
}
