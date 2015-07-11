package java_gui.view;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yu-ni
 * Date: 06.05.12
 * Time: 15:48
 */
public class Test {

    public static void main (String[] args)  throws IOException {
        MenuBar menuBar = new MenuBar();
        ControlBox controlBox = new ControlBox();
        TagInfoBox tagInfoBox = new TagInfoBox();
        // Cube cube = new Cube();

        Layout layout = new Layout(menuBar.getMenuBar(), controlBox.getControlBox(), tagInfoBox.getTagInfoBox());




    }
}
