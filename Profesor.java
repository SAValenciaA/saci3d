import java.time.LocalDateTime;
import java.util.ArrayList;

public class Profesor extends Usuario{
    
    public Profesor(String id, String nombre, String usuario){
        super(id,nombre,usuario);
        this.rol = "profesor";
    }
}
