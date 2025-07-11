import java.time.LocalDateTime;
import java.util.*;

public class Cita extends Evento  {

  public int numImpresora;
  public final double pesoFilamento;
  public int duracion;
  public static ArrayList<Cita> citas = (ArrayList<Cita>)Database.selectEvents(Database.CITAS, "*");

    // Constructor
    public Cita(int numImpresora, double pesoFilamento, LocalDateTime fechaInicio, int duracion,String creador) {
        super(fechaInicio, creador);
        this.numImpresora = numImpresora;
        this.pesoFilamento = pesoFilamento;
        this.duracion = duracion;
        
        Impresora.minusFila(pesoFilamento);

        citas.add(this);

    }
    
    // getters

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
    public static boolean seSuperponeCon(LocalDateTime inicioNueva, LocalDateTime finNueva) {
        LocalDateTime inicioNueva = nueva.getFechaInicio();
        LocalDateTime finNueva = nueva.getFechaInicio.plusMinutes(nueve.duracion);

        for (Cita vieja : this.citas) {
          LocalDateTime inicioVieja = vieja.getFechaInicio();
          LocalDateTime finVieja = vieja.plusMinutes(existente.duracion);

          // Verifica superposición
          if (inicioVieja.isBefore(finNueva) 
              && inicioNueva.isBefore(finVieja)
              && vieja.numImpresora == nueva.numImpresora) {
              return true;
          }
        }
        return false;
      }

    public static boolean seSuperpone(LocalDateTime nuevaInicio, LocalDateTime nuevaFin) {
      for (Cita c : listaCitas) {
          if (c == null || !c.getEstado()) continue;  // Ignora citas eliminadas o pasadas

          LocalDateTime inicioExistente = c.getFechaInicio();
          LocalDateTime finExistente = c.getFechaFinal();

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
        actualizarEstado(); 
        return getCreador() + " " + getFechaInicio() + " " + "Hasta: " + getFechaFinal()+ " " + getId() + "\n";
    }



}
