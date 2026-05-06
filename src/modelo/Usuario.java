package modelo;

/**
 * Clase modelo que representa la tabla USUARIO de Oracle
 */
public class Usuario {

    private int    id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    // ── Constructores ──────────────────────────────────────────────────────

    public Usuario() {}

    public Usuario(int id, String nombre, String apellido, String email, String telefono) {
        this.id       = id;
        this.nombre   = nombre;
        this.apellido = apellido;
        this.email    = email;
        this.telefono = telefono;
    }

    // Constructor sin ID (para INSERT, el ID lo genera la secuencia Oracle)
    public Usuario(String nombre, String apellido, String email, String telefono) {
        this.nombre   = nombre;
        this.apellido = apellido;
        this.email    = email;
        this.telefono = telefono;
    }

    // ── Getters y Setters ──────────────────────────────────────────────────

    public int getId()                  { return id; }
    public void setId(int id)           { this.id = id; }

    public String getNombre()           { return nombre; }
    public void setNombre(String n)     { this.nombre = n; }

    public String getApellido()         { return apellido; }
    public void setApellido(String a)   { this.apellido = a; }

    public String getEmail()            { return email; }
    public void setEmail(String e)      { this.email = e; }

    public String getTelefono()         { return telefono; }
    public void setTelefono(String t)   { this.telefono = t; }

    @Override
    public String toString() {
        return id + " - " + nombre + " " + apellido;
    }
}
