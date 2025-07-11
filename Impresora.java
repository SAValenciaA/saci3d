import java.util.*;
import java.sql.SQLException;

public class Impresora{
    public final int idImpresora ;
    private boolean disponible;
    private double filamento;
    private String razon;
    private static ArrayList<Impresora> impresoras = null;


    public Impresora(int id, double filamento, boolean disponible, String razon) throws SQLException{
      // TODO: Make better id following
        this.idImpresora = id == -1 ? impresoras.size() : id;
        this.filamento = filamento;
        this.disponible = true;
        this.razon = razon;
        impresoras.add(this);
        Database.uploadPrinter(this);
    }
    
    public static ArrayList<Impresora> getImpresoras() {

      try {
        impresoras = impresoras == null ? 
                     Database.loadPrinters() : impresoras;
      } catch(SQLException e) {
        System.out.println("Database error");
        System.out.println(e);
      }
      return impresoras;
    }

    public double getFilamento(){
        return this.filamento;
    }
    public String getRazon() {
      return this.razon;
    }
    public double setFilamento(double dfilamento) {
      this.filamento += dfilamento;
      return filamento;
    }

    public void agendarCita(Cita cita) {
      filamento -= cita.pesoFilamento;
    }
    
    public boolean isDisponible() {
        return disponible;
    }

    // set y calculos utiles
    
    public void setDisponible() {
    this.disponible = !disponible;
    }

    // to string 
    
    public String toString() {
        return idImpresora + "Disponible: "+ disponible + "Filamento: "+ filamento;
    }
}
