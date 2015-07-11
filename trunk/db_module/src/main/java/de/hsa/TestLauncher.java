package de.hsa;

import de.hsa.database.dummy.DummyDataGenerator;
import de.hsa.database.entities.*;
import de.hsa.database.jms.RuleListener;
import de.hsa.database.jms.TriggeredCepEventsListener;
import de.hsa.database.services.DummyService;
import de.hsa.database.services.EventService;
import de.hsa.database.services.TagService;
import de.hsa.jmsconnectiontools.TopicConnector;

import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 12:06 PM
 * <p/>
 * Server Start:
 * 1) cd path/to/module/
 * 2) mvn install
 * 3) mvn exec:java -Dexec.mainClass="org.hsqldb.Server" -Dexec.args="-database.0 file:target/data/db_module"
 */
public class TestLauncher
{


    public static void main(String[] args) throws NamingException, JMSException, InterruptedException
    {
        DummyService dummyService = new DummyService();

        // Generate Dummy Data...
        dummyService.generateDummyData();

        // Dumps all Data (Tags, Products, Events, Rules)
        //dummyService.dumpDummyData();

        /**

         // JMS Code...
         // listens to triggered events
         TopicConnector guiTopic = new TopicConnector("/topic/GuiTopic");
         guiTopic.getSubscriber().setMessageListener(new TriggeredCepEventsListener());

         // listens to new created rules, that are accepted in cep
         TopicConnector guiInstructionTopic = new TopicConnector("/topic/GuiInstructionTopic");
         guiInstructionTopic.getSubscriber().setMessageListener(new RuleListener());

         //@TODO: replace this sleep with a suitable server management
         Thread.sleep(60000);
         **/

        // TagService Tests
        // TagService tagService = new TagService();

        // load by epc Test
        // tagService.dumpTagByEpc("E200 3411 B802 0110 2608 7606");

        // load latest event Test
        // EventService eventService = new EventService();
        // de.hsa.database.entities.Event latestEvent = eventService.loadLatest();
        // System.out.println(latestEvent.toString());

    }
}
