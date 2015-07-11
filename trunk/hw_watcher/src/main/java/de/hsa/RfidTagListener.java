package de.hsa;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * @author : Falk Alexander
 *         Date: 04.06.12
 *         Time: 20:56
 */
public class RfidTagListener extends Observable implements MessageListener {



    @Override
    public void onMessage(Message message) {
        Object[] s = null;
        ObjectMessage om = null;
        Map<String, Object> TagMessage = new HashMap();
        try {
            om = (ObjectMessage) message;
            s =  (Object[]) om.getObject();
            TagMessage.put("timeStamp", om.getLongProperty("timeStamp"));
            TagMessage.put("type", om.getStringProperty("type"));
            TagMessage.put("class", om.getStringProperty("class"));
        } catch (JMSException e) {
            e.printStackTrace();
        }
        RfidTag[] tags = new RfidTag[s.length];
        for (int i = 0; i < s.length; i++) {
            tags[i] = (RfidTag) s[i];
        }
        TagMessage.put("content", tags);
        this.notifyObservers(TagMessage);
        setChanged();
    }
}
