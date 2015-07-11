package hw_encapsulater.model.lightbarrier;

import de.hsa.jmsconnectiontools.QueueConnector;
import de.hsa.jmsconnectiontools.TopicConnector;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;
import java.util.Date;


public class LightBarrierSPListener implements SerialPortEventListener {
	
	private QueueConnector queueConnector;
	private TopicConnector topicConnector;
	
	private boolean watcherActive = true;
	private boolean cepActive = true;
	
	public LightBarrierSPListener(String queueName, String topicName)
	{
		super();
		try {
			if(cepActive)
			{
				queueConnector = new QueueConnector(queueName);
			}
			if(watcherActive)
			{
				topicConnector = new TopicConnector(topicName);
			}	
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
        Runtime.getRuntime().addShutdownHook(new Thread(){

            public void run(){
                try {
                    System.out.println("Shutdown hook");
                    queueConnector.closeConnection();
                    topicConnector.closeConnection();
                } catch (JMSException e) {
                    e.printStackTrace();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }

        });
	}
	public void serialEvent(SerialPortEvent event){
		
		String lichtSchranke = "";
		boolean verbunden = false;
		
		if(event.getEventType() == SerialPortEvent.DSR)
		{
			lichtSchranke = "A";
		}
		else if(event.getEventType() == SerialPortEvent.CD)
		{
			lichtSchranke = "B";
		}
		
		if(event.getNewValue()) // unterbrochen
		{
			verbunden = false;
		}
		else // verbunden
		{
			verbunden = true;
		}
		
		
		try {
			if(cepActive)
			{
				ObjectMessage om;
				om = queueConnector.getQueueSession().createObjectMessage();
				om.setObject(verbunden);
				om.setLongProperty("timeStamp", new Date().getTime());
				om.setStringProperty("sensorName", lichtSchranke);
				om.setStringProperty("type", "LightSensor");
				queueConnector.getQueueSender().send(om);
				
			}
			if(watcherActive)
			{
				ObjectMessage om;
				om = topicConnector.getTopicSession().createObjectMessage();
				om.setObject(verbunden);
                om.setLongProperty("timeStamp", new Date().getTime());
				om.setStringProperty("sensorName", lichtSchranke);
				om.setStringProperty("type", "LightSensor");
				topicConnector.getPublisher().publish(om);
			}
			String s = verbunden ? "verbunden" : "unterbrochen";
			System.out.println("Lichtschranke " + lichtSchranke + " " + s);

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}


