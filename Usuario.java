
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Usuario implements interfazGetId {
    private String id;
    private String nombre;
    private String usuario;
    private ArrayList<String> idCitasAgendadas;
    private String contraseña;
    protected static int tope=1;
    // Constructor
    public Usuario(String id, String nombre, String usuario, String contraseña) {

        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.idCitasAgendadas = new ArrayList<>();
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

    @Override
    public String getId() {
        return id;
    }
    public void getIdCitas(){
        for(String cita:idCitasAgendadas){
            System.out.println(cita);
        }
    }


    public void consultar(ArrayList<Cita> citas) {
        Evento.actualizarTodasLasCitas(citas);
        for(Cita cita: citas){
            if (cita.getEstado()==true){
                System.out.println(cita);
            }
            
        }
        
    }

    public void agendar(Scanner sc, List<Cita> citas,  List<Impresora> impresoras) {
        if(idCitasAgendadas.size()<Usuario.tope){
            try {
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime fechaInicio = null;
                
                // validar fecha cita
                // Bucle hasta que se ingrese una fecha válida
                while (true) {
                    try {
                        System.out.println("Ingrese la fecha y hora de inicio (formato: yyyy-MM-dd HH:mm):");
                        String entradaFecha = sc.nextLine();
                        fechaInicio = LocalDateTime.parse(entradaFecha, formato);

                        if (!Evento.validarFecha(fechaInicio)) {
                            System.out.println("La fecha ingresada ya pasó. Intente con una fecha futura.");
                        } else {
                            break; // fecha válida
                        }
                    } catch (Exception e) {
                        System.out.println("Formato de fecha inválido. Intente nuevamente.");
                    }
                }
                

                System.out.println("Ingrese la duración en minutos:");
                int duracion = sc.nextInt();
                sc.nextLine(); // limpiar salto


                // verificar disponibilidad 

                LocalDateTime fechaFin = fechaInicio.plusMinutes(duracion);
                if (Evento.seSuperpone(fechaInicio, fechaFin, citas)) {
                    System.out.println("Ya hay una cita programada en ese horario. Intente con otro horario.");
                    return;
                }

                System.out.println("Ingrese el peso del filamento:");
                double peso = sc.nextDouble();
                sc.nextLine(); // limpiar salto

                // Buscar impresoras
                Impresora impresoraAsignada = null;
                for (Impresora imp : impresoras) {
                    if (imp.getFilamento() >= peso && imp.agendarCita(null) && imp.isDisponible()) { // Verificamos que tenga espacio y filamento
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

                idCitasAgendadas.add(nuevaCita.getId());

                citas.add(nuevaCita);

                impresoraAsignada.agendarCita(nuevaCita);

                System.out.println("Cita agendada exitosamente en la impresora " + impresoraAsignada.getId());

            } catch (Exception e) {
                System.out.println("Error al ingresar los datos. Verifica el formato.");
            }
        }
        else{
            System.out.println("Has alcanzado el número máximo de reservas/cancelaciones esta semana.");
        }
            
    }

    public void cancelar(ArrayList<Cita> citas,String idCita) {
        boolean toBe=false;
        for(int j=0;j<idCitasAgendadas.size();j++){
            if(idCita.equals(idCitasAgendadas.get(j))){
                toBe=true;
            }
        }
        if(toBe==true){
            int indice = -1;
            for (int i = 0; i < citas.size(); i++) {
                if(citas.get(i).getId().equals(idCita)){
                    indice = i;
                    citas.get(i).getNumImpresora().setDisponible();
                }
            }
            citas.remove(indice);
            System.out.println("cita eliminada con exito");
            }
        
        else{
            System.out.println("No tienes agendada esta cita");
        }
    }
}



