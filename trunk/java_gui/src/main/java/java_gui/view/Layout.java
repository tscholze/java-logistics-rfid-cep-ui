package java_gui.view;

import javax.swing.*;
import java.awt.*;


/**
 * Created with IntelliJ IDEA.
 * User: jenny, mirii
 * Date: 06.05.12
 * Time: 15:15
 */
public class Layout {

    private int width;
    private int height;

    private JFrame superFrame = new JFrame();
    private JMenuBar menuBar;

    private JPanel panel_topRight = new JPanel();
    private JPanel panel_bottomRight = new JPanel();

    private JPanel test3D_rotated = new JPanel();


    // Constructor
    public Layout(JMenuBar menuBar, JPanel controlBox, JPanel tagInfoBox, JPanel cube){
        this.width = 1024;
        this.height = 768;

        this.menuBar=menuBar;
        this.panel_topRight = controlBox;
        this.panel_bottomRight = tagInfoBox;
        this.test3D_rotated = cube;

        visualisation();
    }

    public Layout(JMenuBar menuBar, JPanel controlBox, JPanel tagInfoBox){
        this.width = 1024;
        this.height = 768;

        this.menuBar=menuBar;
        this.panel_topRight = controlBox;
        this.panel_bottomRight = tagInfoBox;

        visualisation();
    }



    private void superFrame() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        superFrame.setTitle("RFID");

        // Im Konstruktor fest eingestellt
        superFrame.setSize(this.width, this.height);

        // Schließt Fenster und beendet Programm
        superFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fenstergröße kann vom Benutzer nicht verändert werden
        //superFrame.setResizable(false);

        // Fenster wird mittig auf dem Bildschirm angezeigt
        superFrame.setLocationRelativeTo(null);

        // Damit's angezeigt wird
        superFrame.setVisible(true);

    }

    private void visualisation() {
        boxLayout();
        superFrame.repaint();
    }


    private void boxLayout() {

        // Haupt-Panel
        JPanel contentPanel = new JPanel();

        // Menubar hinzufügen
        superFrame.setJMenuBar(this.menuBar);

        // Hauptfenster in zwei Spalten unterteilen
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

        // ContentPanel in den superFrame intigrieren
        this.superFrame.add(contentPanel);

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        contentPanel.add(leftPanel);
        contentPanel.add(rightPanel);

        // Rechte Spalte in zwei Reihen unterteilen
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));


        rightPanel.add(panel_topRight);
        rightPanel.add(panel_bottomRight);

        // Background colors
        leftPanel.setBackground(Color.gray);
        this.panel_topRight.setBackground(Color.white);
        this.panel_bottomRight.setBackground(Color.black);

        // Größe des Linken Bereichs
        leftPanel.setPreferredSize(new Dimension(600, 768));
        leftPanel.setMaximumSize((new Dimension(600, 768)));
                                                         //width, height
        this.panel_topRight.setPreferredSize(new Dimension(424,384));
        this.panel_topRight.setMaximumSize(new Dimension(424, 384));

        this.panel_bottomRight.setPreferredSize(new Dimension(424, 384));
        this.panel_bottomRight.setMaximumSize((new Dimension(424, 384)));

        // Windows Fenster darstellen
        superFrame();

    }





    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public JPanel getPanel_topRight() {
        return this.panel_topRight;
    }

    public JPanel getPanel_bottomRight() {
        return this.panel_bottomRight;
    }



}