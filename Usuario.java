
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Usuario {
    private String id;
    private String nombre;
    private String usuario;
    private String idCitaAgendada;
    private String contraseña;

    // Constructor
    public Usuario(String id, String nombre, String usuario, String contraseña) {

        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.idCitaAgendada = "000000";
        this.contraseña=contraseña;
        
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getId() {
        return id;
    }
    public String getIdCita(){
        return idCitaAgendada;
    }


    public void consultar(ArrayList<Cita> citas) {
        for(Cita cita: citas){
            System.out.println(cita);
        }
        
    }

    public void agendar(Scanner sc, List<Cita> citas,  List<Impresora> impresoras) {

            try {
                System.out.println("Ingrese la fecha y hora de inicio (formato: yyyy-MM-dd HH:mm):");
                String entradaFecha = sc.nextLine();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime fechaInicio = LocalDateTime.parse(entradaFecha, formato);

                System.out.println("Ingrese la duración en minutos:");
                int duracion = sc.nextInt();
                sc.nextLine(); // limpiar salto

                System.out.println("Ingrese el peso del filamento:");
                double peso = sc.nextDouble();
                sc.nextLine(); // limpiar salto

                // Buscar impresoras
                Impresora impresoraAsignada = null;
                for (Impresora imp : impresoras) {
                    if (imp.getFilamento() >= peso && imp.agendarCita(null)) { // Verificamos que tenga espacio y filamento
                        impresoraAsignada = imp;
                        break;
                    }
                }

                if (impresoraAsignada == null) {
                    System.out.println("No hay impresoras disponibles con suficiente espacio o filamento.");
                    return;
                }


                // Crear la cita
                Cita nuevaCita = new Cita(impresoraAsignada, peso, fechaInicio, duracion, this);
                citas.add(nuevaCita);

                impresoraAsignada.agendarCita(nuevaCita);

                System.out.println("Cita agendada exitosamente en la impresora " + impresoraAsignada.getIdImpresora());

            } catch (Exception e) {
                System.out.println("Error al ingresar los datos. Verifica el formato.");
            }
    }

    public void cancelar() {
        System.out.println("prueba cancelar");
    }

    public void setIdCita(String cita){
        idCitaAgendada = cita;
    }


}
