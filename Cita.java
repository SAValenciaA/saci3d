public class Cita {
    private int duracion;
    private Impresora numImpresora;
    private String nombre;
    private String descripcion;
    private int pesoEnGramos;

    // Constructor
    public Cita(int duracion, Impresora numImpresora, String nombre, String descripcion, int pesoEnGramos) {
        this.duracion = duracion;
        this.numImpresora = numImpresora;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pesoEnGramos = pesoEnGramos;
    }

    // Getters
    public int getDuracion() {
        return duracion;
    }

    public Impresora getNumImpresora() {
        return numImpresora;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPesoEnGramos() {
        return pesoEnGramos;
    }

}
