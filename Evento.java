import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import  java.util.ArrayList;

abstract class Evento {

    public final int id;
    public final LocalDateTime fechaInicio;
    public final String creador;
    public static ArrayList<Evento>eventos; 

    public static int numEventos=0;
    
    // constructor

    public Evento(LocalDateTime fechaInicio, String creador){
        numEventos++;
        this.id = Evento.createId(fechaInicio);
        this.fechaInicio=fechaInicio;
        this.creador=creador;
        eventos.add(this);
    }

    public LocalDateTime getFechaInicio(){
        return fechaInicio;
    }
    
    // funciones de validacion de fechas

    public boolean validarFecha() {
        return !LocalDateTime.now().isAfter(fechaInicio);
    }

    public static int createId(LocalDateTime date) {
      DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
      return Integer.parseInt(format.format(date));
    }


}
