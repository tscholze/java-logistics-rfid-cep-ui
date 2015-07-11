package de.hsa;

import de.hsa.database.jms.RuleListener;
import de.hsa.database.jms.TriggeredCepEventsListener;
import de.hsa.jmsconnectiontools.TopicConnector;

import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * Created with IntelliJ IDEA.
 * User: falk
 * Date: 5/24/12
 * Time: 10:04 AM
 * Initialize the subscribers to listen to the topics.
 */
public class RunListener {
    public static void main( String[] args ) throws NamingException, JMSException, InterruptedException {
        final TopicConnector genericConnector = new TopicConnector("/topic/GuiTopic");
        genericConnector.getSubscriber().setMessageListener(new TriggeredCepEventsListener());
        final TopicConnector newRuleCon = new TopicConnector("/topic/GuiInstructionTopic");
        newRuleCon.getSubscriber().setMessageListener(new RuleListener());
        Runtime.getRuntime().addShutdownHook(new Thread(){

            public void run(){
                try {
                    System.out.println("Shutdown hook");
                    genericConnector.closeConnection();
                    newRuleCon.closeConnection();
                } catch (JMSException e) {
                    e.printStackTrace();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }

        });
        //@TODO: fix up while(true)
        while (true)
            Thread.sleep(Long.MAX_VALUE);
    }
}
