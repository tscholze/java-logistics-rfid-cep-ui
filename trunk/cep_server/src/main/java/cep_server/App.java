package cep_server;

import cep_server.control.CEP;
import cep_server.control.InstructionListener;
import de.hsa.jmsconnectiontools.QueueConnector;
import de.hsa.jmsconnectiontools.TopicConnector;

import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws NamingException, JMSException, InterruptedException {

        final QueueConnector gcHwEventQueue = new QueueConnector("/queue/HwEventQueue");
        TopicConnector guiTopicConnector = new TopicConnector("/topic/GuiTopic");
        final TopicConnector iqc = new TopicConnector("/topic/GuiInstructionTopic");
        InstructionListener il = new InstructionListener();

        CEP cep = new CEP(guiTopicConnector);
        gcHwEventQueue.getQueueReceiver().setMessageListener(cep);
        il.addObserver(cep);
        iqc.getSubscriber().setMessageListener(il);

        Runtime.getRuntime().addShutdownHook(new Thread(){

            public void run(){
                try {
                    System.out.println("Shutdown hook");
                    gcHwEventQueue.closeConnection();
                    iqc.closeConnection();
                } catch (JMSException e) {
                    e.printStackTrace();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }

        });
        //@TODO: Fix while(true)
        while (true)
            Thread.sleep(Long.MAX_VALUE);

    }
}
