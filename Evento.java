import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import  java.util.ArrayList;

abstract class Evento {

    public final long id;
    public final LocalDateTime fechaInicio;
    public final String creador;

    public static int numEventos=0;
    
    // constructor

    public Evento(LocalDateTime fechaInicio, String creador){
        numEventos++;
        this.id = Evento.createId(fechaInicio);
        this.fechaInicio=fechaInicio;
        this.creador=creador;
    }

    public LocalDateTime getFechaInicio(){
        return fechaInicio;
    }
    
    // funciones de validacion de fechas

    public boolean validarFecha() {
        return !LocalDateTime.now().isAfter(fechaInicio);
    }

    public static long createId(LocalDateTime date) {
      DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
      return Long.valueOf(format.format(date));
    }


}
