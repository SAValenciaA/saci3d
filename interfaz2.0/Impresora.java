// Impresora.java
public class Impresora {
    private int numero;
    private double filamentoActualGramos;
    private double filamentoNoReservadoGramos;
    private boolean disponible; // true si está disponible, false si no
    private String mensajeNoDisponible; // Mensaje si no está disponible

    public Impresora(int numero, double filamentoActualGramos, double filamentoNoReservadoGramos) {
        this.numero = numero;
        this.filamentoActualGramos = filamentoActualGramos;
        this.filamentoNoReservadoGramos = filamentoNoReservadoGramos;
        this.disponible = true; // Por defecto, disponible
        this.mensajeNoDisponible = null;
    }

    public Impresora(int numero, boolean disponible, String mensajeNoDisponible) {
        this.numero = numero;
        this.disponible = disponible;
        this.mensajeNoDisponible = mensajeNoDisponible;
        // Valores por defecto si no está disponible
        this.filamentoActualGramos = 0;
        this.filamentoNoReservadoGramos = 0;
    }

    // Getters
    public int getNumero() { return numero; }
    public double getFilamentoActualGramos() { return filamentoActualGramos; }
    public double getFilamentoNoReservadoGramos() { return filamentoNoReservadoGramos; }
    public boolean isDisponible() { return disponible; }
    public String getMensajeNoDisponible() { return mensajeNoDisponible; }

    // Setters (si necesitas cambiar el estado de la impresora, por ejemplo)
    public void setFilamentoActualGramos(double filamentoActualGramos) { this.filamentoActualGramos = filamentoActualGramos; }
    public void setFilamentoNoReservadoGramos(double filamentoNoReservadoGramos) { this.filamentoNoReservadoGramos = filamentoNoReservadoGramos; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public void setMensajeNoDisponible(String mensajeNoDisponible) { this.mensajeNoDisponible = mensajeNoDisponible; }

    @Override
    public String toString() {
        if (disponible) {
            return "Impresora Nº: " + numero + "\n" +
                   "Filamento Actual: " + filamentoActualGramos + "g\n" +
                   "Filamento no reservado: " + filamentoNoReservadoGramos + "g";
        } else {
            return "Impresora Nº: " + numero + "\n" +
                   "No disponible: " + mensajeNoDisponible;
        }
    }
}