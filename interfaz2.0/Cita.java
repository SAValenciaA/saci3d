// Cita.java
import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {
    private String id;
    private String usuario;
    private LocalDate fecha;
    private LocalTime hora;
    private String duracion; // Podría ser int en minutos/horas, pero String por el ejemplo "10 Horas"

    public Cita(String id, String usuario, LocalDate fecha, LocalTime hora, String duracion) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.duracion = duracion;
    }

    // Getters para acceder a los datos de la cita
    public String getId() { return id; }
    public String getUsuario() { return usuario; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public String getDuracion() { return duracion; }

    @Override
    public String toString() {
        return "ID: " + id + ", Usuario: " + usuario + ", Fecha: " + fecha + ", Hora: " + hora + ", Duracion: " + duracion;
    }
}