import java.time.LocalDateTime;

public class Anuncio extends Evento{
    public final String mensaje;
    public final LocalDateTime fechaFin;

    //Constructor principal

    public Anuncio(String mensaje,LocalDateTime fechaInicio,LocalDateTime fechaFin, String creador) {
        super(fechaInicio,creador);
        this.mensaje = mensaje;
        this.fechaFin = fechaFin;
    }

    // tostring get y set

    @Override
    public String toString() {
        return "Anuncio: " + mensaje;
    }
}
