import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PantallaTopes {
    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Cambiar Topes");

        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label lblDiarioActual = new Label("Tope Diario Actual: " + Usuario.topeDiario);
        Label lblSemanalActual = new Label("Tope Semanal Actual: " + Usuario.tope);

        TextField txtNuevoDiario = new TextField();
        txtNuevoDiario.setPromptText("Nuevo Tope Diario");

        TextField txtNuevoSemanal = new TextField();
        txtNuevoSemanal.setPromptText("Nuevo Tope Semanal");

        Button btnGuardar = new Button("Guardar Cambios");
        btnGuardar.setOnAction(event -> {
            try {
                int nuevoDiario = Integer.parseInt(txtNuevoDiario.getText());
                int nuevoSemanal = Integer.parseInt(txtNuevoSemanal.getText());

                Usuario.tope = nuevoSemanal;
                Usuario.topeDiario = nuevoDiario;


                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Topes actualizados correctamente.");
                alerta.showAndWait();
                stage.close();
            } catch (NumberFormatException ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Por favor ingresa números válidos.");
                alerta.showAndWait();
            }
        });

        vbox.getChildren().addAll(lblDiarioActual, lblSemanalActual, txtNuevoDiario, txtNuevoSemanal, btnGuardar);

        Scene scene = new Scene(vbox, 350, 300);
        stage.setScene(scene);
        stage.show();
    }
}
