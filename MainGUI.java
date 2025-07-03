import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI extends Application {

    private Gestor gestor = new Gestor();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SACI3D - Inicio de sesión");

        Label labelUsuario = new Label("Usuario:");
        TextField campoUsuario = new TextField();

        Label labelContrasena = new Label("Contraseña:");
        PasswordField campoContrasena = new PasswordField();

        Button botonLogin = new Button("Iniciar sesión");
        Label mensaje = new Label();

        botonLogin.setOnAction(e -> {
            String user = campoUsuario.getText();
            String pass = campoContrasena.getText();
            Usuario u = gestor.iniciarSesion(user, pass);
            if (u != null) {
                mensaje.setText("¡Bienvenido " + u.getNombre() + "!");
               
            } else {
                mensaje.setText("Credenciales incorrectas.");
            }
        });

        VBox vbox = new VBox(10, labelUsuario, campoUsuario, labelContrasena, campoContrasena, botonLogin, mensaje);
        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
