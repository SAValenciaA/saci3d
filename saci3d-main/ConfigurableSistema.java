import java.util.ArrayList;

public interface ConfigurableSistema {
    void cambiarDispo(ArrayList<Impresora> impresoras, String idImpresora);
    void cambiarTopeSemanal(int nuevoTope);
    void cambiarTopeDiario(int nuevoTope);
}
