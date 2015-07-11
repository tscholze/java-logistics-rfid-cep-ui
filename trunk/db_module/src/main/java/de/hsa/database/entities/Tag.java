package de.hsa.database.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 12:07 PM
 */

@Entity
public class Tag extends BaseEntity implements Cloneable {
    private String epc;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Event event;
    private Calendar creationDate = Calendar.getInstance();
    private Calendar deletionDate;

    public Tag() {}

    public Event getEvent()
    {
        return event;
    }

    public void setEvent(Event event)
    {
        this.event = event;
    }

    public Tag(String epc) {
        this.epc = epc;
    }

    public Tag(String epc, Product product)
    {
        this.epc = epc;
        this.product = product;
    }
    
    public Tag(String epc, Product product, Event event)
    {
        this.epc = epc;
        this.product = product;
        this.event = event;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public Product getProduct() {
        if(product != null) {
            return product;
        }
        else {
            return new Product("no Product given");
        }
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public Calendar getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(Calendar deletionDate) {
        this.deletionDate = deletionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag)o;

        if (!creationDate.equals(tag.creationDate)) return false;
        if (deletionDate != null ? !deletionDate.equals(tag.deletionDate) : tag.deletionDate != null)
            return false;
        if (!epc.equals(tag.epc)) return false;
        if (product != null ? !product.equals(tag.product) : tag.product != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = epc.hashCode();
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + (deletionDate != null ? deletionDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()  {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return epc+" created at: "+dateFormat.format(creationDate.getTime());

    }

    @Override
    public String displayName() {
        return toString();
    }
}
