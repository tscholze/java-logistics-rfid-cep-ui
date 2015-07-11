package java_gui.view;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Miriam
 * Date: 10.05.12
 * Time: 10:20
 * To change this template use File | Settings | File Templates.
 */
public class MenuBar {

    private JMenuBar menuBar;

    public MenuBar() {
        super();

        this.menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("Antenna");
        JMenu menu2 = new JMenu("About");

        JMenuItem menu1_menuItem1 = new JMenuItem("Einstellungen");
        JMenuItem menu1_menuItem2 = new JMenuItem("Info");
        JMenuItem menu2_menuItem1 = new JMenuItem("Tiefseeanglerfisch");

        menuBar.add(menu1);
        menuBar.add(menu2);
        menu1.add(menu1_menuItem1);
        menu1.add(menu1_menuItem2);
        menu2.add(menu2_menuItem1);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

}
