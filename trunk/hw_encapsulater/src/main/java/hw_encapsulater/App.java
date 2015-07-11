package hw_encapsulater;

import de.hsa.jmsconnectiontools.QueueConnector;
import de.hsa.jmsconnectiontools.TopicConnector;
import de.hsa.jmsconnectiontools.TopicMgr;
import hw_encapsulater.model.GenericModel;
import hw_encapsulater.model.alienmodel.Model;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws ClassNotFoundException, IOException, NamingException, JMSException, InterruptedException {
        QueueConnector qa = new QueueConnector("/queue/HwEventQueue");
        boolean watcher = true;
        TopicConnector watcherTopic = null;
        try {
            watcherTopic = new TopicConnector("/topic/HwWatcherTopic");
        } catch (JMSException e) {
            System.out.println("It seems you are not using a watcher. I'm deactivating it.");
            watcher = false;
        }
        ModelToQueueBridge modelToQueueBridge = new ModelToQueueBridge(qa.getQueueSession(), qa.getQueueSender(), watcherTopic, watcher);

        TopicMgr controltopic = new TopicMgr("192.168.1.2", "1099", "topic/controltopic", true, true);

        GenericModel myModel = new Model(controltopic);
        myModel.addObserver(modelToQueueBridge);
        myModel.run();
    }
}
