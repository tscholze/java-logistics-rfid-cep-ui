package de.hsa.database.daos;

import de.hsa.database.entities.Event;
import de.hsa.database.entities.Tag;
import de.hsa.database.util.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 10:33 PM
 */

public class EventDao extends BaseDao<Event> {
    private Session session;

    public EventDao() {
        super(Event.class);
    }
    
    public Event loadLatest()
    {
        session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Event event = (Event) session.createQuery("from Event as e order by e.triggerDate DESC").setMaxResults(1).uniqueResult();
        session.getTransaction().commit();
        return event;
    }
}
