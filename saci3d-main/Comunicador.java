import java.time.LocalDateTime;
import java.util.ArrayList;

public interface Comunicador {
    void anunciar(String mensaje, ArrayList<Anuncio> anuncios, LocalDateTime fechaInicio, int duracion, Usuario destinatario);
}
