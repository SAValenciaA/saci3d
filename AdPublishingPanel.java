// AdPublishingPanel.javadhs
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints; // Importar ActionListener
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AdPublishingPanel extends JPanel {

    private JTextField txtFecha;
    private JTextField txtAsunto;
    private JTextArea txtCuerpo;
    private JButton btnPublicar; // Hacemos el botón una variable de instancia para añadir el listener desde fuera

    public AdPublishingPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2)); // Borde azul
        setBackground(Color.WHITE); // Fondo blanco

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Rellenar horizontalmente

        // Fila 1: Fecha
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
        JButton btnFechaLabel = new JButton("Fecha"); // Este es solo un "label" con estilo de botón
        styleLabelButton(btnFechaLabel);
        add(btnFechaLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Pesa más horizontalmente para expandirse
        txtFecha = new JTextField(20);
        txtFecha.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtFecha.setToolTipText("Ingrese la fecha de publicación");
        add(txtFecha, gbc);
        gbc.weightx = 0; // Resetear peso

        // Fila 2: Asunto
        gbc.gridx = 0;
        gbc.gridy = 1;
        JButton btnAsuntoLabel = new JButton("Asunto"); // Este es solo un "label" con estilo de botón
        styleLabelButton(btnAsuntoLabel);
        add(btnAsuntoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtAsunto = new JTextField(20);
        txtAsunto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtAsunto.setToolTipText("Asunto del mensaje");
        add(txtAsunto, gbc);
        gbc.weightx = 0;

        // Fila 3: Cuerpo
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Alinear arriba a la izquierda
        JButton btnCuerpoLabel = new JButton("Cuerpo"); // Este es solo un "label" con estilo de botón
        styleLabelButton(btnCuerpoLabel);
        add(btnCuerpoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // Pesa más verticalmente para expandirse
        gbc.fill = GridBagConstraints.BOTH; // Rellenar en ambas direcciones
        txtCuerpo = new JTextArea(8, 30);
        txtCuerpo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtCuerpo.setLineWrap(true);
        txtCuerpo.setWrapStyleWord(true);
        JScrollPane scrollCuerpo = new JScrollPane(txtCuerpo);
        scrollCuerpo.setToolTipText("Ingrese el mensaje para publicar");
        add(scrollCuerpo, gbc);
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botón Publicar
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST; // Alinear a la derecha
        gbc.insets = new Insets(20, 10, 10, 10); // Más espacio arriba
        btnPublicar = new JButton("Publicar");
        btnPublicar.setBackground(new Color(0, 102, 204));
        btnPublicar.setForeground(Color.WHITE);
        btnPublicar.setPreferredSize(new Dimension(120, 35));
        add(btnPublicar, gbc);
    }

    private void styleLabelButton(JButton button) {
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 30));
        button.setFocusPainted(false); // Eliminar el borde de enfoque
    }

    // Métodos para obtener los datos del formulario
    public String getFecha() {
        return txtFecha.getText();
    }

    public String getAsunto() {
        return txtAsunto.getText();
    }

    public String getCuerpo() {
        return txtCuerpo.getText();
    }

    // Método para limpiar los campos del formulario
    public void clearFields() {
        txtFecha.setText("");
        txtAsunto.setText("");
        txtCuerpo.setText("");
    }

    // Método para añadir un listener al botón Publicar desde fuera
    public void addPublishButtonListener(ActionListener listener) {
        btnPublicar.addActionListener(listener);
    }
}