package de.hsa.database.entities;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 12:32 PM
 */
public interface IBaseEntity
        extends Cloneable, Serializable {

    /**
     * @return Long primary identifier ob the object
     */
    long getId();

    /**
     * @param id Setter of primary key. Will be done automatically by hibernate persisting framework.
     */
    void setId(long id);

    /**
     * @return String representation with all attributes of the entity
     */
    String toString();

    /**
     * Compare method for entities
     *
     * @param o Entity that should be compared with this
     * @return true if objects are equal
     */
    boolean equals(Object o);

    /**
     * @return HashCode for iteration
     */
    int hashCode();

    /**
     * @return Basic String representation of the entity, to ensure equal display of entity in view
     */
    String displayName();
}
