public class Impresora {
    final private int idImpresora ;
    private boolean disponible;
    private Cita[] proximasCitas;


    public Impresora(int idImpresora, int capacidadMaxCitas) {
        this.idImpresora = idImpresora;
        this.disponible = true;
        this.proximasCitas = new Cita[capacidadMaxCitas]; 
    }


    public boolean enUso() {
        for (Cita cita : proximasCitas) {
            if (cita != null) return true;
        }
        return false;
    }

    public int getIdImpresora() {
        return idImpresora;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Cita[] getProximasCitas() {
        return proximasCitas;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
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
