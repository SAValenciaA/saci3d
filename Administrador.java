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
                citas.get(i).getNumImpresora().setDisponible(true);
            }
        }

    }
    public void cambiarDispo(Impresora[]listaImpresoras,String idImpresora){
        boolean hecho= false;
        for(int i = 0; i < listaImpresoras.length; i++) {
            if(listaImpresoras[i].getId().equals(idImpresora)){
                System.out.println("cambio hecho!");
                listaImpresoras[i].setDisponible(true);
                hecho=true;
            }
        }
        if(hecho==false){
            System.out.println("impresora no encontrada");
        }
            
    }
    public void anunciar() {
    }
    public void cambiarTope(){
    }
    public void agregarImpresora(){
        
    }
}
