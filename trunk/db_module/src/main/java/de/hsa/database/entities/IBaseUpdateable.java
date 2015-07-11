package de.hsa.database.entities;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 1:01 PM
 */
public interface IBaseUpdateable {
    /**
     * @return Calendar date when the object was modified the last time
     */
    public Calendar getLastModifiedDate();

    /**
     * @param lastModifiedDate Calendar date when the object was updated
     */
    public void setLastModifiedDate(Calendar lastModifiedDate);
}
