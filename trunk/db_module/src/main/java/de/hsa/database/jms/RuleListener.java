package de.hsa.database.jms;

import de.hsa.RuleContainer;
import de.hsa.database.entities.Rule;
import de.hsa.database.services.RuleService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Created by IntelliJ IDEA.
 * User: Falk Alexander
 * Date: 5/27/12
 * Time: 10:07 AM
 *
 * This listener observes incoming rules and adds them to the DB.
 */
public class RuleListener implements MessageListener {

    private RuleService ruleService = new RuleService();

    @Override
    public void onMessage(Message message) {
        RuleContainer ruleContainer = null;
        try {
            ruleContainer = (RuleContainer) ((ObjectMessage) message).getObject();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        // map the ruleContainer to a DB rule. This mapping avoids duplication
        // of code and complex dependencies.
        Rule r = null;
        r = new Rule(ruleContainer.getUniqueName(), ruleContainer.getDescription(), ruleContainer.getRule());
        r.setActive(ruleContainer.isAddEvent());
        ruleService.insert(r);

    }
}
