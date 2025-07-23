import java.awt.*;
import javax.swing.*;

public class FrameOpcionesGeneral extends JFrame implements MenuActionListener {

    private CardLayout cardLayout;
    private JPanel mainContentPanel; // Panel que contendrá los diferentes paneles de contenido

    private UserInfoPanel userInfoPanel;
    private MenuPanel menuPanel;
    private JPanel panelVacio;
    private AdPublishingPanel adPublishingPanel; //panel publicar
    private PanelCancelarCitas panelCancelarCitas; //panel cancelar citas
    private PanelDispoImpresoras panelDispoImpresoras; //
    

    public FrameOpcionesGeneral() {
        setTitle("SACI3D - Sistema Académico"); // Título más general
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Layout principal



        //Informacion del usuario
        userInfoPanel = new UserInfoPanel("Carlos Sánchez", "Profesor", "395832");
        //Acciones de los botones del menu, esta reescritos para direcionar a los paneles correspondientes
        menuPanel = new MenuPanel(this);
        //panel vacio, primera vista
        panelVacio = new PanelVacio();
        
        //panel de publicar anuncios
        adPublishingPanel= new AdPublishingPanel();
        //panel de Cancelar citas
        panelCancelarCitas= new PanelCancelarCitas();
        //panel de Ver impresoras
        panelDispoImpresoras =new PanelDispoImpresoras();
        

        // 2. Configurar el CardLayout para cambiar el contenido central
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);


        // Añadir los paneles al CardLayout con un nombre clave
        mainContentPanel.add(panelVacio, "Vacio");
        mainContentPanel.add(adPublishingPanel, "Publicar");
        mainContentPanel.add(panelCancelarCitas,"Cancelar");
        mainContentPanel.add(panelDispoImpresoras,"Impresoras");
        // mainContentPanel.add(agendarCitaPanel, "AgendarCita");
        
        // Mostrar el panel vacio por defecto
        cardLayout.show(mainContentPanel, "Vacio");


        // 3. Añadir los paneles a las regiones del JFrame
        add(userInfoPanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER); // El panel con CardLayout va al centro
    }
    // Implementación de la interfaz MenuActionListener
    @Override
    public void onAgendarCitaClicked() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de Agendar Cita aún no implementada.", "Información", JOptionPane.INFORMATION_MESSAGE);
        // Aquí podrías cambiar el panel visible: cardLayout.show(mainContentPanel, "AgendarCita");
    }

    @Override
    public void  onCancelarCitaClicked() {
        //hacer visible el panel de cancelar
        cardLayout.show(mainContentPanel, "Cancelar");
        // Aquí podrías cambiar el panel visible: cardLayout.show(mainContentPanel, "CancelarCita");
    }

    @Override
    public void onPublicarAnunciosClicked() {

        //hacer visible el panel de publicar
        cardLayout.show(mainContentPanel, "Publicar");

    }

    @Override
    public void onCerrarSesionClicked() {
        JOptionPane.showMessageDialog(this, "Cerrando sesión...", "Sesión", JOptionPane.INFORMATION_MESSAGE);
        dispose(); // Cierra la ventana actual
        // Aquí iría la lógica para volver a una pantalla de login, etc.
    }

    @Override
    public void onVerImpresoras(){
        //hacer visible el panel de ver Impresoras
        cardLayout.show(mainContentPanel, "Impresoras");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrameOpcionesGeneral().setVisible(true));
    }
}
