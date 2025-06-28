import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Administrador extends Usuario implements interfazAnunciar {

    public Administrador(String id, String nombre, String usuario, String contraseña) {
        super(id, nombre, usuario,contraseña);
    }

    public void cancelarCitas(ArrayList<Cita> citas,String idCita) {
        int indice = -1;
        for (int i = 0; i < citas.size(); i++) {
            if(citas.get(i).getId().equals(idCita)){
                indice = i;
                citas.get(i).getNumImpresora().setDisponible();
            }
        }
        if (indice >= 0){
            citas.remove(indice);
            System.out.println("La cita ha sido cancelada exitosamente.");
        }else{
            System.out.println("Esta cita no puede ser cancelada.");
        }
        
    }
    public void cambiarDispo(ArrayList<Impresora> listaImpresoras,String idImpresora){
        boolean hecho= false;
        for(int i = 0; i < listaImpresoras.size(); i++) {
            if(listaImpresoras.get(i).getId().equals(idImpresora)){
                System.out.println("cambio hecho!");
                listaImpresoras.get(i).setDisponible();
                hecho=true;
            }
        }
        if(hecho==false){
            System.out.println("impresora no encontrada");
        }
            
    }
    public void anunciar() {
    }
    public void cambiarTope(int actualizarTope){
        Usuario.tope=actualizarTope;
    }
    public void agregarImpresora(Scanner sc,  List<Impresora> impresoras){
        System.out.println(impresoras);
        
        System.out.println("Ingrese el Id de la nueva impresora:");
        String id = sc.nextLine();
                
        System.out.println("Ingrese su capacidad de citas:");
        int cantidad = sc.nextInt();
        sc.nextLine(); // limpiar salto

        Impresora nuevaImpresora = new Impresora(id, cantidad);

        impresoras.add(nuevaImpresora);


    }
    
}
