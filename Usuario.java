
import java.util.ArrayList;

public class Usuario {
    private String id;
    private String nombre;
    private String usuario;
    private boolean citaAgendada;
    private String contraseña;
    private Cita proximaCita;

    // Constructor
    public Usuario(String id, String nombre, String usuario, String contraseña) {

        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.citaAgendada = false;
        this.contraseña=contraseña;
        
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
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

    public void consultar(ArrayList<Cita> citas) {
        for(Cita cita: citas){
            System.out.println(cita);
        }
        
    }

    public void agendar() {
        System.out.println("prueba agendar");
    }
}
