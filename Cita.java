import java.time.LocalDateTime;
import java.sql.SQLException;
import java.util.*;

public class Cita extends Evento  {

  public int numImpresora;
  public final double pesoFilamento;
  public int duracion;
  private static ArrayList<Cita> citas = null;

    // Constructor
    public Cita(int numImpresora, double pesoFilamento, LocalDateTime fechaInicio, int duracion,String creador) {
        super(fechaInicio, creador);
        this.numImpresora = numImpresora;
        this.pesoFilamento = pesoFilamento;
        this.duracion = duracion;
        
        Impresora.getImpresora(numImpresora).agendarCita(this);

        citas.add(this);
        try {
          Database.uploadEvent(this);
        } catch(SQLException e) {
          e.printStackTrace(System.err);
        }
    }
    

    public static ArrayList<Cita> getCitas() {
      try {
        citas = citas == null ? Database.selectCitas("*") : citas;
      } catch(SQLException e) {
        System.out.println(e);
      }
      return citas;
    }
    public int getNumImpresora() {
        return numImpresora;
    }

    public double  getPesoEnGramos() {
        return pesoFilamento;
    }

    /*
     * Esta funcion busca si una cita que se va a agendar
     * se cruza con una ya existente
     * 
     * @param 
     */
    public static boolean seSuperpone(LocalDateTime nuevaInicio, LocalDateTime nuevaFin) {
      for (Cita c : citas) {
          if (c == null || !c.validarFecha()) continue;  // Ignora citas eliminadas o pasadas

          LocalDateTime inicioExistente = c.getFechaInicio();
          LocalDateTime finExistente = inicioExistente.plusMinutes(c.duracion);

          // Se superponen si ambos intervalos se cruzan
          if (nuevaInicio.isBefore(finExistente) && nuevaFin.isAfter(inicioExistente)) {
              return true;
          }
      }
      return false;
    }

    //toString
    @Override
    public String toString() {
        return id + ": " + creador + " " + fechaInicio + " " + "Duracion: " + duracion + "\n";
    }



}
