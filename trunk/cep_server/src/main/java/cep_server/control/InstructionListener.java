package cep_server.control;

import de.hsa.RuleContainer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Observable;

/**
 * @author : Falk Alexander
 *         Date: 20.05.12
 *         Time: 11:10
 */
public class InstructionListener extends Observable implements MessageListener {
    @Override
    public void onMessage(Message message) {
        RuleContainer rc = null;
        try {
            rc = (RuleContainer) ((ObjectMessage) message).getObject();

        } catch (JMSException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers(rc);
    }
}
