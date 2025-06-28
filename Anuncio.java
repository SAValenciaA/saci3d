
import java.time.LocalDateTime;
public class Anuncio extends Evento{
    private String mensajeContenido;

    public Anuncio(String mensajeContenido,LocalDateTime fechaInicio,int duracion,Usuario destinatario) {
        super(fechaInicio,destinatario,duracion);
        this.mensajeContenido = mensajeContenido;
    }

    @Override
    public String toString() {
        return "Anuncio: " + mensajeContenido;
    }

    
    public String getMensajeContenido() {
        return mensajeContenido;
    }

    public void setMensajeContenido(String mensajeContenido) {
        this.mensajeContenido = mensajeContenido;
    }
}
