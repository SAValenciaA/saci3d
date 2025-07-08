import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;

public class PantallaAnuncio {
    private Usuario usuario;
    private Gestor gestor;

    public PantallaAnuncio(Usuario usuario, Gestor gestor) {
        this.usuario = usuario;
        this.gestor = gestor;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Realizar Anuncio");

        Label titulo = new Label("Realice su anuncio");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextArea areaMensaje = new TextArea();
        areaMensaje.setPromptText("Escriba aqui su anuncio...");
        areaMensaje.setPrefRowCount(5);
        areaMensaje.setWrapText(true);

        Button btnPublicar = new Button("Publicar");
        btnPublicar.setOnAction(e -> {
            String mensaje = areaMensaje.getText().trim();
            if (mensaje.isEmpty()) {
                mostrarError("El mensaje no puede estar vacío.");
                return;
            }

            LocalDateTime ahora = LocalDateTime.now();
            int duracion = 60; // minutos (puedes cambiar esto si deseas)
            
            if (usuario instanceof Profesor) {
                gestor.getListaAnuncios().add(new Anuncio(mensaje, ahora, duracion, null));
                mostrarExito("Anuncio publicado (visible solo para estudiantes).");
            } else if (usuario instanceof Administrador) {
                gestor.getListaAnuncios().add(new Anuncio(mensaje, ahora, duracion));
                mostrarExito("Anuncio publicado (visible para todos los usuarios).");
            } else {
                mostrarError("Tu rol no tiene permiso para publicar anuncios.");
                return;
            }

            stage.close();
        });

        VBox layout = new VBox(15, titulo, areaMensaje, btnPublicar);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 500, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
