import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PantallaAdministrativa {
    private Gestor gestor;

    public PantallaAdministrativa(Gestor gestor) {
        this.gestor = gestor;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Panel de Administrador");

        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label titulo = new Label("Estado de las impresoras");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        HBox contenedorImpresoras = new HBox(20);
        contenedorImpresoras.setStyle("-fx-alignment: center;");

        for (Impresora impresora : gestor.getListaImpresoras()) {
            VBox impresoraBox = new VBox(5);
            impresoraBox.setStyle("-fx-alignment: center;");

            //Imagen impresora

            Image imagenImpresora = new Image("file:impresora.png");
            javafx.scene.image.ImageView vistaImagen = new javafx.scene.image.ImageView(imagenImpresora);
            vistaImagen.setFitWidth(80);
            vistaImagen.setFitHeight(80);


            Label nombreImpresora = new Label("Impresora " + impresora.getId());

            ComboBox<String> disponibilidad = new ComboBox<>();
            disponibilidad.getItems().addAll("Disponible", "Ocupada");
            disponibilidad.setValue(impresora.isDisponible() ? "Disponible" : "Ocupada");

            disponibilidad.setOnAction(e -> {
                boolean disponible = disponibilidad.getValue().equals("Disponible");
                impresora.setDisponible(disponible);
            });

            impresoraBox.getChildren().addAll(vistaImagen, nombreImpresora, disponibilidad);
            contenedorImpresoras.getChildren().add(impresoraBox);
        }

        Button btnCambiarTopes = new Button("Cambiar topes");
        btnCambiarTopes.setOnAction(e -> {
            PantallaTopes pantallaTopes = new PantallaTopes();
            pantallaTopes.mostrar();
        });

        vbox.getChildren().addAll(titulo, contenedorImpresoras, btnCambiarTopes);

        Scene scene = new Scene(vbox, 700, 500);
        stage.setScene(scene);
        stage.show();
    }
}
