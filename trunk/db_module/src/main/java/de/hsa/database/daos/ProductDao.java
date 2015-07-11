package de.hsa.database.daos;

import de.hsa.database.entities.Product;
import de.hsa.database.entities.Tag;
import de.hsa.database.util.HibernateUtils;
import org.hibernate.classic.Session;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 1:06 PM
 */
public class ProductDao extends BaseDao<Product> {

    private Session session;


    public ProductDao() {
        super(Product.class);
    }

    public Product loadProductbyEpc(String epc)
    {
        try {
            session = HibernateUtils.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Tag tag = (Tag) session.createQuery("from Tag as t WHERE t.epc = :epc").setParameter("epc", epc).setMaxResults(1).uniqueResult();
            session.getTransaction().commit();
            return tag.getProduct();
        } catch(Exception e) {
            return loadProductByName("Lakritze");
        }
    }

    public Product loadProductByName(String name)
    {
        session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Product product = (Product) session.createQuery("from Product as p WHERE p.name = :name").setParameter("name", name).setMaxResults(1).uniqueResult();
        session.getTransaction().commit();
        return product;
    }
}
