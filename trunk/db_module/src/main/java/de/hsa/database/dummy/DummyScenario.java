package de.hsa.database.dummy;

import de.hsa.database.entities.Event;
import de.hsa.database.entities.Product;
import de.hsa.database.entities.Rule;
import de.hsa.database.entities.Tag;
import de.hsa.database.services.*;
import de.hsa.database.util.HibernateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 1:19 PM
 */
public class DummyScenario {
    private TagService tagService = new TagService();
    private ProductService productService = new ProductService();
    private DummyService dummyService = new DummyService();
    private EventService eventService = new EventService();
    private RuleService ruleService = new RuleService();

    public DummyScenario() {
        System.out.println("--- Start Dummy Scenario Class ---");
    }

    public void play() {
        dummyService.generateDummyData();
        HibernateUtils.getSessionFactory().getCurrentSession().close();
    }

    public void dump()
    {
        dummyService.dumpDummyData();
    }

    @Deprecated
    public void dummyEvent()
    {
        // Anstatt new Rule koennte hier auch eine Rule ID oder sonstiges angegeben werden,
        // wo nach dann ein .load(id) auf die rule aufgerufen wird bevor sie ins Event gespeichert
        // wird.
        String ruleName = "Foobar";
        
        // Erstelle Rule mit dem Namen.
        Rule rule = new Rule(ruleName);

        // Speichere die URL in die Datenbank, dies kann natuerlich zu jedem Zeitpunkt passieren.
        ruleService.insert(rule);

        // Erstelle ein neues Event, und lade die Rule durch den Namen.
        Event event = new Event(ruleService.loadByName(ruleName));
        
        // Event vor Tags speichern!
        eventService.insert(event);

        Tag tag1 = new Tag("01010");
        // Event wie ein normales Attribut setzen
        tag1.setEvent(event);
        tagService.insert(tag1);

        Tag tag2 = new Tag("02020");
        tag2.setEvent(event);
        tagService.insert(tag2);

        tagService.dumpTagList();
    }
}
