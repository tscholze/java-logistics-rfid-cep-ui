package de.hsa.database.daos;

import de.hsa.database.entities.BaseEntity;
import de.hsa.database.entities.IBaseUpdateable;
import de.hsa.database.util.HibernateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 12:57 PM
 */
public class BaseDao<E extends BaseEntity> {
    private static Logger logger = Logger.getLogger(BaseDao.class);
    private Session session;
    protected Class<E> entityClass;

    public BaseDao(final Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public BaseDao() {}

    public Class<E> getEntityClass() {
        return entityClass;
    }

    public Criteria getCriteria() {
        return session.createCriteria(entityClass);
    }

    public E insert(E e) {
        session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        long id = (Long)session.save(e);
        session.getTransaction().commit();
        return load(id);
    }

    public E load(long id) {
        session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return (E)session.get(getEntityClass(), id);
    }

    public E update(E e) {
        E entity = e;
        try {
            entity = update(e, true);
        }
        catch (Exception ex) {
            logger.warn(ex);
        }
        return entity;
    }

    public E update(E e, Boolean flush) {
        try {
            session = HibernateUtils.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            if (e instanceof IBaseUpdateable) {
                ((IBaseUpdateable)e).setLastModifiedDate(Calendar.getInstance());
            }
            try {
                session.update(e);
            }
            catch (final NonUniqueObjectException ex) {
                session.merge(ex);
            }
            if (flush) {
                session.flush();
            }
            session.getTransaction().commit();
        }
        catch (Exception ex) {
            logger.warn(ex);
        }
        return e;
    }

    public List<E> listAll() {
        session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return getCriteria().list();
    }
}
