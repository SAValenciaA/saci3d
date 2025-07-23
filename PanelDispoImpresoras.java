import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;

public class PanelDispoImpresoras extends JPanel {

    private ArrayList<Impresora> listaImpresoras;
    private JPanel impresorasContainerPanel;
    private JScrollPane scrollPane;

    public PanelDispoImpresoras() { // Sigue recibiendo el JFrame padre
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(220, 230, 240));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("SansSerif", Font.PLAIN, 18));
        topPanel.add(searchIcon);
        topPanel.add(new JLabel("Buscar Impresora"));
        add(topPanel, BorderLayout.NORTH);

        impresorasContainerPanel = new JPanel();
        impresorasContainerPanel.setLayout(new BoxLayout(impresorasContainerPanel, BoxLayout.Y_AXIS));
        impresorasContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        impresorasContainerPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(impresorasContainerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        listaImpresoras = new ArrayList<>();
        cargarImpresorasDeEjemplo();
        displayImpresoras();
    }

    private void cargarImpresorasDeEjemplo() {
        listaImpresoras.add(new Impresora(1, 2000.0, 10.0));
        listaImpresoras.add(new Impresora(2, false, "Alguien se robó la placa de la Impresora"));
        listaImpresoras.add(new Impresora(3, 500.0, 50.0));
        listaImpresoras.add(new Impresora(4, false, "Mantenimiento requerido"));
        listaImpresoras.add(new Impresora(5, 1500.0, 150.0));
        listaImpresoras.add(new Impresora(6, false, "Falta filamento especial"));
    }

    private void displayImpresoras() {
        impresorasContainerPanel.removeAll();

        for (Impresora impresora : listaImpresoras) {
            PanelImpresoraIndividual item = new PanelImpresoraIndividual(impresora);

            item.addEditarListener(e -> {
         
                VentanaEditarImpresora ventana = new VentanaEditarImpresora(item.getImpresora()); // <-- ¡Aquí el cambio!

                // Añadir un WindowListener para saber cuándo la ventana de edición se cierra
                ventana.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        if (ventana.isGuardadoExitoso()) {
                            JOptionPane.showMessageDialog(PanelDispoImpresoras.this,
                                "Impresora " + item.getImpresora().getNumero() + " actualizada exitosamente.",
                                "Edición Exitosa", JOptionPane.INFORMATION_MESSAGE);
                            displayImpresoras(); // Redibujar el panel principal para reflejar los cambios
                        } else {
                            JOptionPane.showMessageDialog(PanelDispoImpresoras.this,
                                "Edición de impresora cancelada.",
                                "Edición Cancelada", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

                ventana.setVisible(true);
            });

            impresorasContainerPanel.add(item);
            impresorasContainerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        impresorasContainerPanel.revalidate();
        impresorasContainerPanel.repaint();
    }

}