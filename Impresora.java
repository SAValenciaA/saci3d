import java.util.*;
import java.sql.SQLException;

public class Impresora{
    public final int idImpresora ;
    private boolean disponible;
    private double filamento;
    private String razon;
    public static ArrayList<Impresora> impresoras = Database.loadPrinters();


    public Impresora(int id, double filamento, boolean disponible, String razon) throws SQLException{
      // TODO: Make better id following
      this.idImpresora = impresoras.size() != 0 ? 
                          impresoras
                            .get(impresoras.size() - 1)
                            .idImpresora :
                          0;

      this.filamento = filamento;
      this.disponible = true;
      this.razon = razon;
      impresoras.add(this);
      Database.uploadPrinter(this);
    }
    
    public static Impresora getImpresora(int id){
      for(Impresora impresora : impresoras) {
        if(id == impresora.idImpresora) {
          return impresora;
        }
      }
      return null;
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
