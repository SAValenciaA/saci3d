import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static Map<String, String>[] getUsers() {
        Map<String, String>[] usuarios = new HashMap[] {
            new HashMap<>() {{ put("id", "52567"); put("usuario", "sevalenciaa"); put("nombre", "Sergio Valencia"); put("contraseña", "rockyou"); put("rol", "profesor"); }},
            new HashMap<>() {{ put("id", "81352"); put("usuario", "jlopezm"); put("nombre", "Juliana Lopez"); put("contraseña", "password123"); put("rol", "estudiante"); }},
            new HashMap<>() {{ put("id", "90211"); put("usuario", "cmartinez"); put("nombre", "Carlos Martinez"); put("contraseña", "1234abcd"); put("rol", "profesor"); }}
            // Agrega más si quieres...
        };
        return usuarios;
    }

    public static int options(Scanner sc) {
        System.out.println("Opciones");
        System.out.println(" 1) Consultar citas");
        System.out.println(" 2) Agendar citas");
        System.out.println(" 3) Salir");
        System.out.print("Seleccione una opción: ");
        return sc.nextInt();
    }

    public static boolean login(String username, String password) {
        for (Map<String, String> usuario : getUsers()) {
            if (usuario.get("usuario").equals(username)) {
                if (usuario.get("contraseña").equals(password)) {
                    System.out.println("¡Usuario encontrado! Hola señor(a) " + usuario.get("nombre"));
                    return true;
                } else {
                    System.out.println("Contraseña incorrecta.");
                    return false;
                }
            }
        }
        System.out.println("Usuario no encontrado.");
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Usuario: ");
        String username = sc.nextLine();

        System.out.print("Contraseña: ");
        String password = sc.nextLine();

        if (login(username, password)) {
            int option = options(sc);
            switch (option) {
                case 1:
                    System.out.println("Consultala tú mismo."); // Aquí iría lógica real
                    break;
                case 2:
                    System.out.println("Agéndala tú mismo."); // Aquí iría lógica real
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
