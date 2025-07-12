// Esta clase es para manejar la base de datos de los Eventos y que el programa funcione
// con informacion no volatil

import java.time.LocalDateTime;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Database {

  static Connection connection;
  static Statement statement;
    

  static final String CREATE_CITAS_IF_NOT_EXITS = 
    "create table if not exists citas (" +
        "id text," +
        "impresora integer," + 
        "peso integer," + 
        "fecha text," + 
        "creador text," +
        "duracion integer" +
    ")";
  static final String CREATE_ANUNCIOS_IF_NOT_EXITS = 
    "create table if not exists anuncios (" +
        "id integer," +
        "fechaInicio text," + 
        "fechaFin text," + 
        "creador text," +
        "mensaje" +
    ")";

  static final String CREATE_IMPRESORAS_IF_NOT_EXITS = 
    "create table if not exists impresoras (" +
        "id integer," +
        "disponible integer," + 
        "filamento integer," + 
        "razon text" +
    ")";

  public static final String CITAS = "citas";
  public static final String ANUNCIOS = "anuncios";
  public static final String IMPRESORAS = "impresoras";
  public static final ArrayList<String> TABLES = new ArrayList<String>(){{
    add(CITAS);
    add(ANUNCIOS);
    add(IMPRESORAS);
  }};

  // Por defecto aun no se ha hecho la conexion a la base de datos
  static boolean dbConnectionMade = false;

 /*
  * Esta funcion crea la conexion a la base de datos si aun no se ha hecho
  * y define las tablas si aun no se an definido
  *
  * @param filename el nombre del archivo de la base de datos
  * @return void
  *
 */
  public static void createConnection(String filename){

    // Si la conexion al a base de datos ya se hizo
    // Solo salir sin hacer nada
    if(dbConnectionMade) {
      return;
    }

    // Intenta definir la conexion a la base de datos
    // Si falla escupe el error
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
      statement = connection.createStatement();
      dbConnectionMade = true;
      // Crea las tablas que no existan
      statement.executeUpdate(CREATE_CITAS_IF_NOT_EXITS );
      statement.executeUpdate(CREATE_ANUNCIOS_IF_NOT_EXITS );
      statement.executeUpdate(CREATE_IMPRESORAS_IF_NOT_EXITS );
    } 
    catch (SQLException e) {
      e.printStackTrace(System.err);
    }


  }


  /*
   * Esta funcnion busca en la base de datos de eventos y retorna lo encontrado (No mas)
   * 
   * @param table Ya sea la tabla de citas o la tabla de anuncions segun las variables finales
   * @param query La condicion que debe cumplir para que el evento sea seleccionado
   * @param columns Las columanas que se estan pidiendo de la tabla
   *
   * @return Eventos encontrados
  */
  public static ResultSet selectEvents(String table, String query) throws SQLException{

    if(!table.equals(CITAS) && table.equals(ANUNCIOS)) {
      throw new IllegalArgumentException("La tabla '" + table + "' no es considerada valida");
    }
    if(query.equals("")) {
      throw new IllegalArgumentException("La query no puede estar vacia");
    }

    ResultSet result = null;
    try {
      result = statement.executeQuery("select " + query + " from " + table + ";");
    } catch(NullPointerException e) {
      // do nothing
    }

    return result;
  }

  public static ArrayList<Cita> selectCitas(String query) throws SQLException{
    ResultSet citasList = selectEvents(Database.CITAS, query);
    ArrayList<Cita> citas = new ArrayList<Cita>();
    if(citasList == null) {
      return citas;
    }
    while(citasList.next()) {
      citas.add(
        new Cita(
          citasList.getInt("impresora"), 
          citasList.getInt("peso"),
          Gestor.parseTime(citasList.getString("fecha")),
          citasList.getInt("duracion"),
          citasList.getString("creador")
        )
      );
    }
    return citas;
  }

  public static ArrayList<Anuncio> selectAnuncios(String query) throws SQLException{
    ResultSet anunciosList = selectEvents(Database.ANUNCIOS ,query);
    ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();

    if(anunciosList == null) {
      return anuncios;
    }

    while(anunciosList.next()) {
      anuncios.add(
        new Anuncio(
          anunciosList.getString("mensaje"),
          Gestor.parseTime(anunciosList.getString("fechaInicio")),
          Gestor.parseTime(anunciosList.getString("fechaFinal")),
          anunciosList.getString("creador")
        )
      );
    }
    return anuncios;
  }

  /*
   * Esta funcion agrega eventos a la base de datos
   *
   * @param evento el evento que se piensa agregar
   */
  public static void uploadCita(Cita evento) throws SQLException{
    statement.executeUpdate("insert into citas values(" +
        "'"+evento.id + "', " + //string
        evento.numImpresora + ", " +
        evento.pesoFilamento + ", " + 
        "'"+Gestor.dateFormatter(evento.fechaInicio) + "', " + //string
        "'"+evento.creador + "', " +  //string
        evento.duracion + ")"
        );
    }
      
  public static void uploadAnuncio(Anuncio evento) throws SQLException{
    statement.executeUpdate("insert into anuncios values(" +
        evento.id + ", " +
        (evento).fechaInicio + ", " +
        (evento).fechaFin + ", " +
        (evento).creador + ", " + 
        (evento).mensaje + ")"
        );
  }

  /*
   * Esta funcion carga las impresoras en  la base de datos a la memoria
   *
   *  @return La lista de impresoras disponibles o no disponibles
   */
  public static ArrayList<Impresora> loadPrinters(){

    ArrayList<Impresora> impresoras = new ArrayList<Impresora>();
    ResultSet result = null;
    try{
      result = statement.executeQuery("select * from impresoras");

      while(result.next()) {
        impresoras.add(new Impresora(
                            result.getInt("id"), 
                            (double)result.getInt("filamento"),
                            result.getInt("disponible") == 1,
                            result.getString("razon")
                            ));
      }

      System.out.println("Impresoras cargadas");

    } catch(NullPointerException e) {
      e.printStackTrace(System.err);
      return null;
    } catch(SQLException e) {
      System.out.println("No hay impresoras...");
      System.out.println(e);
    }

    return impresoras;
  }
  
  /*
   * Esta funcion agrega impresoras a la base de datos
   *
   * @param impresora Es la impresora que se piensa agregar
   */

  public static void uploadPrinter(Impresora impresora) throws SQLException{
    try{
      statement.executeUpdate("insert into impresoras values(" +
        impresora.idImpresora + ", " +
        (impresora.isDisponible() ? "1" : "0") + ", " +
        (int)impresora.getFilamento() + ", " + // TODO: Implement double
        "'" + impresora.getRazon() + "'" + ")");
    } catch(SQLException e) {
      e.printStackTrace(System.err);
    } catch(Exception e) {
      e.printStackTrace(System.err);
    }
  }

  /*
   * Esta funcion se encarga de eliminar cosas de la base de datos
   *
   * @param table La tabla que se esta seleccionando
   * @param id El id del objeto que se borra
   */
  public static void delete(String table, long id) throws SQLException{
    statement.executeUpdate("delete from " + table + " where id=" + id);
  }



  /*
   * Esta funcion es para poner a prueba la funcionalidad de la clase Esta funcion es para poner a prueba la funcionalidad de la clase
   */
  // TODO: Implement
  public static void main(String[] args) {
    System.out.println("Entraste al test de funcionalidad de la clase Database");
  }
}
