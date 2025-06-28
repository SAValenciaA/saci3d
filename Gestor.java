import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gestor {
    private Usuario[] listaUsuarios;
    private ArrayList<Impresora> listaImpresoras;
    private ArrayList<Cita> listaCitas;
    

    public Gestor() {
        this.listaUsuarios= getUsuarios();
        this.listaImpresoras = getImpresora();
        this.listaCitas = getCitas();
        
    }

    private Usuario[] getUsuarios() {
        Usuario[] usuarios = new Usuario[] {
        new Profesor("1001", "Sergio Valencia", "sevalenciaa", "rockyou"),
        new Administrador("1002", "Juliana Lopez", "jlopezm", "password123"),
        new Estudiante("1003", "Carlos Martinez", "cmartinez", "1234abcd"),
        new Estudiante("1004", "Ana Vasquez", "anavasq", "ana2024"),
        new Profesor("1005", "Luis Rodriguez", "lrodriguez", "qwerty"),
        new Administrador("1006", "Alejandro Muñoz", "a", "a")
        };
        return usuarios;
    }

    private ArrayList<Impresora> getImpresora(){
        ArrayList<Impresora> impresoras = new ArrayList<>();
        
            impresoras.add(new Impresora("A", 5));
            impresoras.add(new Impresora("B", 3));
            impresoras.add(new Impresora("C", 4));
        
        return impresoras;
        
    }
    private ArrayList<Cita> getCitas() {  ArrayList<Cita> citas = new ArrayList<>();

        // Agregar objetos Cita
        citas.add(new Cita(listaImpresoras.get(0), 23.5, LocalDateTime.of(2025, 6, 28, 10, 0), 60, listaUsuarios[0]));
        citas.add(new Cita(listaImpresoras.get(1), 45.0, LocalDateTime.of(2025, 6, 28, 12, 0), 90, listaUsuarios[1]));
        citas.add(new Cita(listaImpresoras.get(2), 30.0, LocalDateTime.of(2025, 6, 29, 9, 30), 45, listaUsuarios[2]));
        
        return citas;
    }

    // Métodos para agregar, buscar, validar, etc...

    // Método para iniciar sesión (versión básica con solo nombre de usuario)
    public Usuario iniciarSesion(String inicioUsuario, String contraseña) {

        for (Usuario usuario : listaUsuarios) {
            if (usuario != null && usuario.getUsuario().equals(inicioUsuario)) {
                if(usuario.getContraseña().equals(contraseña)){
                    return usuario;
                }
            }
        }
        return null; // no encontrado
    }

    // Calcula el total de filamento usado en todas las citas
    public double calculoFilamento() {
        double total = 0;
        for (Impresora c : listaImpresoras) {
            if (c != null) {
                total += c.getFilamento();
            }
        }
        return total;
    }

    // Verifica si una nueva cita se superpone con alguna existente
    public boolean seSuperponeCon(Cita nueva) {
        LocalDateTime ini2 = nueva.getFechaInicio();
        LocalDateTime fin2 = nueva.getFechaFinal();

        for (Cita existente : listaCitas) {
            if (existente != null) {
                LocalDateTime ini1 = existente.getFechaInicio();
                LocalDateTime fin1 = ini1.plusMinutes(existente.getDuracion());

                // Verifica superposición
                if (ini1.isBefore(fin2) && ini2.isBefore(fin1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void inicio(){

        System.out.println("Bienvenido al SACI3D piloto");

        Scanner sc = new Scanner(System.in);

        System.out.print("Usuario: ");
        String username = sc.nextLine();

        System.out.print("Contraseña: ");
        String password = sc.nextLine();

        boolean rep= true;
        Usuario ingreUsuario;

        while(rep){
            ingreUsuario=iniciarSesion(username, password);
            if(ingreUsuario!=null){
                rep=false;
                if (ingreUsuario instanceof Profesor) {
                    Profesor profesor = (Profesor) ingreUsuario;
                    inicioProfesor(profesor);
                } else if (ingreUsuario instanceof Estudiante) {
                    Estudiante estudiante = (Estudiante) ingreUsuario;
                    inicioEstudiante(estudiante);
                    // Aquí puedes acceder a métodos o atributos específicos de Estudiante
                } else if (ingreUsuario instanceof Administrador) {
                    Administrador admin = (Administrador) ingreUsuario;
                    inicioAdministrador(admin);
                    // Aquí puedes acceder a métodos o atributos específicos de Administrador
                } else {
                    System.out.println("Tipo de usuario desconocido");
                }

            }else{
                System.out.println("Usuario o contraseña incorrectos");
                inicio();
            }
            
        }


    }

    public void inicioEstudiante(Estudiante estudiante){
        Scanner sc= new Scanner(System.in);
        boolean menu= true;
        while(menu){
            System.out.println("Hola "+estudiante.getNombre()+" ¿que deseas hacer el dia de hoy?");
            System.out.println("1) consultar\n2) agendar\n3) cancelar\n4) salir");
            String opt=sc.nextLine();
        
            boolean rep=true;
            while(rep){

                switch (opt) {

                    case "1":
                        estudiante.consultar(listaCitas);
                        rep=false;
                        break;

                    case "2":
                        estudiante.agendar(sc,listaCitas, listaImpresoras);
                        rep=false;
                        break;

                    case "3":
                        estudiante.cancelar();
                        rep=false;
                        break;
                    case "4":
                        
                        return;

                    default:
                        System.out.println("Opción no válida.");
                }
            }


        }

    }  
    public void inicioProfesor(Profesor profesor){
        Scanner sc= new Scanner(System.in);
        boolean menu= true;
        while(menu){
            System.out.println("Hola profesor "+profesor.getNombre()+" ¿que deseas hacer el dia de hoy?");
            System.out.println("1) consultar\n2) agendar\n3) cancelar\n4) anunciar\n5)salir");
            String opt=sc.nextLine();
            boolean rep=true;
            while(rep){

                switch (opt) {

                    case "1":
                        profesor.consultar(listaCitas);
                        rep=false;
                        break;

                    case "2":
                        profesor.agendar(sc,listaCitas, listaImpresoras);
                        rep=false;
                        break;

                    case "3":
                        profesor.cancelar();
                        rep=false;
                        break;
                    case "4":
                        profesor.anunciar();
                        rep=false;
                        break;
                    case "5":
                    
                        return;

                    default:
                        System.out.println("Opción no válida.");
                }
            }


        }

    }
    public void inicioAdministrador(Administrador administrador){
        Scanner sc= new Scanner(System.in);
        boolean menu= true;
        while(menu){
            System.out.println("Hola profesor "+administrador.getNombre()+" ¿que deseas hacer el dia de hoy?");
            System.out.println("1) consultar\n2) agendar\n3) cancelar mi cita\n4) anunciar\n5)cancelar citas\n6)cambiar disponibilidad impresora\n7)cambiar tope de impresion \n8)agregar impresora\n9)salir");
            String opt=sc.nextLine();
            boolean rep=true;
            while(rep){

                switch (opt) {

                    case "1":
                        administrador.consultar(listaCitas);
                        rep=false;
                        break;

                    case "2":
                        administrador.agendar(sc,listaCitas, listaImpresoras);
                        rep=false;
                        break;

                    case "3":
                        administrador.cancelar();
                        rep=false;
                        break;
                    case "4":
                        administrador.anunciar();
                        rep=false;
                        break;
                    case "5":

                        System.out.print("Ingresa el id de la cita a cancelar: ");
                        String idCita = sc.nextLine();
                        administrador.cancelarCitas(listaCitas, idCita);
                        rep = false;
                    
                        break;
                    case "6":
                        System.out.println("Ingresa el id de la impresora");
                        String idImpresora=sc.nextLine();
                        administrador.cambiarDispo(listaImpresoras,idImpresora);
                        rep=false;
                        break;
                    case "7":
                        administrador.cambiarTope();
                        rep=false;
                        break;
                    case "8":
                        administrador.agregarImpresora(sc, listaImpresoras);
                        rep=false;
                        break;
                    case "9":
                        
                        return;

                    default:
                        System.out.println("Opción no válida.");
                }
            }


        }

    }
}
