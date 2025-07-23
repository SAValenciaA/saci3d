// AdPublishingPanel.java
import java.awt.*;
import javax.swing.*;

public class PanelVacio extends JPanel {

    public PanelVacio() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2)); // Borde azul
        setBackground(Color.WHITE); // Fondo blanco
    }
}