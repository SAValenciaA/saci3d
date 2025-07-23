// DialogoEditarImpresora.java
import java.awt.*;
import javax.swing.*;

public class DialogoEditarImpresora extends JDialog {

    private Impresora impresoraOriginal; // La impresora que se está editando
    private JTextField txtFilamentoActual;
    private JTextField txtFilamentoNoReservado;
    private JCheckBox chkDisponible;
    private JTextField txtMensajeNoDisponible; // Campo para el mensaje si no está disponible
    private JButton btnGuardar;
    private JButton btnCancelar;

    private boolean guardadoExitoso = false;

    public DialogoEditarImpresora(JFrame parentFrame, Impresora impresora) {
        super(parentFrame, "Editar Impresora Nº: " + impresora.getNumero(), true); // 'true' para hacerlo modal
        this.impresoraOriginal = impresora;
        setLayout(new BorderLayout(10, 10));
        setSize(450, 350);
        setLocationRelativeTo(parentFrame);
        setResizable(false);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Número de Impresora:"));
        formPanel.add(new JLabel(String.valueOf(impresora.getNumero()))); // El número no se edita

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
        txtMensajeNoDisponible.setEnabled(!impresora.isDisponible()); // Habilitado solo si no está disponible
        formPanel.add(txtMensajeNoDisponible);

        // Listener para habilitar/deshabilitar el campo de mensaje
        chkDisponible.addActionListener(e -> {
            txtMensajeNoDisponible.setEnabled(!chkDisponible.isSelected());
            if (chkDisponible.isSelected()) {
                txtMensajeNoDisponible.setText(""); // Limpiar si se vuelve disponible
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

        // ActionListeners para los botones
        btnGuardar.addActionListener(e -> {
            try {
                double nuevoFilamentoActual = Double.parseDouble(txtFilamentoActual.getText().trim());
                double nuevoFilamentoNoReservado = Double.parseDouble(txtFilamentoNoReservado.getText().trim());
                boolean nuevaDisponibilidad = chkDisponible.isSelected();
                String nuevoMensajeNoDisponible = txtMensajeNoDisponible.getText().trim();

                if (nuevaDisponibilidad) {
                    // Si está disponible, el mensaje debe ser nulo o vacío
                    nuevoMensajeNoDisponible = null;
                    if (nuevoFilamentoActual < 0 || nuevoFilamentoNoReservado < 0) {
                        JOptionPane.showMessageDialog(this, "Los valores de filamento no pueden ser negativos.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else {
                    // Si no está disponible, el mensaje es obligatorio
                    if (nuevoMensajeNoDisponible.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "El mensaje de no disponibilidad es obligatorio.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                //metodos de impresora hay que cambiarlos.
                impresoraOriginal.setFilamentoActualGramos(nuevoFilamentoActual);
                impresoraOriginal.setFilamentoNoReservadoGramos(nuevoFilamentoNoReservado);
                impresoraOriginal.setDisponible(nuevaDisponibilidad);
                impresoraOriginal.setMensajeNoDisponible(nuevaDisponibilidad ? null : nuevoMensajeNoDisponible);


                guardadoExitoso = true;
                dispose(); // Cerrar el diálogo
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos para el filamento.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar la impresora: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> {
            guardadoExitoso = false;
            dispose(); // Cerrar el diálogo sin guardar
        });
    }

    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }
}