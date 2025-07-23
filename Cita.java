import java.time.LocalDateTime;
import java.sql.SQLException;
import java.util.*;

public class Cita extends Evento  {

  public int numImpresora;
  public final double pesoFilamento;
  public int duracion;
  private static ArrayList<Cita> citas = null;

    public Cita(int numImpresora, double pesoFilamento, LocalDateTime fechaInicio, int duracion,String creador, boolean redundant) {
      super(fechaInicio, creador);
      this.numImpresora = numImpresora;
      this.pesoFilamento = pesoFilamento;
      this.duracion = duracion;

      if(!redundant) {
        if(citas != null) {
          citas.add(this);
        }

        try {
          Database.uploadCita(this);
        } catch(SQLException e) {
          e.printStackTrace(System.err);
        }
      }
    }

    
    // Getters para acceder a los datos de la cita
    public String getId() { return id; }
    public String getUsuario() { return usuario; }
    public LocalDateTime getFecha() { return fechaInicio; }
    public String getDuracion() { return duracion; }


    /*
     * Este overloading del constructor es para hacer que la variable
     * de redundancia sea por defecto false (osea la cita es 
     * completamente) nueva
     */
    public Cita(int numImpresora, double pesoFilamento, LocalDateTime fechaInicio, int duracion,String creador) {
        this(numImpresora, pesoFilamento, fechaInicio, duracion,creador, false);
    }
    

    /*
     * Si las citas ya estan cargadas, retornarlas, caso contrario
     * pedirselas a la base de datos y guardar la lista en memoria
     */
    public static ArrayList<Cita> getCitas() {
      try {
        citas = citas == null ? Database.selectCitas("*") : citas;
      } catch(SQLException e) {
        System.out.println(e);
      }
      return citas;
    }

    /*
     * Busca las citas validas acorde a un criterio
     *
     * @param creador nombre de las creador de las citas que se buscan
     */
    public static ArrayList<Cita> getCitasByName(String creador){
      ArrayList<Cita> citasEncontradas = new ArrayList<Cita>();
      ArrayList<Cita> citaListaCompleta = Cita.getCitas();

      for(Cita cita : citaListaCompleta) {
        if(cita.creador.equals(creador)){
          citasEncontradas.add(cita);
        }
      }

      return citasEncontradas;
    }


    /*
     * Esta funcion busca todas las citas no vencidas
     *
     * @returns citas no vencidas
     */
    public static ArrayList<Cita> getCitasValidas()  {
      ArrayList<Cita> todasLasCitas = Cita.getCitas();
      ArrayList<Cita> citasValidas = new ArrayList<Cita>();
      for(Cita cita : todasLasCitas) {
        if(cita.validarFecha()) {
          citasValidas.add(cita);
        }
      }
      return citasValidas;
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
        return "id: "+id + "; creada por: " + this.creador + "; para el: " + fechaInicio + "; " + "por: " + duracion + " minutos\n";
    }



}
