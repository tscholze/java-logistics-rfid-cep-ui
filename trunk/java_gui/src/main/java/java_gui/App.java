package java_gui;

import de.hsa.jmsconnectiontools.TopicConnector;
import de.hsa.jmsconnectiontools.TopicMgr;
import java_gui.engine.ButtonObserver;
import java_gui.engine.ListenerManager;
import java_gui.view.Gui;

import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws NamingException, JMSException, InterruptedException {
        TopicConnector guiTopicConnector = new TopicConnector("/topic/GuiTopic");

        TopicMgr controlTopic = new TopicMgr("192.168.178.98", "1099", "/topic/controltopic", true, true);

        Gui gui = new Gui(controlTopic);

        TopicConnector itc = new TopicConnector("/topic/GuiInstructionTopic");
        ListenerManager lm = new ListenerManager(guiTopicConnector, itc);
        ButtonObserver buttonObserver = new ButtonObserver(gui, lm);

        // this loop is needed to keep the jms connection open, otherwise
        // hornetq will clean up needed connections after a certain time
        while (true)
            Thread.sleep(Long.MAX_VALUE);
    }
}
