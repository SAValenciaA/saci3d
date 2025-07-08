import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI extends Application {

    private Gestor gestor = new Gestor();

    @Override
    public void start(Stage primaryStage) {
        Image imagen = new Image(new File("logo-Universidad-Nacional.png").toURI().toString());
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        // Título
        Label titulo = new Label("SACI3D - Sistema de Agendamiento");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Campos de usuario y clave
        Label labelUsuario = new Label("Usuario:");
        TextField campoUsuario = new TextField();

        Label labelClave = new Label("Clave:");
        PasswordField campoClave = new PasswordField();

        Button botonLogin = new Button("Iniciar sesion");
        Label mensaje = new Label();

        // Cambiar tamaño de letra (agrandar)
        labelUsuario.setStyle("-fx-font-size: 18px;");
        campoUsuario.setStyle("-fx-font-size: 18px;");
        labelClave.setStyle("-fx-font-size: 18px;");
        campoClave.setStyle("-fx-font-size: 18px;");
        botonLogin.setStyle("-fx-font-size: 18px;");
        mensaje.setStyle("-fx-font-size: 18px;");

        // Acción del botón
        botonLogin.setOnAction(e -> {
            String usuario = campoUsuario.getText();
            String clave = campoClave.getText();

            Usuario usuarioLogueado = gestor.iniciarSesion(usuario, clave);

            if (usuarioLogueado != null) {
                mensaje.setText("Inicio de sesion exitoso");
                PantallaPrincipal pantalla = new PantallaPrincipal(usuarioLogueado, gestor);
                pantalla.mostrar();
            } else {
                mensaje.setText("Credenciales incorrectas");
            }
        });

        // Organizar todo en el VBox
        VBox vbox = new VBox(10, imageView, titulo, labelUsuario, campoUsuario, labelClave, campoClave, botonLogin, mensaje);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Agrandar ventana (800x600)
        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SACI3D - Inicio de sesion");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
