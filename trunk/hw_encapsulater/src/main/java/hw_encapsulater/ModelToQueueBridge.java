package hw_encapsulater;

import com.alien.enterpriseRFID.tags.Tag;
import de.hsa.RfidTag;
import de.hsa.jmsconnectiontools.TopicConnector;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import java.util.*;

/**
 * @author : Falk Alexander
 * @date : 18.05.12
 * Time: 18:13
 */
public class ModelToQueueBridge implements Observer {
    QueueSession session;
    QueueSender producer;
    private boolean isWatcherActive = false;
    private TopicConnector watcherTopic;

    public ModelToQueueBridge(QueueSession session, QueueSender producer) {
        this(session, producer, null, false);
    }

    public ModelToQueueBridge(QueueSession session, QueueSender producer, TopicConnector watcherTopic, boolean isWatcherActive) {
        this.session = session;
        this.producer = producer;
        this.watcherTopic = watcherTopic;
        this.isWatcherActive = isWatcherActive;
    }

    @Override
    public void update(Observable o, Object arg) {
        Tag[] arr = (Tag[]) arg;
        List<RfidTag> rfidTagList = new ArrayList();
        for (Tag tag : arr) {
            rfidTagList.add(new RfidTag(tag));
        }
        try {

            ObjectMessage om = session.createObjectMessage(rfidTagList.toArray());
            om.setStringProperty("type", "RFIDTagArray");
            om.setStringProperty("class", rfidTagList.toArray().getClass().getName());
            om.setLongProperty("timeStamp", new Date().getTime());
            producer.send(om);
            if (isWatcherActive) {
                watcherTopic.getPublisher().send(om);
                //System.out.println("send to HwWatcherTopic");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public void setWatcherActive(TopicConnector tp) {
        this.watcherTopic = tp;
        this.isWatcherActive = true;
    }
}