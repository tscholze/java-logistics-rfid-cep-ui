package java_gui.engine;

import de.hsa.RfidTag;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Observable;

/**
 * @author : Falk Alexander
 *         Date: 20.05.12
 *         Time: 10:28
 * This class sistens on a queue for new events from Esper. If a new Event is
 * received, the gui will be notified.
 * @TODO: Skip the observer step.
 */
public class DataProvider extends Observable implements MessageListener {
    @Override
    public void onMessage(Message message) {
        Object[] tags = null;
        try {
            tags = (RfidTag[]) ((ObjectMessage) message).getObject();

        } catch (JMSException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers(tags);
        clearChanged();
    }
}
