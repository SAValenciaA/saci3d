import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;

public class PantallaAgendar {

    private Usuario usuario;
    private Gestor gestor;
    private int diaSeleccionado;

    public PantallaAgendar(Usuario usuario, Gestor gestor, int diaSeleccionado) {
        this.usuario = usuario;
        this.gestor = gestor;
        this.diaSeleccionado = diaSeleccionado;
    }

    public void mostrar() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label titulo = new Label("Agendar Cita para el Día " + diaSeleccionado);
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Selección de hora
        Label lblHora = new Label("Hora de inicio (7 a 17):");
        Spinner<Integer> spinnerHora = new Spinner<>(7, 17, 7);
        
        // Duración
        Label lblDuracion = new Label("Duración (minutos):");
        Spinner<Integer> spinnerDuracion = new Spinner<>(30, 240, 60, 30);
        
        // Peso de filamento
        Label lblPeso = new Label("Peso estimado del filamento (gramos):");
        Spinner<Double> spinnerPeso = new Spinner<>(1.0, 100.0, 10.0, 1.0);

        // Impresora (elige entre 3)
        Label lblImpresora = new Label("Selecciona Impresora:");
        ComboBox<String> comboImpresora = new ComboBox<>();
        comboImpresora.getItems().addAll("IMP-01", "IMP-02", "IMP-03");
        comboImpresora.getSelectionModel().selectFirst();

        Button btnAgendar = new Button("Agendar Cita");
        btnAgendar.setOnAction(e -> {
            int horaInicio = spinnerHora.getValue();
            int duracion = spinnerDuracion.getValue();
            double peso = spinnerPeso.getValue();
            String impresoraId = comboImpresora.getValue();

            LocalDateTime fechaInicio = LocalDateTime.now().withDayOfMonth(diaSeleccionado)
                                                      .withHour(horaInicio).withMinute(0).withSecond(0).withNano(0);

            Impresora impresora = new Impresora(impresoraId, 10); // Puedes ajustar la capacidad

            Cita nuevaCita = new Cita(impresora, peso, fechaInicio, duracion, usuario);
            gestor.registrarCita(nuevaCita);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cita Agendada");
            alert.setHeaderText(null);
            alert.setContentText("Cita agendada con éxito para el día " + diaSeleccionado);
            alert.showAndWait();

            stage.close();
        });

        layout.getChildren().addAll(titulo, lblHora, spinnerHora, lblDuracion, spinnerDuracion, 
                                    lblPeso, spinnerPeso, lblImpresora, comboImpresora, btnAgendar);

        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Agendar Cita");
        stage.show();
    }
}
