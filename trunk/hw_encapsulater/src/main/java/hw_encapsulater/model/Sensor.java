package hw_encapsulater.model;

import de.hsa.jmsconnectiontools.QueueConnector;
import de.hsa.jmsconnectiontools.TopicConnector;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple Class which implements the observer interface.
 *
 * @author Felix Wagner
 *
 */


public class Sensor {
    private String name;
    private Observer observer = new MyObserver();
    private QueueConnector queueConnector;
    private SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
    private boolean isWatcherActive = false;
    private TopicConnector watcherTopic;

    public Sensor(String nm, QueueConnector queueConnector) {
        name = nm;
        this.queueConnector = queueConnector;
    }

    private class MyObserver implements Observer {
    	public void update(Observable ob, Object a) {
    		long tstamp = new Date().getTime();
            try {
                ObjectMessage om = queueConnector.getQueueSession().createObjectMessage();
                om.setObject((Serializable) a);
                om.setStringProperty("class", ob.toString());
                om.setStringProperty("sensorName", name);
                om.setLongProperty("timeStamp", tstamp);
                // type is essential here. CEP-Server uses this to identify the message.
                om.setStringProperty("type", "LightSensor");
                queueConnector.getQueueSender().send(om);
                if (isWatcherActive) {
                    watcherTopic.getPublisher().publish(om);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
    		System.out.println("Sensor: " + name + "; Status: "+ a +"; Timestamp: " + df.format(tstamp));
    	}
    }
    
    public Observer openObserver() {
    	return observer;
    }
    
    public Observer closeObserver() {
    	return observer;
    }

    public void setWatcherActive(TopicConnector tc) {
        this.watcherTopic = tc;
        this.isWatcherActive = true;
    }
}
