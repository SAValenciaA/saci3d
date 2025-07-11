import java.time.format.DateTimeFormatter;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.io.BufferedReader;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.net.URI;
import java.net.URL;

public class Usuario {

    public String rol = "estudiante";

    public final String id;
    public final String nombre;
    public final String usuario;
    // public final String interfaze; //TODO: Implement functionality

    protected ArrayList<Integer> idCitasAgendadas = null;

    protected static int tope=1;
    public int topeDiarioUsuario = 0;
    protected static int topeDiario = 60;

    // Constructor
    public Usuario(String id, String nombre, String usuario) {

        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.idCitasAgendadas = this.getMisCitas();
        
    }

    /*
     * Esta funcion crea el inicio de sesion de los usuarios
     * atravez de un request a un servidor externo
     *
     * @param username Nombre de usuario
     * @param password Contraseña de ususario
     *
     * @return El objeto usuario
     */
    public static Usuario iniciarSesion(String username, String password) {

      Usuario sesion = null;
      String urlString = "http://localhost:8080?username=" + username + "&password=" + password;
      URL url = null;
      HttpURLConnection con = null;
      BufferedReader in = null;
            
      String inputLine;
      String content = "";


      try{
        url = new URI(urlString).toURL();
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        in = new BufferedReader(
          new InputStreamReader(con.getInputStream())
        );

        while ((inputLine = in.readLine()) != null) {
            content += inputLine;
        }

        con.disconnect();
        in.close();

      } catch(Exception e)  {
        System.out.println(e);
      }




      JSONObject body = new JSONObject(content);     

      if(body.getString("result").equals("fail")) {
        return sesion;
      }

      if(body.getString("result").equals("error")) {
        System.out.println("Algo muy malo paso en la base de datos");
        return sesion;
      }

      // TODO: feisimo, redundante, arreglalo mas tarde
      if(body.getString("rol").equals("estudiante")) {
        sesion = new Usuario(
                      body.getInt("id")+"",
                      body.getString("name"),
                      body.getString("username")
                     );
      } else if (body.getString("rol").equals("profesor")) {
        sesion = new Profesor(
                      body.getInt("id")+"",
                      body.getString("name"),
                      body.getString("username")
                     );
      } else if (body.getString("rol").equals("administrador")) {
        sesion = new Administrador(
                      body.getInt("id")+"",
                      body.getString("name"),
                      body.getString("username")
                     );
      } else {
        System.out.println("El usuario no tiene un rol correcto");
      }

      return sesion;
    }

    /*
     * Esta funcion busca en las citas existentes las que
     * pertenecen al usuario
     *
     * @return Lista de id's de todas las citas que pertenecen al
     * usuario
     */
    public ArrayList<Integer> getMisCitas(){
      if(this.idCitasAgendadas != null) {
        return this.idCitasAgendadas;
      }

      ArrayList<Integer> result = new ArrayList<Integer>();

      for(Cita cita : Cita.citas) {
        if(cita.creador.equals(this.usuario) && Evento.validarFecha(cita.getFechaInicio())) {
          result.add(cita.id);
        }
      }

      this.idCitasAgendadas = result;

      return this.idCitasAgendadas;
    }


    /*
     * Esta funcion busca las citas validas al momento
     *
     * @return Las citas no vencidas del usuario
     */
    public ArrayList<Cita> consultar() {
      ArrayList<Cita> result = null;

      for(Cita cita: Cita.citas){
          if(Evento.validarFecha(cita.getFechaInicio())){
              result.add(cita);
          }
      }

      return result;
        
    }

    /*
     * Esta funcion es para agregar citas
     *
     * @param fechaInicio La fecha en la que empieza la cita
     * @param duracion El tiempo que dura la cita
     * @param filamento El peso de la impresion
     *
     * @return Verdadero si pudo agendar la cita, falso para 
     * lo contrario
     */
    public boolean agendar(LocalDateTime fechaInicio, int duracion, double filamento) {

      // Verificar datos
      if(idCitasAgendadas.size() >= Usuario.tope || topeDiarioUsuario + duracion >= topeDiario) {
        System.out.println("Ya llegaste al tope de citas diarias o por usuario");
        return false;
      }
      if (Cita.seSuperpone(fechaInicio, fechaInicio.plusMinutes(duracion))) {
          System.out.println("Ya hay una cita programada en ese horario. Intente con otro horario.");
          return false;
      }
              
      // Buscar impresoras
      Impresora impresoraAsignada = null;

      for (Impresora imp : Impresora.getImpresoras()) {
          if (imp.getFilamento() >= filamento && imp.isDisponible()) {
              impresoraAsignada = imp;
              break;
          }
      }

      if (impresoraAsignada == null) {
          System.out.println("No hay impresoras disponibles con suficiente espacio o filamento.");
          return false;
      }


      // Crear la cita
      Cita nuevaCita = new Cita(
                            impresoraAsignada.idImpresora, 
                            filamento, 
                            fechaInicio, 
                            duracion, 
                            this.usuario);

      idCitasAgendadas.add(nuevaCita.id);

      impresoraAsignada.agendarCita(nuevaCita);

      return true;

    }

    /*
     * Esta funcion elimina un elemento de ya sea anuncios o citas
     *
     * @param eleccion Es la tabla de la que se elimina
     * @param id El id del lemento a eliminar
     */
    public void cancelar(String eleccion, int id) {

      try {
        Database.delete(eleccion, id);
      } catch(SQLException e) {
        System.out.println(e);
      }

      this.idCitasAgendadas.remove(id);

      // Denuevo, bastante feo, pero funciona
      // Esto busca la cita que se esta eliminando y se elimina
      for(Cita cita: Cita.citas) {
        if(cita.id == id && cita.creador.equals(this.usuario)){
          Cita.citas.remove(cita);
          return;
        }
      }

      System.out.println("Esa cita no te pertenece o no existe");
    }

    /*
     * Esta funcion crea un anuncio y lo agrega a la base de datos
     *
     * @param mensaje El mensaje que lleva el anuncio
     * @param fechaInicio La fecha en que el anuncio toma lugar
     * @param fechaFin La fecha en que el aununcio finaliza
     *
     * @return nada
     */
    public void anunciar(String mensaje, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if(this.rol.equals("estudiante")) {
          System.out.println("Tu no puedes anunciar nada!!!!!");
          return;
        }

        Anuncio anuncio = new Anuncio(mensaje, fechaInicio, fechaFin, this.usuario);

        try {
          Database.uploadEvent(anuncio);
        } catch(Exception e) {
          System.out.println(e);
        }

        System.out.println("Anuncio realizado con exito!");
    }
}



