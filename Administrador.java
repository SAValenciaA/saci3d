import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;

public class Administrador extends Usuario  {

    //constructor 

    public Administrador(String id, String nombre, String usuario) {
        super(id, nombre, usuario);
        this.rol = "administrador";
    }

    //redundante con la misma funcion en Ususario. TODO: desredundar
    

    /*
     * Esta funcion elimina un elemento de ya sea anuncios o citas
     *
     * @param eleccion Es la tabla de la que se elimina
     * @param id El id del lemento a eliminar
     */
    @Override
    public void cancelar(String eleccion,int id) {

      try{
        Database.delete(eleccion, id);
      } catch(SQLException e) {
        System.out.println(e);
      }

      this.idCitasAgendadas.remove(id);

      // Denuevo, bastante feo, pero funciona
      // Esto busca la cita que se esta eliminando y se elimina
      for(Cita cita: Cita.getCitas()) {
        if(cita.id == id){
          Cita.getCitas().remove(cita);
          return;
        }
      }

      System.out.println("No se encotro la cita");
    }
    
    //TODO: Implement
    public void cambiarDispo(ArrayList<Impresora> listaImpresoras,String idImpresora){
      return;
    }

    //TODO: Implementar
    public void cambiarTopeSemanal(int actualizarTope){
      return;
    }

    //TODO: Implement
    public void cambiarTopeDiario(int actualizarTope){
      return;
    }

}
