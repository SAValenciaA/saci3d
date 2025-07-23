// UserInfoPanel.java
import java.awt.*;
import javax.swing.*;

public class UserInfoPanel extends JPanel {

    private JLabel lblUsuarioValor;
    private JLabel lblRolValor;
    private JLabel lblIdValor;

    public UserInfoPanel(String userName, String role, String id) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding

        // Panel de Información de Usuario
        JPanel panelInfoUsuario = new JPanel(new GridLayout(3, 2, 5, 2)); // 3 filas, 2 columnas, espaciado
        panelInfoUsuario.add(new JLabel("Usuario:"));
        lblUsuarioValor = new JLabel(userName);
        panelInfoUsuario.add(lblUsuarioValor);
        panelInfoUsuario.add(new JLabel("Rol:"));
        lblRolValor = new JLabel(role);
        panelInfoUsuario.add(lblRolValor);
        panelInfoUsuario.add(new JLabel("ID:"));
        lblIdValor = new JLabel(id);
        panelInfoUsuario.add(lblIdValor);

        // Panel para el logo y SACI3D
        JPanel panelLogo = new JPanel(new BorderLayout());
        JLabel lblSACI3D = new JLabel("SACI3D");
        lblSACI3D.setFont(new Font("Arial", Font.BOLD, 24));
        lblSACI3D.setHorizontalAlignment(SwingConstants.CENTER);
        panelLogo.add(lblSACI3D, BorderLayout.WEST);

        // Simulación del logo UN (Normalmente sería un JLabel con ImageIcon)
        JLabel lblUNLogo = new JLabel("<html><center>UNIVERSIDAD<br>NACIONAL<br>DE COLOMBIA</center></html>");
        lblUNLogo.setFont(new Font("Arial", Font.PLAIN, 10));
        lblUNLogo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUNLogo.setVerticalAlignment(SwingConstants.CENTER);
        panelLogo.add(lblUNLogo, BorderLayout.EAST);

        add(panelInfoUsuario, BorderLayout.WEST);
        add(panelLogo, BorderLayout.EAST);
    }

    // Métodos para actualizar la información del usuario si fuera necesario
    public void updateUserInfo(String userName, String role, String id) {
        lblUsuarioValor.setText(userName);
        lblRolValor.setText(role);
        lblIdValor.setText(id);
    }
}
