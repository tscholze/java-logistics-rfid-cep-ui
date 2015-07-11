package java_gui.view;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: yu-ni
 * Date: 09.05.12
 * Time: 16:08plates.
 */
public class ControlBox {

    private JPanel panel = new JPanel();;
    private JPanel panel_1 = new JPanel();
    private JPanel panel_2 = new JPanel();
    private JPanel panel_3 = new JPanel();
    private JPanel panel_4 = new JPanel();


    // Constructor
    public ControlBox() {
        super();
        boxLayout();
        content();
    }

    public void boxLayout() {

        this.panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        this.panel.add(panel_1);
        this.panel.add(panel_2);
        this.panel.add(panel_3);
        this.panel.add(panel_4);
    }

    private void content() {
        JLabel tagLabel = new JLabel("34/64 Tags gefunden");
        JProgressBar progressBar = new JProgressBar();
        JButton startButton = new JButton("START");
        JButton stopButton = new JButton("STOP");

        this.panel_1.add(tagLabel);
        this.panel_2.add(progressBar);
        this.panel_3.add(startButton);
        this.panel_3.add(stopButton);
    }


    public JPanel getControlBox() {
        return panel;
    }



}
