public class Impresora implements interfazGetId{
    final private String idImpresora ;
    private boolean disponible;
    private Cita[] proximasCitas;
    public static double filamento;


    public Impresora(String idImpresora, int capacidadMaxCitas) {
        this.idImpresora = idImpresora;
        this.disponible = true;
        this.proximasCitas = new Cita[capacidadMaxCitas]; 
        this.filamento=100;
    }

    public double getFilamento(){
        return filamento;
    }
    public boolean enUso() {
        for (Cita cita : proximasCitas) {
            if (cita != null) return true;
        }
        return false;
    }

    public String getId() {
        return idImpresora;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Cita[] getProximasCitas() {
        return proximasCitas;
    }
    
    public static void minusFila(double valor){
        filamento-=valor;
    }

    public void setDisponible(boolean var1) {
    this.disponible = var1;
    }

    public boolean agendarCita(Cita nuevaCita) {
        for (int i = 0; i < proximasCitas.length; i++) {
            if (proximasCitas[i] == null) {
                proximasCitas[i] = nuevaCita;
                return true;
            }
        }
        return false; 
    }
}
