
import java.time.LocalDateTime;

public class Cita extends Evento  {
    private Impresora numImpresora;
    private String usuario;
    private double pesoFilamento;

    // Constructor
    public Cita(Impresora numImpresora, double pesoFilamento, LocalDateTime fechaInicio, int duracion,Usuario destinatario) {
        super(fechaInicio,destinatario,duracion);
        this.numImpresora = numImpresora;
        this.pesoFilamento = pesoFilamento;
        Impresora.minusFila(pesoFilamento);

    }
    public Impresora getNumImpresora() {
        return numImpresora;
    }

    public String getUsuario(){
        return usuario;
    }

    public double  getPesoEnGramos() {
        return pesoFilamento;
    }

    public String toString(){
        return getUsuario()+" "+getDuracion()+"\n";
    }

   
    public void estadoActual(){
        System.out.println();
    }


}
