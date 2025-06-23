public class Evento {
    private int duracion;
    private Impresora numImpresora;
    private String nombre;
    private String descripcion;
    private int pesoEnGramos;

    // Constructor
    public Evento(int duracion, Impresora numImpresora, String nombre, String descripcion, int pesoEnGramos) {
        this.duracion = duracion;
        this.numImpresora = numImpresora;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pesoEnGramos = pesoEnGramos;
    }
}
