
import java.util.ArrayList;

public class Usuario {
    private String id;
    private String nombre;
    private String usuario;
    private String idCitaAgendada;
    private String contraseña;

    // Constructor
    public Usuario(String id, String nombre, String usuario, String contraseña) {

        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.idCitaAgendada = "000000";
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

    public void consultar(ArrayList<Cita> citas) {
        for(Cita cita: citas){
            System.out.println(cita);
        }
        
    }

    public void agendar() {
        System.out.println("prueba agendar");
    }

    public void cancelar() {
        System.out.println("prueba cancelar");
    }
}
