import java.time.LocalDateTime;
import java.util.ArrayList;

public class Profesor extends Usuario{
    
    public Profesor(String id, String nombre, String usuario, String clave){
        super(id,nombre,usuario,clave);
    }
    // metodo util
    public void anunciar(String mensaje, ArrayList<Anuncio> listaAnuncios,LocalDateTime fechaInicio, int duracion,Usuario destinatario) {
        listaAnuncios.add(new Anuncio(mensaje,fechaInicio,duracion,destinatario));
    }
}
