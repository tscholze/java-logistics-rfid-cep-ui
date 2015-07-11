package de.hsa.database.services;

import de.hsa.database.daos.BaseDao;
import de.hsa.database.daos.EventDao;
import de.hsa.database.daos.ProductDao;
import de.hsa.database.entities.Event;
import de.hsa.database.entities.Product;
import de.hsa.database.entities.Rule;
import de.hsa.database.entities.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 10:34 PM
 */
public class EventService
        extends BaseDao<Event>
{
    private EventDao dao = new EventDao();
    private TagService tagService = new TagService();
    private RuleService ruleService = new RuleService();

    public EventService()
    {
        super(Event.class);
    }

    public Event insert(Event newEvent)
    {
        return dao.insert(newEvent);
    }

    public List<Event> listAll()
    {
        return dao.listAll();
    }

    public void dumpEventList()
    {
        System.out.println("--- Dump Event List ---");
        for (Event event : listAll())
        {
            System.out.println(event.toString());
            System.out.println("------------------");
        }
    }

    public Event loadLatest()
    {
        return dao.loadLatest();
    }
}
