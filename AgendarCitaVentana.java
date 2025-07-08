import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class AgendarCitaVentana {
    private int dia;
    private Usuario usuario;
    private Gestor gestor;

    public AgendarCitaVentana(int dia, Usuario usuario, Gestor gestor) {
        this.dia = dia;
        this.usuario = usuario;
        this.gestor = gestor;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Agendar Cita");

        // Selección de Impresora
        ArrayList<Impresora> impresoras = gestor.getListaImpresoras();
        ComboBox<String> comboImpresoras = new ComboBox<>();
        for (Impresora impresora : impresoras) {
            comboImpresoras.getItems().add(impresora.getId());
        }

        // Selección de hora y duración
        TextField campoHora = new TextField();
        campoHora.setPromptText("Hora de inicio (Ej: 9 para 9:00am)");

        TextField campoDuracion = new TextField();
        campoDuracion.setPromptText("Duracion (minutos)");

        // Peso de filamento
        TextField campoPeso = new TextField();
        campoPeso.setPromptText("Peso del filamento (gramos)");

        // Botón Agendar
        Button btnAgendar = new Button("Agendar");
        btnAgendar.setOnAction(e -> {
            try {
                String idImpresora = comboImpresoras.getValue();
                int hora = Integer.parseInt(campoHora.getText());
                int duracion = Integer.parseInt(campoDuracion.getText());
                double peso = Double.parseDouble(campoPeso.getText());

                if (usuario.topeSemanalUsuario >= Usuario.tope) {
                    mostrarError("Has alcanzado tu tope semanal de citas. Espera hasta la próxima semana.");
                return;
                }

                Impresora impresoraSeleccionada = null;
                for (Impresora imp : impresoras) {
                    if (imp.getId().equals(idImpresora)) {
                        impresoraSeleccionada = imp;
                        break;
                    }
                }

                if (impresoraSeleccionada == null) {
                    mostrarError("Selecciona una impresora válida.");
                    return;
                }

                // Crear cita con fecha completa
                LocalDateTime fechaInicio = LocalDateTime.of(2025, 6, dia, hora, 0);
                Cita nuevaCita = new Cita(impresoraSeleccionada, peso, fechaInicio, duracion, usuario);

                gestor.registrarCita(nuevaCita);
                usuario.topeSemanalUsuario++;

                mostrarMensaje("Cita registrada con exito.");
                stage.close();
            } catch (Exception ex) {
                mostrarError("Datos invalidos, revisa los campos.");
            }
        });

        VBox vbox = new VBox(10,
                new Label("Selecciona Impresora:"),
                comboImpresoras,
                campoHora,
                campoDuracion,
                campoPeso,
                btnAgendar
        );
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
