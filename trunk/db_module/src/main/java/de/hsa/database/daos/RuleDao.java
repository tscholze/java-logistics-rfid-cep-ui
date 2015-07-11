package de.hsa.database.daos;

import de.hsa.database.entities.Rule;
import de.hsa.database.util.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.Entity;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 7:45 PM
 */
@Entity
public class RuleDao
        extends BaseDao<Rule>
{

    private Session session;

    public RuleDao()
    {
        super(Rule.class);
    }

    public Rule loadByName(String name)
    {
        session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Rule rule = (Rule)session.createQuery("from Rule as r where r.name = :name").setParameter("name", name).uniqueResult();
        session.getTransaction().commit();
        return rule;
    }
}
