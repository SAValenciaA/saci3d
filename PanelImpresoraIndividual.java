// ImpresoraPanelItem.java (MODIFICADO para permitir editar impresoras no disponibles)
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PanelImpresoraIndividual extends JPanel {

    private Impresora impresora;
    private JButton btnEditar;
    private JLabel lblEstado; // Para mostrar "Disponible" o "No disponible"
    private JLabel lblMensajeNoDisponible; // Para el mensaje "Alguien se robó la placa..."

    public PanelImpresoraIndividual(Impresora impresora) {
        this.impresora = impresora;
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY)); // Solo borde inferior
        setLayout(new GridBagLayout()); // Usamos GridBagLayout para mayor control
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Márgenes internos

        // --- Impresora Nº: ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Expandirse horizontalmente
        JLabel lblNumero = new JLabel("Impresora Nº: " + impresora.getNumero());
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 14));
        // El color del número cambia según la disponibilidad
        lblNumero.setForeground(impresora.isDisponible() ? new Color(0, 102, 204) : Color.RED);
        add(lblNumero, gbc);

        // --- Estado: Disponible / No disponible ---
        gbc.gridy++;
        lblEstado = new JLabel(impresora.isDisponible() ? "Disponible" : "No disponible");
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEstado.setForeground(impresora.isDisponible() ? new Color(0, 102, 204) : Color.RED);
        add(lblEstado, gbc);

        // --- Filamento Actual y No Reservado (solo si disponible) ---
        if (impresora.isDisponible()) {
            gbc.gridy++;
            JLabel lblFilamentoActual = new JLabel("Filamento Actual: " + impresora.getFilamentoActualGramos() + "g");
            lblFilamentoActual.setForeground(new Color(0, 102, 204));
            add(lblFilamentoActual, gbc);

            gbc.gridy++;
            JLabel lblFilamentoNoReservado = new JLabel("Filamento no reservado: " + impresora.getFilamentoNoReservadoGramos() + "g");
            lblFilamentoNoReservado.setForeground(new Color(0, 102, 204));
            add(lblFilamentoNoReservado, gbc);
        } else {
            // --- Mensaje de no disponibilidad (si no disponible) ---
            gbc.gridy++; // Pasa a la siguiente fila después del estado
            lblMensajeNoDisponible = new JLabel(impresora.getMensajeNoDisponible());
            lblMensajeNoDisponible.setForeground(Color.RED);
            lblMensajeNoDisponible.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            lblMensajeNoDisponible.setOpaque(true);
            lblMensajeNoDisponible.setBackground(new Color(255, 230, 230));
            lblMensajeNoDisponible.setHorizontalAlignment(SwingConstants.CENTER);
            lblMensajeNoDisponible.setPreferredSize(new Dimension(250, 30)); // Ajustar tamaño para que quepa el texto
            // Podemos añadirlo a un panel para mejor alineación si es necesario,
            // pero para este ejemplo, lo añadiremos directamente con gbc.
            add(lblMensajeNoDisponible, gbc);
        }

        // --- Botón Editar (SIEMPRE se crea) ---
        gbc.gridy++; // Pasa a la siguiente fila después de los detalles o el mensaje
        gbc.fill = GridBagConstraints.NONE; // No expandir
        gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
        btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(0, 123, 255)); // Azul Bootstrap
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFocusPainted(false);
        btnEditar.setBorderPainted(false);
        btnEditar.setRolloverEnabled(true);
        btnEditar.setPreferredSize(new Dimension(100, 30));
        add(btnEditar, gbc); // El botón "Editar" se añade siempre

        // Un "glue" vertical al final para empujar todo hacia arriba
        gbc.gridy++;
        gbc.weighty = 1.0; // Darle todo el peso vertical restante
        add(Box.createVerticalGlue(), gbc);
    }

    // Método para obtener la impresora asociada a este panel
    public Impresora getImpresora() {
        return impresora;
    }

    // Método para añadir ActionListener al botón Editar (sigue siendo el mismo)
    public void addEditarListener(ActionListener listener) {
        // Ahora btnEditar siempre existirá
        btnEditar.addActionListener(listener);
    }
}