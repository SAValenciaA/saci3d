// VentanaEditarImpresora.java (MODIFICADO - No recibe JFrame padre)
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class VentanaEditarImpresora extends JFrame {

    private Impresora impresoraOriginal; // La impresora que se está editando
    private JTextField txtFilamentoActual;
    private JTextField txtFilamentoNoReservado;
    private JCheckBox chkDisponible;
    private JTextField txtMensajeNoDisponible;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private boolean guardadoExitoso = false; // Bandera para saber si se guardó

    // Constructor que SÓLO recibe la impresora a editar
    public VentanaEditarImpresora(Impresora impresora) { // <-- ¡Ya no recibe parentFrame aquí!
        this.impresoraOriginal = impresora;
        setTitle("Editar Impresora Nº: " + impresora.getNumero());
        setLayout(new BorderLayout(10, 10));
        setSize(450, 350);
        setLocationRelativeTo(null); // Centrar en la pantalla (no necesita padre para esto)
        setResizable(false);
        // setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Por defecto, cierra solo esta ventana

        // Añadir un WindowListener para manejar el cierre de la ventana por la 'X'
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardadoExitoso = false; // Considerar el cierre por la 'X' como una cancelación
                dispose();
            }
        });


        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Número de Impresora:"));
        formPanel.add(new JLabel(String.valueOf(impresora.getNumero())));

        formPanel.add(new JLabel("Filamento Actual (gramos):"));
        txtFilamentoActual = new JTextField(String.valueOf(impresora.getFilamentoActualGramos()));
        formPanel.add(txtFilamentoActual);

        formPanel.add(new JLabel("Filamento No Reservado (gramos):"));
        txtFilamentoNoReservado = new JTextField(String.valueOf(impresora.getFilamentoNoReservadoGramos()));
        formPanel.add(txtFilamentoNoReservado);

        formPanel.add(new JLabel("Disponible:"));
        chkDisponible = new JCheckBox();
        chkDisponible.setSelected(impresora.isDisponible());
        formPanel.add(chkDisponible);

        formPanel.add(new JLabel("Mensaje (si no disponible):"));
        txtMensajeNoDisponible = new JTextField(impresora.getMensajeNoDisponible() != null ? impresora.getMensajeNoDisponible() : "");
        txtMensajeNoDisponible.setEnabled(!impresora.isDisponible());
        formPanel.add(txtMensajeNoDisponible);

        chkDisponible.addActionListener(e -> {
            txtMensajeNoDisponible.setEnabled(!chkDisponible.isSelected());
            if (chkDisponible.isSelected()) {
                txtMensajeNoDisponible.setText("");
            }
        });

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(46, 139, 87));
        btnGuardar.setForeground(Color.WHITE);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(220, 20, 60));
        btnCancelar.setForeground(Color.WHITE);

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        add(buttonPanel, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> {
            try {
                double nuevoFilamentoActual = Double.parseDouble(txtFilamentoActual.getText().trim());
                double nuevoFilamentoNoReservado = Double.parseDouble(txtFilamentoNoReservado.getText().trim());
                boolean nuevaDisponibilidad = chkDisponible.isSelected();
                String nuevoMensajeNoDisponible = txtMensajeNoDisponible.getText().trim();

                if (nuevaDisponibilidad) {
                    nuevoMensajeNoDisponible = null;
                    if (nuevoFilamentoActual < 0 || nuevoFilamentoNoReservado < 0) {
                        JOptionPane.showMessageDialog(this, "Los valores de filamento no pueden ser negativos.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else {
                    if (nuevoMensajeNoDisponible.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "El mensaje de no disponibilidad es obligatorio.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                impresoraOriginal.setFilamentoActualGramos(nuevoFilamentoActual);
                impresoraOriginal.setFilamentoNoReservadoGramos(nuevoFilamentoNoReservado);
                impresoraOriginal.setDisponible(nuevaDisponibilidad);
                impresoraOriginal.setMensajeNoDisponible(nuevaDisponibilidad ? null : nuevoMensajeNoDisponible);

                guardadoExitoso = true;
                dispose(); // Cerrar esta ventana (JFrame)
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos para el filamento.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar la impresora: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> {
            guardadoExitoso = false;
            dispose(); // Cerrar esta ventana sin guardar
        });
    }

    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }
}