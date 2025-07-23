import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Buttons {

  public static HashMap<String, JButton> adminPanel() {

    HashMap<String,String> adminPanelButtons = new HashMap<String, String>();
    HashMap<String,JButton> adminButtons = new HashMap<String, JButton>();
    HashMap<String, String> adminStringButtons = new HashMap<String, String>();

    adminStringButtons = new HashMap<String,String>();
    adminStringButtons.put("cancel","Cancelar Cita");
    adminStringButtons.put("printers","Mostrar Impresoras");
    adminStringButtons.put("addPrinters","Agregar Impresoras");
    adminStringButtons.put("notice","Hacer Anuncio");

    for(Map.Entry<String,String> button : adminStringButtons.entrySet()) {
      adminButtons.put(button.getKey(), new JButton(button.getValue()));
    }

    return adminButtons;

  }

}
