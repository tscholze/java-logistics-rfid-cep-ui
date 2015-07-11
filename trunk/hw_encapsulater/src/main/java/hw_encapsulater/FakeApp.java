package hw_encapsulater;

import de.hsa.jmsconnectiontools.QueueConnector;
import de.hsa.jmsconnectiontools.TopicMgr;
import hw_encapsulater.model.GenericModel;
import hw_encapsulater.model.localtestmodel.FakeTestModel;

import javax.jms.JMSException;
import javax.naming.NamingException;

public class FakeApp {

	/**
	 * @param args
	 * @throws JMSException 
	 * @throws NamingException 
	 */
	public static void main(String[] args) throws NamingException, JMSException {
        QueueConnector qa = new QueueConnector("/queue/HwEventQueue");
        ModelToQueueBridge modelToQueueBridge = new ModelToQueueBridge(qa.getQueueSession(), qa.getQueueSender());
        
        TopicMgr controltopic = new TopicMgr("localhost", "1099", "topic/controltopic", true, true);
        
//        GenericModel genericModel = new ChangeingTagsModel();
//        genericModel.addObserver(modelToQueueBridge);
//        genericModel.start();
        
        GenericModel myModel = new FakeTestModel(500, 22, controltopic);
        myModel.addObserver(modelToQueueBridge);
        myModel.run();
	}

}
