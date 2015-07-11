package de.hsa.database.jms;

import de.hsa.RfidTag;
import de.hsa.database.entities.Event;
import de.hsa.database.entities.Rule;
import de.hsa.database.entities.Tag;
import de.hsa.database.services.EventService;
import de.hsa.database.services.ProductService;
import de.hsa.database.services.RuleService;
import de.hsa.database.services.TagService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/19/12
 * Time: 9:33 AM
 */
public class TriggeredCepEventsListener implements MessageListener {
    
    private EventService eventService = new EventService();
    private TagService tagService = new TagService();
    private RuleService ruleService = new RuleService();
    private ProductService productService = new ProductService();

    @Override
    public void onMessage(Message message) {
        Object obj = null;
        try {
            obj = ((ObjectMessage) message).getObject();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        Rule r = null;
        try {
            // Pruefe ob der Name der Rule einzigartig ist
            String uniqueName = message.getStringProperty("uniqueName");
            if(ruleService.checkIsUnique(uniqueName))
            {
                // Wenn ja -> Rule existiert nicht -> erstelle sie -> zu testzwecken.
                // Name, Description, Syntax
                r = new Rule(uniqueName, "i was added in a time of need", "i don't know my syntax");
                ruleService.insert(r);
            }
            else
            {
                // wenn nicht einzigartig -> Rule existiert -> lade dieses Objekt
                r = ruleService.loadByName(uniqueName);
            }
        } catch (JMSException e) {
            e.printStackTrace();
            System.out.println("Konnte JMS nicht versterben :( - gehe sterben.");
            System.exit(1);
        }

        Event event = new Event(r);
        eventService.insert(event);
        
        RfidTag[] rfidTags = (RfidTag[]) obj;

        for (RfidTag tag : rfidTags)
        {
            System.out.println("Mache: "+tag.getTagID());
            Tag tmpTag = new Tag();
            tmpTag.setEpc(tag.getTagID());
            tmpTag.setEvent(event);
            tmpTag.setProduct(productService.loadProductByEpc(tag.getTagID()));
            System.out.println(tmpTag.toString());
            tagService.insert(tmpTag);
        }
    }
}
