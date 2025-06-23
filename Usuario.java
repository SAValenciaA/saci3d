public class Usuario {
    private String id;
    private String nombre;
    private String usuario;
    private boolean citaAgendada;
    private Cita proximaCita;

    // Constructor
    public Usuario(String id, String nombre, String usuario) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.citaAgendada = false;
        
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getId() {
        return id;
    }

    public Cita getProxCita() {
        return proximaCita;
    }

    // Métodos funcionales
    public void consultar() {
    }

    public void agendar() {
        
    }
}
