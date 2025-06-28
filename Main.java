
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    //arreglo de usuarios
    //una plantilla para cada tipo de usuario

    public static Usuario[] getUsuarios() {
    Usuario[] usuarios = new Usuario[] {
        new Usuario("1001", "Sergio Valencia", "sevalenciaa", "rockyou"),
        new Usuario("1002", "Juliana Lopez", "jlopezm", "password123"),
        new Usuario("1003", "Carlos Martinez", "cmartinez", "1234abcd"),
        new Usuario("1004", "Ana Vasquez", "anavasq", "ana2024"),
        new Usuario("1005", "Luis Rodriguez", "lrodriguez", "qwerty")
        };
        return usuarios;
    }
    //arreglo de citas 
    //sugerencia: cita debe heredar de evento, una plantilla para cada tipo de evento
    public static ArrayList<Cita> getSchedule() {
        List<Cita> Eventos =List.of(
            new Cita(120, new Impresora(2,4), "Engranaje motor", "Pieza PLA para motor NEMA 17", 150),
            new Cita(90, new Impresora(5,4), "Carcasa sensor", "Caja protectora para sensor ultrasónico", 85),
            new Cita(180, new Impresora(3,4), "Soporte brazo robot", "Soporte estructural en PETG", 220),
            new Cita(60, new Impresora(4,4), "Tapa roscada", "Prototipo de tapa con rosca M20x2.5", 45),
            new Cita(150, new Impresora(1,4), "Base de dron", "Estructura base en ABS resistente", 300)
        );
        ArrayList<Cita> citas=new ArrayList<>(Eventos);
        return citas;
    }


    public static int options(Scanner sc) {
        System.out.println("Opciones");
        System.out.println(" 1) Consultar citas");
        System.out.println(" 2) Agendar citas");
        System.out.println(" 3) Salir");
        System.out.print("Seleccione una opción: ");
        return sc.nextInt();
    }

    public static int login(String username, String password) {
        Usuario [] usuarios=getUsuarios();
        for (int i=0;i<usuarios.length; i++){
            if (usuarios[i].getUsuario().equals(username)) {
                if (usuarios[i].getContraseña().equals(password)) {
                    System.out.println("¡Usuario encontrado! Hola señor(a) " +username);
                    return i;
                } else {
                    System.out.println("Contraseña incorrecta.");
                    return -1;
                }
            }
        }
        System.out.println("Usuario no encontrado.");
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Usuario: ");
        String username = sc.nextLine();

        System.out.print("Contraseña: ");
        String password = sc.nextLine();
        int busqueda=login(username, password);
        if (busqueda!=-1) {
            int option = options(sc);
            Usuario usuario= getUsuarios()[busqueda];

            switch (option) {
                case 1 -> usuario.consultar(getSchedule());
                case 2 -> usuario.agendar();
                case 3 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        }
    }
}


