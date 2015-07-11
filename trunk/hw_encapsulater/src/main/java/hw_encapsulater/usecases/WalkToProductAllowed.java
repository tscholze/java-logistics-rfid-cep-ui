package hw_encapsulater.usecases;

import de.hsa.jmsconnectiontools.QueueConnector;
import de.hsa.jmsconnectiontools.TopicConnector;
import hw_encapsulater.ModelToQueueBridge;
import hw_encapsulater.model.LightSensor;
import hw_encapsulater.model.Sensor;
import hw_encapsulater.model.Wait;
import hw_encapsulater.model.events.Visitor;


import javax.jms.JMSException;
import javax.naming.NamingException;
import java.io.IOException;

/**
 * First one Visitor walks through the reader goes to the product.
 * He only carries his visitor Tag
 */

public class WalkToProductAllowed {
    public static void main(String[] args) throws ClassNotFoundException, IOException, NamingException, JMSException, InterruptedException {
        QueueConnector qa = new QueueConnector("/queue/HwEventQueue");
        TopicConnector watcherTopic = new TopicConnector("/topic/HwWatcherTopic");
        ModelToQueueBridge modelToQueueBridge = new ModelToQueueBridge(qa.getQueueSession(), qa.getQueueSender(), watcherTopic, true);
        modelToQueueBridge.setWatcherActive(watcherTopic);

        /**
         * Instantiating two usecases
         */

        Visitor usecase = new Visitor();

        /**
         * Adding usecases to the model queue of jms if
         * I understand this correctly
         */

        usecase.addObserver(modelToQueueBridge);

        /**
         *  Instantiating two Light sensors
         */

        LightSensor A = new LightSensor();
        LightSensor B = new LightSensor();
        Sensor one = new Sensor("A", qa);
        one.setWatcherActive(watcherTopic);
        Sensor two = new Sensor("B", qa);
        two.setWatcherActive(watcherTopic);

        /**
         * Setup complete now starting with the usecase
         */

        System.out.println("Starting Sensor");
        A.connected().addObserver(one.openObserver());
        A.open();
        B.connected().addObserver(two.openObserver());
        B.open();

        /**
         * someone goes through light sensor A
         */

        B.disconnected().addObserver(one.closeObserver());
        B.close();
        B.connected().addObserver(one.openObserver());
        B.open();

        /**
         * we read all tags for 5 seconds
         */

        usecase.start();
        Wait.manySec(5);
        usecase.stop();

        /**
         * someone goes through light sensor B
         */


        A.disconnected().addObserver(two.closeObserver());
        A.close();
        A.connected().addObserver(two.openObserver());
        A.open();


        /**
         * cleaning up jms
         */

        qa.closeConnection();
        watcherTopic.closeConnection();

    }
}
