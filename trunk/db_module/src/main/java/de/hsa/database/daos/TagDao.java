package de.hsa.database.daos;

import de.hsa.database.entities.Event;
import de.hsa.database.entities.Rule;
import de.hsa.database.entities.Tag;
import de.hsa.database.util.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 1:09 PM
 */
public class TagDao extends BaseDao<Tag> {
    private Session session;

    public TagDao() {
        super(Tag.class);
    }
    
    public List<Tag> loadByEpc(String epc) {
        session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Tag> tags = session.createQuery("from Tag as t where t.epc = :epc").setParameter("epc", epc).list();
        session.getTransaction().commit();
        return tags;
    }

    public List<Tag> loadByEvent(Event event)
    {
        session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Tag> tags = session.createQuery("from Tag as t where t.event = :event").setParameter("event", event).list();
        session.getTransaction().commit();
        return tags;
    }
}
