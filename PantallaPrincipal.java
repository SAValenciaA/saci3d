import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ArrayList;

public class PantallaPrincipal {
    private Usuario usuario;
    private Gestor gestor;

    public PantallaPrincipal(Usuario usuario, Gestor gestor) {
        this.usuario = usuario;
        this.gestor = gestor;
    }

    public void mostrar() {
        Stage stage = new Stage();

        // Bienvenida
        String tipoUsuario = usuario.getClass().getSimpleName();
        Label bienvenida = new Label("Bienvenido " + tipoUsuario + " " + usuario.getNombre());
        bienvenida.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Sección superior (Mes, Tope semanal, Ver mis citas)
        LocalDate fechaActual = LocalDate.now();
        String mes = fechaActual.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        Label mesLabel = new Label(mes + " / " + fechaActual.getYear());
        Label topeLabel = new Label("Tope semanal restante: " + (Usuario.tope - usuario.topeSemanalUsuario));
        Button btnVerCitas = new Button("Ver mis citas");

        HBox seccionSuperior = new HBox(20, mesLabel, topeLabel, btnVerCitas);
        seccionSuperior.setStyle("-fx-alignment: center; -fx-padding: 10;");

        // Mostrar botón "Realizar anuncio" solo para Profesor o Administrador
        if (usuario instanceof Profesor || usuario instanceof Administrador) {
            Button btnAnunciar = new Button("Realizar anuncio");
            btnAnunciar.setOnAction(e -> mostrarVentanaAnuncio());
            seccionSuperior.getChildren().add(btnAnunciar);

            // Mostrar boton de Panel de administracion
            if (usuario instanceof Administrador) {
                Button btnPanelAdmin = new Button("Panel de Administrador");
                btnPanelAdmin.setOnAction(e -> {
                    PantallaAdministrativa pantallaAdmin = new PantallaAdministrativa(gestor);
                pantallaAdmin.mostrar();
            });
            seccionSuperior.getChildren().add(btnPanelAdmin);
            }
        }

        // Calendario
        GridPane calendario = new GridPane();
        calendario.setHgap(10);
        calendario.setVgap(10);
        calendario.setStyle("-fx-padding: 10; -fx-alignment: center;");

        int diasDelMes = fechaActual.lengthOfMonth();
        int columna = 0, fila = 0;

        for (int dia = 1; dia <= diasDelMes; dia++) {
            int diaFinal = dia;
            Button btnDia = new Button(String.valueOf(dia));
            btnDia.setOnAction(e -> {
                LocalDate fechaSeleccionada = LocalDate.of(
                    LocalDate.now().getYear(),
                    LocalDate.now().getMonth(),
                    diaFinal
                );
                PantallaHorario pantalla = new PantallaHorario(fechaSeleccionada, gestor, usuario);
                pantalla.mostrar();
            });
            calendario.add(btnDia, columna, fila);

            columna++;
            if (columna == 7) {
                columna = 0;
                fila++;
            }
        }

        // Acción "Ver mis citas"
        btnVerCitas.setOnAction(e -> mostrarMisCitas());

        VBox layout = new VBox(15, bienvenida, seccionSuperior, calendario);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Panel Principal - SACI3D");
        stage.show();
    }

    // Muestra las citas del usuario
    private void mostrarMisCitas() {
        Stage stage = new Stage();
        stage.setTitle("Mis Citas");

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        ArrayList<Cita> citas = gestor.getListaCitas();
        boolean tieneCitas = false;

        for (Cita cita : citas) {
            if (cita.getCreador().equals(usuario)) {
                tieneCitas = true;
                HBox fila = new HBox(10);
                Label lbl = new Label("Cita en impresora " + cita.getNumImpresora().getId() +
                        " el " + cita.getFechaInicio() + " durante " + cita.getDuracion() + " min.");
                Button btnEliminar = new Button("-");
                btnEliminar.setOnAction(e -> {
                    gestor.eliminarCita(cita);
                    stage.close();
                    mostrarMisCitas();  // Actualiza la ventana después de eliminar
                });

                fila.getChildren().addAll(lbl, btnEliminar);
                vbox.getChildren().add(fila);
            }
        }

        if (!tieneCitas) {
            vbox.getChildren().add(new Label("No tienes citas registradas."));
        }

        Scene scene = new Scene(vbox, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    // Abre la ventana real para crear un anuncio
    private void mostrarVentanaAnuncio() {
        PantallaAnuncio pantallaAnuncio = new PantallaAnuncio(usuario, gestor);
        pantallaAnuncio.mostrar();
    }

    private void mostrarPanelAdministrador() {
        Stage stage = new Stage();
        stage.setTitle("Panel de Administrador");

        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
        vbox.getChildren().add(new Label("Aquí van las funciones avanzadas de administración."));

        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }


}
