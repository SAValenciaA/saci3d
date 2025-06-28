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
                citas.get(i).getNumImpresora().setDisponible(true);
            }
        }
        if (indice > 0){
            citas.remove(indice);
            System.out.println("cita eliminada con exito");
        }else{
            System.out.println("Esa cita no existe");
        }
        
    }
    public void cambiarDispo(ArrayList<Impresora> listaImpresoras,String idImpresora){
        boolean hecho= false;
        for(int i = 0; i < listaImpresoras.size(); i++) {
            if(listaImpresoras.get(i).getId().equals(idImpresora)){
                System.out.println("cambio hecho!");
                listaImpresoras.get(i).setDisponible(true);
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
