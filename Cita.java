
import java.time.LocalDateTime;

public class Cita extends Evento  {
    public Impresora numImpresora;
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


    public double  getPesoEnGramos() {
        return pesoFilamento;
    }

    public String toString(){
        return getCreador()+" "+getDuracion()+" "+getId()+"\n";
    }

    public void estadoActual(){
        System.out.println();
    }


}
