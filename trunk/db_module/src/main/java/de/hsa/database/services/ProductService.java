package de.hsa.database.services;

import de.hsa.database.daos.BaseDao;
import de.hsa.database.daos.ProductDao;
import de.hsa.database.entities.Product;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 1:33 PM
 */
public class ProductService extends BaseDao<Product> {
    private ProductDao dao = new ProductDao();

    public ProductService() {
        super(Product.class);
    }

    public Product insert(Product newProduct) {
        return dao.insert(newProduct);
    }

    public List<Product> listAll() {
        return dao.listAll();
    }

    public Product loadProductByEpc(String epc) {
        return dao.loadProductbyEpc(epc);
    }

    public void dumpProductList() {
        System.out.println("--- Dump Product List ---");
        for (Product product: listAll()) {
            System.out.println(product.toString());
            System.out.println("------------------");
        }
    }
}
