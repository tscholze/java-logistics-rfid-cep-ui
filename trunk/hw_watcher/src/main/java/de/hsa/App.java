package de.hsa;

import de.hsa.CmdUI.LSPrinter;
import de.hsa.CmdUI.TagPrinter;
import de.hsa.jmsconnectiontools.TopicConnector;

import javax.jms.JMSException;
import javax.jms.TopicSubscriber;
import javax.naming.NamingException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws NamingException, JMSException, InterruptedException {
        TopicConnector hwWatcherTopic = new TopicConnector("/topic/HwWatcherTopic");
        String queryStringLS = "type='LightSensor'";
        TopicSubscriber qr = hwWatcherTopic.addSubscriber(queryStringLS, true);
        LSListener lsl = new LSListener();
        qr.setMessageListener(lsl);
        lsl.addObserver(new LSPrinter());
        String queryStringTags = "type='RFIDTagArray'";
        TopicSubscriber tr = hwWatcherTopic.addSubscriber(queryStringTags, true);
        RfidTagListener rtl = new RfidTagListener();
        tr.setMessageListener(rtl);
        rtl.addObserver(new TagPrinter());
        while (true)
           Thread.sleep(Long.MAX_VALUE);
    }
}
