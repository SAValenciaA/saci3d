import java.util.ArrayList;
public class Administrador extends Usuario implements interfazAnunciar {

    public Administrador(String id, String nombre, String usuario, String contraseña) {
        super(id, nombre, usuario,contraseña);
    }

    public void cancelarCitas(ArrayList<Cita> citas,String idCita) {
        
        for (int i = 0; i < citas.size(); i++) {
            if(citas.get(i).getId().equals(idCita)){
                citas.remove(i);
                System.out.println("cita eliminada con exito");
            }
        }
    }
    public void cambiarDispo(){
        
    }
    public void anunciar() {
    }
    public void cambiarTope(){
    }
    public void agregarImpresora(){

    }
}
