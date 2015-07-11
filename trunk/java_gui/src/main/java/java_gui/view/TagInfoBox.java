package java_gui.view;


import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Miriam
 * Date: 10.05.12
 * Time: 11:09
 * To change this template use File | Settings | File Templates.
 */
public class TagInfoBox {

    private JPanel panel = new JPanel();

    private JPanel panel_top = new JPanel();
    private JPanel panel_bottom = new JPanel();
    private JPanel panel_bottomLeft = new JPanel();
    private JPanel panel_bottomRight = new JPanel();


    // Constructor
    public TagInfoBox() {
        super();
        boxLayout();
        content();
    }

    private void boxLayout() {

        // Haupt-Panel Horizontal aufeilen
        this.panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        this.panel.add(panel_top);
        this.panel.add(panel_bottom);

        // Bottom Panel Vertikal aufeilen
        this.panel_bottom.setLayout(new BoxLayout(panel_bottom, BoxLayout.X_AXIS));

        this.panel_bottom.add(panel_bottomLeft);
        this.panel_bottom.add(panel_bottomRight);

    }

    private void content() {

        panel_top.setLayout(new BoxLayout(panel_top, BoxLayout.Y_AXIS));

        JPanel panel_1 = new JPanel();
        JPanel panel_2 = new JPanel();
        JPanel panel_3 = new JPanel();
        JPanel panel_4 = new JPanel();
        JPanel panel_5 = new JPanel();

        panel_top.add(panel_1);
        panel_top.add(panel_2);
        panel_top.add(panel_3);
        panel_top.add(panel_4);
        panel_top.add(panel_5);

        // Größe setzen
        panel_top.setPreferredSize(new Dimension(600, 290));
        panel_top.setMaximumSize((new Dimension(600, 290)));

        JLabel headline = new JLabel("Tag Information", JLabel.RIGHT);
        JLabel tagID = new JLabel("Tag ID: 4747475.34799.2364.44", JLabel.LEFT);
        JLabel antenna = new JLabel("Gelesen von Antenne: 2", JLabel.LEFT);
        JLabel time = new JLabel("Timestamp: 15:30:44", JLabel.LEFT);
        JLabel position = new JLabel("Position: x y z", JLabel.LEFT);

        JButton anzeigeButton = new JButton("Anzeigen");


        panel_1.add(headline);
        panel_2.add(tagID);
        panel_3.add(antenna);
        panel_4.add(time);
        panel_5.add(position);

        this.panel_bottomRight.add(anzeigeButton);

    }


    public JPanel getTagInfoBox() {
        return this.panel;
    }



}
