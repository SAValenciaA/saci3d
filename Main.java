import java.util.HashMap;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Main {

  static List<Map<String, String>> getUsers() {
      List<Map<String, String>> usuarios = Arrays.asList(
        new HashMap<>() {{ put("id", "52567"); put("usuario", "sevalenciaa"); put("nombre", "Sergio Valencia"); put("contraseña", "rockyou"); put("rol", "profesor");; }},
        new HashMap<>() {{ put("id", "81352"); put("usuario", "jlopezm"); put("nombre", "Juliana Lopez"); put("contraseña", "password123"); put("rol", "estudiante"); }},
        new HashMap<>() {{ put("id", "90211"); put("usuario", "cmartinez"); put("nombre", "Carlos Martinez"); put("contraseña", "1234abcd"); put("rol", "profesor"); }},
        new HashMap<>() {{ put("id", "73485"); put("usuario", "anavasq"); put("nombre", "Ana Vasquez"); put("contraseña", "ana2024"); put("rol", "estudiante"); }},
        new HashMap<>() {{ put("id", "61134"); put("usuario", "lrodriguez"); put("nombre", "Luis Rodriguez"); put("contraseña", "qwerty"); put("rol", "profesor"); }},
        new HashMap<>() {{ put("id", "47390"); put("usuario", "mjimenez"); put("nombre", "Maria Jimenez"); put("contraseña", "pass321"); put("rol", "estudiante"); }},
        new HashMap<>() {{ put("id", "29876"); put("usuario", "dtorres"); put("nombre", "Daniel Torres"); put("contraseña", "dt2025"); put("rol", "profesor"); }},
        new HashMap<>() {{ put("id", "15892"); put("usuario", "fsandoval"); put("nombre", "Fabiana Sandoval"); put("contraseña", "fabi123"); put("rol", "estudiante"); }},
        new HashMap<>() {{ put("id", "68745"); put("usuario", "rmeneses"); put("nombre", "Ricardo Meneses"); put("contraseña", "rmene456"); put("rol", "profesor"); }}
        );
      return usuarios;
  }
  
  static List<Map<String, String>> getSchedule() {
      List<Map<String, String>> agenda = Arrays.asList(
        new HashMap<>() {{ put("id", "AG001"); put("Fecha", "2025-06-23 09:00"); put("duracion", "120"); put("Peso", "150"); put("nombre", "Engranaje motor"); put("descripcion", "Pieza PLA para motor NEMA 17"); put("Creador", "sevalenciaa"); put("Impresora", "2"); }},
        new HashMap<>() {{ put("id", "AG002"); put("Fecha", "2025-06-23 11:30"); put("duracion", "90"); put("Peso", "85"); put("nombre", "Carcasa sensor"); put("descripcion", "Caja protectora para sensor ultrasónico"); put("Creador", "jlopezm"); put("Impresora", "5"); }},
        new HashMap<>() {{ put("id", "AG003"); put("Fecha", "2025-06-23 14:00"); put("duracion", "180"); put("Peso", "220"); put("nombre", "Soporte brazo robot"); put("descripcion", "Soporte estructural en PETG"); put("Creador", "cmartinez"); put("Impresora", "2"); }},
        new HashMap<>() {{ put("id", "AG004"); put("Fecha", "2025-06-24 08:00"); put("duracion", "60"); put("Peso", "45"); put("nombre", "Tapa roscada"); put("descripcion", "Prototipo de tapa con rosca M20x2.5"); put("Creador", "anavasq"); put("Impresora", "4"); }},        
        new HashMap<>() {{ put("id", "AG005"); put("Fecha", "2025-06-24 10:00"); put("duracion", "150"); put("Peso", "300"); put("nombre", "Base de dron"); put("descripcion", "Estructura base en ABS resistente"); put("Creador", "lrodriguez"); put("Impresora", "1"); }}
      ); 
      return agenda;
  }

  static int options(Scanner sc) {
    System.out.println("Opciones");
    System.out.println(" 1) Consultar citas");
    System.out.println(" 2) Agendar citas");
    System.out.println(" 3) salir");
    System.out.print("Selecciones una opcion: ");
    return sc.nextInt();
  }
  

  static boolean login(String username, String password) {
    for(Map<String, String> usuario: getUsers()) {
      if (usuario.get("usuario").equals(username)) {
        if(usuario.get("contraseña").equals(password)){
          System.out.println("Usuario encontradro! Hola señor " + username);
          return true;
        } else {
          System.out.println("Contraseña incorrecta");
        }
      }
    }
    return false;
  }
  
  public static void main(String[] args) {
    
    
    //for(Hashmap<>() usuario: usuarios) {
    //  if (usuario.get("usuario") == usuarioInput) {
        //cromprobat contraseña
    //  }
    //}

    Scanner sc = new Scanner(System.in);
    System.out.print("Usuario: ");
    String username = sc.nextLine();
    System.out.print("Contraseña: ");
    String password = sc.nextLine();
    
    if(login(username,password)) {
      int option = options(sc);
      switch(option) {
        case 1:
          System.out.println("Consultala tu mismo!!!!"); // cambiar
          break;
        case 2:
          System.out.println("Agendala tu mismo!!!!"); // cambiar
          break;
        case 3:
          System.out.println("Ahhhhh!!!!");
          break;
        default:
          System.out.println("Esa no es una opcion valida");
          return;
      }
    } else {
      
    }
  }
}
