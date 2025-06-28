import java.util.ArrayList;
import  java.time.LocalDateTime;
abstract class Evento implements  interfazGetId{
    private String idEvento;
    private LocalDateTime  fechaInicio;
    private LocalDateTime fechaFinal;
    private  int duracion;
    private String creador;
    private boolean estadoActual;

    public static int numEventos;

    public Evento(LocalDateTime  fechaInicio, Usuario creador,int duracion){
        numEventos++;
        this.idEvento= 2025+""+numEventos;
        this.duracion=duracion;
        this.fechaInicio=fechaInicio;
        this.fechaFinal=fechaInicio.plusMinutes(duracion);
        this.creador=creador.getUsuario();
        this.estadoActual = true;
    }

    public void estadoActual(boolean estado){
        this.estadoActual = estado;
    }


    public String getId(){
        return idEvento;
    }
    public String getCreador(){
        return creador;
    }
    public LocalDateTime getFechaInicio(){
        return fechaInicio;
    }
    public LocalDateTime getFechaFinal(){
        return fechaFinal;
    }

    public int getDuracion() {
        return duracion;
    }
    public boolean getEstado(){
        return estadoActual;
    }



    // funciones de validacion de fechas

    public static boolean validarFecha(LocalDateTime fecha) {
        return !LocalDateTime.now().isAfter(fecha);
    }

    public void actualizarEstado() {
        if (LocalDateTime.now().isAfter(fechaFinal)) {
            this.estadoActual = false;
        }

    }

    public static void actualizarTodasLasCitas( ArrayList<Cita> listaCitas) {
        for (Cita c : listaCitas ) {
            if (c != null) {
                c.actualizarEstado();
            }
        }
    }
}
