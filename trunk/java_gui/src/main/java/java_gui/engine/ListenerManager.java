package java_gui.engine;

import de.hsa.RuleContainer;
import de.hsa.jmsconnectiontools.TopicConnector;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TopicSubscriber;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * @author : Falk Alexander
 *         Date: 26.05.12
 *         Time: 09:16
 */
public class ListenerManager {

    TopicConnector listeningTopic;
    TopicConnector sendingTopic;
    Map<String, Map> activeRules;

    public ListenerManager(TopicConnector listeningTopic, TopicConnector sendingTopic) {
        this.listeningTopic = listeningTopic;
        this.sendingTopic = sendingTopic;
        activeRules = new HashMap<String, Map>();
    }

    public void addRule(RuleContainer ruleContainer, Observer observer) throws JMSException {
        Map<String, Object> activeRule = new HashMap<String, Object>();
        DataProvider dataProvider = new DataProvider();
        dataProvider.addObserver(observer);
        activeRule.put("observer", observer);
        activeRule.put("listener", dataProvider);
        TopicSubscriber ts = listeningTopic.addSubscriber("uniqueName = '" + ruleContainer.getUniqueName() + "'", false);
        ts.setMessageListener(dataProvider);
        activeRule.put("subscriber", ts);
        ObjectMessage om = sendingTopic.getTopicSession().createObjectMessage(ruleContainer);
        om.setStringProperty("uniqueName", ruleContainer.getUniqueName());
        sendingTopic.getPublisher().send(om);
        activeRule.put("uniqueName", ruleContainer.getUniqueName());
        this.activeRules.put((String) activeRule.get("uniqueName"), activeRule);
    }

    public void removeRule(RuleContainer ruleContainer) throws JMSException {
        this.removeRule(ruleContainer.getUniqueName());
    }

    public void removeRule(String uniqueName) throws JMSException {
        Map ruleMap = this.activeRules.get(uniqueName);
		if (ruleMap != null) {
			((Observable) ruleMap.get("listener")).deleteObserver((Observer) ruleMap.get("observer"));
			((TopicSubscriber) ruleMap.get("subscriber")).setMessageListener(null);
			// remove rule from activesRules
			this.activeRules.remove(uniqueName);
			// create minimal RuleContainer with the addEvent flag set to false
			RuleContainer rc = new RuleContainer(uniqueName, "", "", "", "", false);
			ObjectMessage om = sendingTopic.getTopicSession().createObjectMessage(rc);
			om.setStringProperty("uniqueName", rc.getUniqueName());
			sendingTopic.getPublisher().send(om);
		}
    }
}
