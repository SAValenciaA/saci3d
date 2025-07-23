// CitaPanelItem.java
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*; // Importar ActionListener

public class PanelCancelarIndividual extends JPanel {

    private Cita cita; // La cita que este panel representa
    private JButton btnCancelar;
    private JButton btnInfo;

    public PanelCancelarIndividual(Cita cita) {
        this.cita = cita;
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // Borde para separar las citas
        setLayout(new BorderLayout(10, 5)); // BorderLayout para la estructura principal del ítem
        setBackground(Color.WHITE); // Fondo blanco para la tarjeta

        // Panel para la información de la cita (ID, Usuario, Fecha, Hora, Duracion)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Apilar verticalmente los labels
        infoPanel.setBackground(Color.WHITE);

        infoPanel.add(new JLabel("ID: " + cita.getId()));
        infoPanel.add(new JLabel("Usuario: " + cita.getUsuario()));
        infoPanel.add(new JLabel("Fecha: " + cita.getFecha().toLocalDate()));
        infoPanel.add(new JLabel("Hora: " + cita.getFecha().toLocalTime()));
        infoPanel.add(new JLabel("Duracion: " + cita.getDuracion()));

        // Panel para los botones (X e i)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0)); // Alinear a la derecha los botones
        buttonPanel.setBackground(Color.WHITE); // Mismo fondo que el infoPanel

        btnCancelar = new JButton("X");
        btnCancelar.setBackground(new Color(255, 99, 71)); // Color rojizo
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false); // Quitar el borde de foco
        btnCancelar.setPreferredSize(new Dimension(50, 30)); // Tamaño fijo para el botón X

        btnInfo = new JButton("i"); // O puedes usar un ícono, como en tu imagen
        btnInfo.setBackground(new Color(65, 105, 225)); // Color azulado
        btnInfo.setForeground(Color.WHITE);
        btnInfo.setFocusPainted(false);
        btnInfo.setPreferredSize(new Dimension(50, 30)); // Tamaño fijo para el botón i

        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnInfo);

        // Añadir los paneles al CitaPanelItem
        add(infoPanel, BorderLayout.CENTER); // Información en el centro
        add(buttonPanel, BorderLayout.SOUTH); // Botones abajo
    }

    // Métodos para añadir ActionListeners a los botones desde el panel padre
    public void addCancelarListener(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }

    public void addInfoListener(ActionListener listener) {
        btnInfo.addActionListener(listener);
    }

    // Método para obtener la cita asociada a este panel (útil para los listeners)
    public Cita getCita() {
        return cita;
    }
}
