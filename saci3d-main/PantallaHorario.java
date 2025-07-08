import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class PantallaHorario {
    private LocalDate fecha;
    private Gestor gestor;
    private Usuario usuario;  // Agregado para pasar el usuario actual

    // Modificamos el constructor para recibir el usuario
    public PantallaHorario(LocalDate fecha, Gestor gestor, Usuario usuario) {
        this.fecha = fecha;
        this.gestor = gestor;
        this.usuario = usuario;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Horario del " + fecha.toString());

        // Título
        Label titulo = new Label("Listado de horario para citas del " + fecha.toString());
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox contenedorHorario = new VBox(10);
        contenedorHorario.setStyle("-fx-padding: 10; -fx-alignment: center;");

        // Horario de 7:00 a 18:00
        for (int hora = 7; hora <= 18; hora++) {
            HBox fila = new HBox(10);
            Label horaLabel = new Label(String.format("%02d:00", hora));

            int impresorasLibres = contarImpresorasLibresEnHora(hora);
            String estado;
            if (impresorasLibres == 0) {
                estado = "Ocupado";
            } else if (impresorasLibres == 1) {
                estado = "Libre 1 impresora";
            } else {
                estado = "Libre " + impresorasLibres + " impresoras";
            }

            Label estadoLabel = new Label(estado);
            fila.getChildren().addAll(horaLabel, estadoLabel);
            contenedorHorario.getChildren().add(fila);
        }

        // Scroll
        ScrollPane scrollPane = new ScrollPane(contenedorHorario);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-padding: 10;");

        // Botón Agendar (ya con funcionalidad)
        Button btnAgendar = new Button("Agendar");
        btnAgendar.setStyle("-fx-font-size: 16px;");
        btnAgendar.setOnAction(e -> {
            // Abre la ventana de agendar cita
            AgendarCitaVentana agendarVentana = new AgendarCitaVentana(
                    fecha.getDayOfMonth(),
                    usuario,
                    gestor
            );
            agendarVentana.mostrar();
        });

        VBox layout = new VBox(15, titulo, scrollPane, btnAgendar);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    // Método para contar impresoras libres por hora
    private int contarImpresorasLibresEnHora(int hora) {
        int totalImpresoras = gestor.getListaImpresoras().size();
        int ocupadas = 0;
        ArrayList<Cita> citas = gestor.getListaCitas();

        for (Cita cita : citas) {
            if (cita.getFechaInicio().toLocalDate().equals(fecha)) {
                int horaInicio = cita.getFechaInicio().getHour();
                int horaFin = cita.getFechaFinal().getHour();
                if (hora >= horaInicio && hora < horaFin) {
                    ocupadas++;
                }
            }
        }

        int libres = totalImpresoras - ocupadas;
        return Math.max(libres, 0);
    }
}
