import java.time.LocalDateTime;
import java.sql.SQLException;
import java.util.*;

public class Anuncio extends Evento{
    public final String mensaje;
    public final LocalDateTime fechaFin;
    private static ArrayList<Anuncio> anuncios = null;

    //Constructor principal
    public Anuncio(String mensaje, LocalDateTime fechaInicio, LocalDateTime fechaFin, String creador, boolean isInDatabase) {
        super(fechaInicio,creador);
        this.mensaje = mensaje;
        this.fechaFin = fechaFin;
        if(! isInDatabase) {
          try{
            Database.uploadAnuncio(this);
          } catch(SQLException e) {
            System.out.println(e);
          }
        }
    }

    public Anuncio(String mensaje,LocalDateTime fechaInicio,LocalDateTime fechaFin, String creador) {
      this(mensaje,fechaInicio,fechaFin, creador, false);
    }
    public static ArrayList<Anuncio> getAnuncios(){
      if(anuncios != null) {
        return anuncios;
      }
      try {
        anuncios = Database.selectAnuncios("*");
      } catch(SQLException e) {
        System.out.println(e);
      }
      return anuncios;
    }

    public static ArrayList<Anuncio> getAnunciosValidos() {
      ArrayList<Anuncio> anunciosValidos = new ArrayList<Anuncio>();

      for(Anuncio anuncio : getAnuncios()) {
        if(LocalDateTime.now().isAfter(anuncio.fechaInicio)&&
           LocalDateTime.now().isBefore(anuncio.fechaFin)) {
            anunciosValidos.add(anuncio);
        }
      }
      return anunciosValidos;
    }

    // tostring get y set

    @Override
    public String toString() {
        return "Anuncio: " + mensaje;
    }
}
