
import  java.time.LocalDateTime;
abstract class Evento {
    private String idEvento;
    private LocalDateTime  fechaInicio;
    private LocalDateTime fechaFinal;
    private  int duracion;
    private String creador;

    public static int numEventos;

    public Evento(LocalDateTime  fechaInicio, Usuario creador,int duracion){
        numEventos++;
        this.idEvento= 2025+""+numEventos;
        this.duracion=duracion;
        this.fechaInicio=fechaInicio;
        this.fechaFinal=fechaInicio.plusMinutes(duracion);
        this.creador=creador.getUsuario();
    }
    abstract void estadoActual();

    public String getId(){
        return idEvento;
    }
    public String getCreador(){
        return idEvento;
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
}
