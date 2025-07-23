// CancelarCitaPanel.java
import java.awt.*;
import java.time.*;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*; // Importa Iterator para eliminar elementos de forma segura

public class PanelCancelarCitas extends JPanel {

    private ArrayList<Cita> listaCitas; // Tu lista de citas
    private JPanel citasContainerPanel; // Panel donde se añadirán los CitaPanelItem
    private JScrollPane scrollPane; // Para hacer el panel desplazable si hay muchas citas

    public PanelCancelarCitas() {
        setLayout(new BorderLayout()); // Layout principal para este panel
        setBackground(new Color(240, 240, 240)); // Un color de fondo suave

        // Panel superior para la barra de búsqueda (como en tu imagen)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(220, 230, 240)); // Un color diferente para la barra superior
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen
        JLabel searchIcon = new JLabel(new ImageIcon("lupa.png")); // Reemplaza con tu ícono de lupa
        // Puedes escalar el ícono si es necesario:
        // ImageIcon originalIcon = new ImageIcon("lupa.png");
        // Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        // JLabel searchIcon = new JLabel(new ImageIcon(scaledImage));
        topPanel.add(searchIcon);
        topPanel.add(new JLabel("Buscar")); // Texto "Buscar"
        add(topPanel, BorderLayout.NORTH);


        // Panel contenedor de las citas individuales
        citasContainerPanel = new JPanel();
        citasContainerPanel.setLayout(new BoxLayout(citasContainerPanel, BoxLayout.Y_AXIS)); // Apila las citas verticalmente
        citasContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno
        citasContainerPanel.setBackground(Color.WHITE); // Fondo para el contenedor de citas

        // Añadir el contenedor de citas a un JScrollPane para que sea desplazable
        scrollPane = new JScrollPane(citasContainerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Hace el scroll más suave
        add(scrollPane, BorderLayout.CENTER); // El scroll pane ocupa el centro del panel principal

        // Inicializar la lista de citas (ejemplo con datos ficticios)
        listaCitas = new ArrayList<>();
        cargarCitasDeEjemplo(); // Método para añadir citas de prueba

        // Cargar los paneles de citas en la interfaz
        displayCitas();
    }

    // Método para cargar citas de ejemplo (en una aplicación real, vendrían de una base de datos, etc.)
    private void cargarCitasDeEjemplo() {
        listaCitas.add(new Cita(1,32,    LocalDateTime.of(LocalDate.of(2025, 7, 22), LocalTime.of(10, 0)), 10*60,"Jhon Smith"));
        listaCitas.add(new Cita(1, 180,  LocalDateTime.of(LocalDate.of(2025, 7, 23), LocalTime.of(14, 30)), 2*60,"Alice Johnson"));
        listaCitas.add(new Cita(1, 200,  LocalDateTime.of(LocalDate.of(2025, 7, 24), LocalTime.of(9, 15)), 1*60,"Bob Williams"));
        listaCitas.add(new Cita(1,0,     LocalDateTime.of(LocalDate.of(2025, 7, 25), LocalTime.of(11, 0)), 45,"Charlie Brown"));
        listaCitas.add(new Cita(1,100,   LocalDateTime.of(LocalDate.of(2025, 7, 26), LocalTime.of(16, 0)), 3*60,"Diana Prince"));
        // Puedes añadir más citas para probar el scroll
    }

    // Método para crear y añadir los CitaPanelItem al contenedor
    private void displayCitas() {
        citasContainerPanel.removeAll(); // Limpiar el panel antes de añadir nuevas citas

        for (Cita cita : listaCitas) {
            PanelCancelarIndividual item = new PanelCancelarIndividual(cita);

            // Añadir ActionListener para el botón "X" (Cancelar)
            item.addCancelarListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Estás seguro de que quieres cancelar la cita con ID: " + item.getCita().getId() + "?",
                        "Confirmar Cancelación",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    cancelarCita(item.getCita());
                }
            });

            // Añadir ActionListener para el botón "i" (Información/Detalles)
            item.addInfoListener(e -> {
                JOptionPane.showMessageDialog(this,
                        "Detalles de la Cita:\n" + item.getCita().toString(),
                        "Información de la Cita",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            citasContainerPanel.add(item);
            // Añadir un pequeño espacio entre los ítems (opcional)
            citasContainerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 10px de espacio vertical
        }

        citasContainerPanel.revalidate(); // Revalidar para que el layout se recalcule
        citasContainerPanel.repaint();    // Repintar el componente
    }

    // Método para manejar la lógica de cancelación de una cita
    private void cancelarCita(Cita citaACancelar) {
       
        Iterator<Cita> iterator = listaCitas.iterator();
        while (iterator.hasNext()) {
            Cita c = iterator.next();
            if (c.getId().equals(citaACancelar.getId())) {
                iterator.remove();
                break;
            }
        }

        // Volver a dibujar la lista de citas sin la cita cancelada
        displayCitas();
    }

}
