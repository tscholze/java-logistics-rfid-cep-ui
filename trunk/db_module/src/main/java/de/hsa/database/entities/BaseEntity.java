package de.hsa.database.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 12:27 PM
 */
@MappedSuperclass
public abstract class BaseEntity implements IBaseEntity {

    /**
     * Primary Key for an entities (Long)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * @return Long primary identifier ob the object
     */
    public long getId() {
        return id;
    }

    /**
     * @param id setter of primary key. Will be done automatically by hibernate persisting framework.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return String representation with all attributes of the entity
     */
    public abstract String toString();

    /**
     * Compare method for entities
     *
     * @param o Entity that should be compared with this
     * @return true if objects are equal
     */
    public abstract boolean equals(Object o);

    /**
     * @return HashCode for iteration
     */
    public abstract int hashCode();

    /**
     * @return Basic String representation of the entity, to ensure equal display of entity in view
     */
    public abstract String displayName();

}
