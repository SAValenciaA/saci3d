import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Gestor {
    public static Usuario usuario;
    public static Scanner sc = new Scanner(System.in);

    public Gestor() {
    }

    public static LocalDateTime parseTime(String date) {
      DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      return LocalDateTime.parse(date, formato);
    }
    public static String dateFormatter(LocalDateTime date) {
      DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      return date.format(formato);
    }

    /*
     * This function checks if a string is a number
     * 
     * @param str the string to be checked
     * @return true if it is a number
     */
    public static boolean isNumeric(String str) { 
      try {  
        Double.parseDouble(str);  
        return true;
      } catch(NumberFormatException e){  
        return false;  
      }  
    }

    
    /*
     * Esta funcion toma una fecha de un usuario y la valida
     *
     * @param mensaje Es el mensaje que se le manda al usuario cada 
     * vez que se le pide la fecha
     *
     * @return La fecha ingresada
     */
    public static LocalDateTime ingresarFecha(String mensaje){

        sc = new Scanner(System.in);
        LocalDateTime fechaInicio;
        String fechaStr;

        while (true) {
          try {
              System.out.print(mensaje + " (formato: yyyy-MM-dd HH:mm): ");
              fechaStr = sc.nextLine();
              fechaInicio = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

              if (LocalDateTime.now().isAfter(fechaInicio)) {
                  System.out.println("La fecha ingresada ya pasó. Intente con una fecha futura.");
                  continue;
              } 

              break;

          } catch (Exception e) {
              System.out.println("Formato de fecha inválido. Intente nuevamente.");
          }
        }
        return fechaInicio;
    }

    /*
     * Esta funcion es para probar el programa por terminal,
     * empezando con una pantalla de login
     */
    public static void inicio(){

        System.out.println("Bienvenido al SACI3D piloto");

        Scanner sc = new Scanner(System.in);
        String username, password;

        while(true){
            System.out.print("Usuario: ");
            username = sc.nextLine();

            System.out.print("Contraseña: ");
            password = sc.nextLine();

            usuario = Usuario.iniciarSesion(username, password);

            if(usuario != null){
                elegirOpcion();
                break;
            }

            System.out.println("Usuario o contraseña incorrecta.");
            
        }
    }

    /*
     * Esta funcion le muestra al usuario las opciones que 
     * puede elegir acorde al tipo de usuario que es
     */
    public static void elegirOpcion() {
      Scanner sc = new Scanner(System.in);
      boolean menu = true;
      String optionString;
      int option;

      while(menu) {

        System.out.println("Hola " + usuario.nombre + " ¿Que deseas hacer el dia de hoy?");
        System.out.println(
            "1 -> consultar\n" +
            "2 -> agendar\n" +
            "3 -> cancelar mis citas\n" +
            "4 ->ver mis citas");

        // Imprime opciones para profesor y administrador, 
        // si el usuario es profesor o administrador
        if(usuario instanceof Profesor) {
          System.out.println("5 -> Anunciar");
        } else if(usuario instanceof Administrador) {
          System.out.println(
              "5 -> Anunciar\n" +
              "6 ->Agregar material a impresora\n" +
              "7 ->Agregar Impresora\n" +
              "8 ->Cancerlar cita de otro usuario\n" +
              "9 -> Banear usuario\n" +
              "10 -> Imprimir informe actual");
        }

        System.out.println("q -> Salir");

        System.out.print("Elige: ");

        optionString=sc.nextLine();

        // Comprueba si el usuario desea salir
        if(optionString.equals("q")) {
          System.out.println("D: Saliendo...");
          break;
        } else if(! isNumeric(optionString)) {
          System.out.println("No es una opcion valida");
          continue;
        }

        option = Integer.parseInt(optionString);

        // Comprueba que el usuario no esta intentando hackear 
        // el sistema
        if( 
            (option >= 5 && usuario.rol == "estudiante") || 
            (option > 5 && usuario.rol == "profesor")
          ) {

          System.out.println("Buen intento hacker!!!");
          continue;
        }

        hacerOpcion(option);
      }
    }


    /*
     * Esta funcion ejecuta la orden que el usuario eligio
     */
    public static void hacerOpcion(int option) {
      LocalDateTime fechaInicio, fechaFin;
      int id, duracion;
      double filamento;

      switch(option) {
        // Consultar que citas hay
        case 1:
          ArrayList<Cita> citas = Cita.getCitasValidas();
          if(citas == null) {
            System.out.println("No hay citas agendadas");
            break;
          }
          for(Cita cita : citas) {
            System.out.println(cita);
          }
          break;

        // Agendar una cita
        case 2:
            fechaInicio = ingresarFecha("Ingrese fecha y hora");

            System.out.print("Ingrese la duracion de impresion: ");
            duracion = sc.nextInt();

            System.out.print("Ingrese peso de impresion: ");
            filamento = (double)sc.nextInt();


            if(usuario.agendar(fechaInicio, duracion, filamento)) {
              System.out.println("Cita agendada exitosamente");
            } else {
              System.out.println("Cita no agendada");
            }
            break;

        // Cancelar una cita
        case 3:
            System.out.println("ingresa el id de tu cita a cancelar");
            long citaId;
            citaId=sc.nextLong();
            usuario.cancelar(Database.CITAS, citaId);
            break;
        // Listar citas del usuario
        case 4:
            ArrayList<Cita> userCitas = Cita.getCitasByName(usuario.usuario);
            if(userCitas.size() == 0) {
              System.out.println("No tienes citas agendadas.");
              break;
            }
            for(Cita cita : userCitas) {
              System.out.println(cita);
            }
            break;
        // Anunciar
        case 5:

          System.out.print("Escribe el mensaje del anuncio: ");
          String mensaje = sc.nextLine();

          fechaInicio = ingresarFecha("Escribe la fecha de inicio del anuncio: ");  
          fechaFin = ingresarFecha("Escribe la fecha de fin del anuncio: ");  

          usuario.anunciar(mensaje, fechaInicio, fechaFin);
          break;

        // Cambiar cantidad de filamento TODO: Implementar
        case 6:

            System.out.print("Ingrese el id de la impresora a la que agregar/quita material: ");
            id = sc.nextInt();

            System.out.print("Ingrese cuanto filamento se agrego/quito: ");
            filamento = (double)sc.nextInt();

            break;

        // Agregar impresora
        case 7:
            System.out.print("Ingrese cuanto filamento tiene la nueva impresora:");
            filamento = (double)sc.nextInt();

            try{
              Impresora nuevaImpresora = new Impresora(-1 , filamento, true,"");
            } catch(SQLException e) {
              System.out.println(e);
            }

            break;
        // cancelar cita
        case 8:
          while(true) {

            String tablas, eleccion;

            tablas = "(";
            tablas += Database.CITAS + ", ";
            tablas += Database.ANUNCIOS + ", ";
            tablas += Database.IMPRESORAS + ")";

            System.out.print("Ingrese la tabla en la que desea eliminar " + tablas + ": ");
            eleccion = sc.nextLine();

            if(! Database.TABLES.contains(eleccion)) {
              System.out.println("Esa no es una opcion valida!!!");
              continue;
            }

            System.out.print("Ingresa el id del elemento a eliminar: ");
            id = sc.nextInt();

            usuario.cancelar(eleccion, id);

            break;
          }
          break;

        // Banear usuario TODO: IMPLEMENTAR
        case 9:
          break;

        // Imprimir informe TODO: IMPLEMENTAR
        case 10:
          //((Administrador)usuario).informe();
          break;

        default:
            System.out.println("Opción no válida.");
      }

    }

    public static void main(String[] args) {
      Database.createConnection("database.db");
      Gestor.inicio();
    }
}
