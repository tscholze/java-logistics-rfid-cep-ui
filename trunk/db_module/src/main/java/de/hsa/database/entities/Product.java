package de.hsa.database.entities;

import javax.persistence.Entity;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 12:18 PM
 */

@Entity
public class Product extends BaseEntity implements Cloneable {
    private String name;
    private String note;
    private Calendar creationDate;
    private boolean isActive;

    public Product() {
        setCreationDate(Calendar.getInstance());
    }

    public Product(String name) {
        this.name = name;
        setCreationDate(Calendar.getInstance());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product)o;

        if (isActive != product.isActive) return false;
        if (!creationDate.equals(product.creationDate)) return false;
        if (!name.equals(product.name)) return false;
        if (note != null ? !note.equals(product.note) : product.note != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product (#"+getId()+"): "+ name;
    }

    @Override
    public String displayName() {
        return name;
    }
}
