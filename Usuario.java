public class Usuario{
    private int id;
    private String nombre;
    private String usuario;
    private boolean citaDisponible;
    private Cita proximaCita;

    public Usuario(int id,String nombre,String usuario,boolean citaDisponible){
        this.id=id;
        this.nombre=nombre;
        this.usuario=usuario;
        this.boolean=citaDisponible;
        this.Cita=new Cita;
    }
    
    public String getNombre(){
        return nombre;
    }
    public String getUsuario(){
        return usuario;
    }
    public int getId(){
        return id;
    }
    public Cita getProxCita(){
        return proximaCita;
    }

    public void consultar(){

    }

    public void agendar(){
        
    }
}
