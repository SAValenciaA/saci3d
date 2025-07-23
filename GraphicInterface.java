import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GraphicInterface {
    static CardLayout cardLayout;
    static JPanel mainPanel;
    static JFrame frame;
    static Usuario currentUser;

    public static void main(String[] args) {

        frame = new JFrame("Universidad Nacional de Colombia");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(loginPanel(), "login");
        mainPanel.add(adminPanel(), "admin");
        mainPanel.add(studentPanel(), "student");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public static void startPanel() {
      
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
        JPanel panel = new JPanel(new BorderLayout());

        JButton logout = new JButton("Salir");
        logout.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        JPanel calendar = new JPanel(new GridLayout(6, 7));

        calendar.setBorder(BorderFactory.createTitledBorder("Horario de citas"));

        panel.add(logout, BorderLayout.NORTH);
        panel.add(calendar, BorderLayout.CENTER);
        return panel;
    }
}

