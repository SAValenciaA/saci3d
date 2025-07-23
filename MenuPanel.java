// MenuPanel.java
import java.awt.*;
import javax.swing.*;

public class MenuPanel extends JPanel {

    private MenuActionListener listener; // Referencia al listener

    public MenuPanel(MenuActionListener listener) {
        this.listener = listener; // Se recibe la instancia del listener
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Apilado verticalmente
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Padding

        JButton btnAgendarCita = new JButton("Agendar Cita");
        JButton btnCancelarCita = new JButton("Cancelar Cita");
        JButton btnPublicarAnuncios = new JButton("Publicar Anuncios");
        JButton btnVerImpresoras = new JButton("Ver Impresoras");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        // Estilo de los botones
        styleButton(btnAgendarCita);
        styleButton(btnCancelarCita);
        styleButton(btnPublicarAnuncios);
        styleButton(btnCerrarSesion);
        styleButton(btnVerImpresoras);

        // Estilo específico para el botón de Cerrar Sesión
        btnCerrarSesion.setBackground(new Color(51, 51, 51));
        btnCerrarSesion.setForeground(Color.WHITE);

        // Añadir ActionListeners y llamar al listener principal
        btnAgendarCita.addActionListener(e -> listener.onAgendarCitaClicked());
        btnCancelarCita.addActionListener(e -> listener.onCancelarCitaClicked());
        btnPublicarAnuncios.addActionListener(e -> listener.onPublicarAnunciosClicked());
        btnCerrarSesion.addActionListener(e -> listener.onCerrarSesionClicked());
        btnVerImpresoras.addActionListener(e->listener.onVerImpresoras());

        add(Box.createVerticalStrut(10)); // Espacio
        add(btnAgendarCita);
        add(Box.createVerticalStrut(10));
        add(btnCancelarCita);
        add(Box.createVerticalStrut(10));
        add(btnPublicarAnuncios);
        add(Box.createVerticalStrut(10));
        add(btnVerImpresoras);
        add(Box.createVerticalGlue()); // Empuja los elementos hacia arriba
        add(btnCerrarSesion);
        add(Box.createVerticalStrut(10));
        
    }

    private void styleButton(JButton button) {
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setPreferredSize(new Dimension(150, 30)); // Ancho fijo
        button.setMaximumSize(new Dimension(150, 30)); // Para BoxLayout
        button.setFocusPainted(false); // Eliminar el borde de enfoque
        button.setBackground(UIManager.getColor("Button.background")); // Color por defecto del sistema
        button.setForeground(UIManager.getColor("Button.foreground"));
    }
}