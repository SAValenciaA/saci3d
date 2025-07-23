import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class GraphicInterface {
    static CardLayout cardLayout;
    static JPanel mainPanel;
    static JFrame frame;
    static Usuario currentUser;
    static JPanel formPanel = new JPanel(); // globally so we can reuse and update it
    static JPanel outerPanel = new JPanel(new BorderLayout()); // main panel to append form


    public static void main(String[] args) {

        Database.createConnection("database.db");


        frame = new JFrame("Universidad Nacional de Colombia");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(loginPanel(), "login");
        mainPanel.add(adminPanel(), "admin");
        mainPanel.add(studentPanel(), "student");

        frame.add(mainPanel);
        frame.setVisible(true);
    }


    public void message(String message, String type) {

      String messagePreface = "";

      if(type.equals("warning")) {
        messagePreface = "Atencion: ";
      } else if(type.equals("information")) {
       messagePreface = "Atencion: ";
      } else if(type.equals("error")) {
        messagePreface = "Error: ";
      }

      JOptionPane.showMessageDialog(frame, messagePreface + message);
    }


    static JPanel loginPanel() {
        
        final int titleSize = 24;
        final int loginInputCharNum = 15;

        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBackground(Color.white);
        

        // TODO: Put a real logo
        JLabel logo = new JLabel("UN");
        logo.setFont(new Font("Arial", Font.BOLD, 36));

        JLabel title = new JLabel("Inicio de sesion");
        title.setFont(new Font("Arial", Font.BOLD, titleSize));

        JTextField userField = new JTextField(loginInputCharNum);
        JPasswordField passField = new JPasswordField(loginInputCharNum);

        JButton loginButton = new JButton("Entrar");
        JButton closeButton = new JButton("Cerrar");

        loginButton.addActionListener(e -> {

            String username = userField.getText();
            String password = new String(passField.getPassword());

            Usuario user = Usuario.iniciarSesion(username, password);

            if(user == null) {
              return;
            }

            currentUser = user;

            // TODO: menos spanglish
            if(currentUser.rol.equals("administrador")) {
              cardLayout.show(mainPanel, "admin");
            } else if(currentUser.rol.equals("estudiante")) {
              cardLayout.show(mainPanel, "student");
            }
        });

        closeButton.addActionListener(e -> System.exit(0));

        // TODO: esto posiciona los inputs y botones del login
        // No lo se leer, tu tampoco lo sabes leer probablemente,
        // no importa por que esto muy probablemente no se va a
        // tocar. Pero si hay tiempo hacerlo legible
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; panel.add(logo, gbc);
        gbc.gridy++; panel.add(title, gbc);
        gbc.gridy++; gbc.gridwidth = 1; panel.add(new JLabel("Usuario"), gbc);
        gbc.gridx = 1; panel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Contraseña"), gbc);
        gbc.gridx = 1; panel.add(passField, gbc);
        gbc.gridy++; gbc.gridx = 0; panel.add(loginButton, gbc);
        gbc.gridx = 1; panel.add(closeButton, gbc);

        return panel;
    }

    static JPanel adminPanel() {

        // TODO: Make this whole dashboard panel a card that 
        // can be not shown
        JPanel panel = new JPanel(new BorderLayout());

        JPanel sidebar = new JPanel(new GridLayout(4, 1));

        JPanel centerPanel = new JPanel(new CardLayout());

        Map<String, JButton> adminButtons = Buttons.adminPanel();

        adminButtons.forEach((nameButton, button) -> {
          centerPanel.add(
              new JLabel("Panel " + nameButton), 
              nameButton);

          button.addActionListener((e) -> {
            CardLayout card = (CardLayout) (centerPanel.getLayout());
            card.show(centerPanel, nameButton);
          });

          sidebar.add(button);

        });

        // TODO: una funcion para esto tal vez sea lo mejor
        JButton logout = new JButton("Salir");
        logout.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        panel.add(sidebar, BorderLayout.WEST);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(logout, BorderLayout.NORTH);

        return panel;
    }

    static JPanel studentPanel() {
        Color mainBlue = Color.decode("#006eb6");
        Color neutralGrey = Color.decode("#363735");
        Color lunchGray = new Color(220, 220, 220);

        Font dayFont = new Font("SansSerif", Font.BOLD, 14);
        Font hourFont = new Font("SansSerif", Font.PLAIN, 10);

        outerPanel = new JPanel(new BorderLayout());
        outerPanel.setPreferredSize(new Dimension(700, 400));
        outerPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(mainBlue, 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        String[] days = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        int timeSlots = 22;
        LocalTime startTime = LocalTime.of(6, 0);

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);

        // ⏱️ Get citas from Cita class
        ArrayList<Cita> citas = Cita.getCitas();

        // Header row
        for (int col = 0; col < days.length; col++) {
            JLabel label = new JLabel(days[col], SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.setFont(col == 0 ? hourFont : dayFont);
            label.setForeground(col == 0 ? neutralGrey : mainBlue);
            label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            gbc.gridx = col;
            gbc.gridy = 0;
            grid.add(label, gbc);
        }

        // Time rows
        for (int row = 0; row < timeSlots; row++) {
            LocalTime time = startTime.plusMinutes(row * 30);
            gbc.gridy = row + 1;

            for (int col = 0; col < 7; col++) {
                gbc.gridx = col;

                if (col == 0) {
                    JLabel timeLabel = new JLabel(" " + time.toString() + " ", SwingConstants.CENTER);
                    timeLabel.setFont(hourFont);
                    timeLabel.setForeground(neutralGrey);
                    timeLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    grid.add(timeLabel, gbc);
                } else {
                    LocalDate date = monday.plusDays(col - 1);
                    LocalDateTime dateTime = LocalDateTime.of(date, time);

                    JButton slot = new JButton();
                    slot.setPreferredSize(new Dimension(100, 30));
                    slot.setFont(new Font("Arial", Font.PLAIN, 11));
                    slot.setMargin(new Insets(2, 2, 2, 2));
                    slot.setBackground(Color.WHITE);
                    slot.setFocusPainted(false);
                    slot.putClientProperty("datetime", dateTime);

                    // Determine disabled states
                    boolean isLunch = !time.isBefore(LocalTime.of(12, 0)) && time.isBefore(LocalTime.of(13, 30));
                    boolean isPast = dateTime.isBefore(now);
                    boolean isConflict = false;

                    for (Cita cita : citas) {
                        LocalDateTime citaStart = cita.fechaInicio;
                        LocalDateTime citaEnd = citaStart.plusMinutes(cita.duracion);
                        if (!dateTime.isBefore(citaStart) && dateTime.isBefore(citaEnd)) {
                            isConflict = true;
                            break;
                        }
                    }

                    if (isLunch || isPast || isConflict) {
                        slot.setEnabled(false);
                        slot.setBackground(lunchGray);
                    } else {
                        slot.addActionListener(e -> {
                          LocalDateTime dt = (LocalDateTime) ((JButton) e.getSource()).getClientProperty("datetime");
                          showFormPanel(dt);
                        });
                    }

                    grid.add(slot, gbc);
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(grid);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement((int)(scrollPane.getVerticalScrollBar().getUnitIncrement() * 1.2));
        scrollPane.getHorizontalScrollBar().setUnitIncrement((int)(scrollPane.getHorizontalScrollBar().getUnitIncrement() * 1.2));
        outerPanel.add(scrollPane, BorderLayout.CENTER);

        return outerPanel;
    }
    public static void showFormPanel(LocalDateTime selectedDateTime) {
      formPanel.removeAll();
      formPanel.setLayout(new GridBagLayout());
      formPanel.setBorder(BorderFactory.createTitledBorder("Create Cita for: " + selectedDateTime.toString()));
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.gridx = 0;
      gbc.gridy = 0;

      formPanel.add(new JLabel("Fecha Inicio:"), gbc);
      gbc.gridx = 1;
      JTextField fechaField = new JTextField(selectedDateTime.toString());
      fechaField.setEditable(false);
      formPanel.add(fechaField, gbc);

      gbc.gridx = 0; gbc.gridy++;
      formPanel.add(new JLabel("Duración (min):"), gbc);
      gbc.gridx = 1;
      JTextField duracionField = new JTextField();
      formPanel.add(duracionField, gbc);

      gbc.gridx = 0; gbc.gridy++;
      formPanel.add(new JLabel("Número de impresora:"), gbc);
      gbc.gridx = 1;
      JTextField impresoraField = new JTextField();
      formPanel.add(impresoraField, gbc);

      gbc.gridx = 0; gbc.gridy++;
      formPanel.add(new JLabel("Peso de PLA (g):"), gbc);
      gbc.gridx = 1;
      JTextField pesoPLAField = new JTextField();
      formPanel.add(pesoPLAField, gbc);

      gbc.gridx = 0; gbc.gridy++;
      formPanel.add(new JLabel("Uso de PLA:"), gbc);
      gbc.gridx = 1;
      JRadioButton uniPLA = new JRadioButton("Usar PLA de la universidad");
      JRadioButton ownPLA = new JRadioButton("Traer mi propio PLA");
      ButtonGroup plaGroup = new ButtonGroup();
      plaGroup.add(uniPLA);
      plaGroup.add(ownPLA);
      uniPLA.setSelected(true);

      uniPLA.addActionListener(e -> pesoPLAField.setEnabled(true));
      ownPLA.addActionListener(e -> {
          pesoPLAField.setText("0.0");
          pesoPLAField.setEnabled(false);
      });

      JPanel radioPanel = new JPanel(new GridLayout(0, 1));
      radioPanel.add(uniPLA);
      radioPanel.add(ownPLA);
      formPanel.add(radioPanel, gbc);

      gbc.gridx = 1; gbc.gridy++;
      JButton submit = new JButton("Crear Cita");
      submit.addActionListener(e -> {
          try {
              int impresora = Integer.parseInt(impresoraField.getText());
              int duracion = Integer.parseInt(duracionField.getText());
              double peso = Double.parseDouble(pesoPLAField.getText());
              Cita nueva = new Cita(impresora, peso, selectedDateTime, duracion, currentUser.usuario);
              // You could store or use nueva here
              JOptionPane.showMessageDialog(formPanel, "Cita creada exitosamente.");
          } catch (Exception ex) {
              JOptionPane.showMessageDialog(formPanel, "Error en los campos: " + ex.getMessage());
          }
      });
      formPanel.add(submit, gbc);

      outerPanel.add(formPanel, BorderLayout.SOUTH);
      outerPanel.revalidate();
      outerPanel.repaint();
  }

}

