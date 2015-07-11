package de.hsa;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * @author : Falk Alexander
 *         Date: 04.06.12
 *         Time: 20:56
 */
public class LSListener extends Observable implements MessageListener {

    @Override
    public void onMessage(Message message) {
        Serializable s = null;
        ObjectMessage om = null;
        Map<String, Object> LSMessage = new HashMap();
        try {
            om = (ObjectMessage) message;
            s = om.getObject();
            LSMessage.put("timeStamp", om.getLongProperty("timeStamp"));
            LSMessage.put("type", om.getStringProperty("type"));
            LSMessage.put("class", om.getStringProperty("class"));
            LSMessage.put("sensorName", om.getStringProperty("sensorName"));
        } catch (JMSException e) {
            e.printStackTrace();
        }

        LSMessage.put("status", s.toString());
        this.notifyObservers(LSMessage);
        setChanged();
    }
}