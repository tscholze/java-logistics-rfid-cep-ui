package de.hsa.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 7:34 PM
 */
@Entity
public class Rule
        extends BaseEntity
{
    @Column(unique=true)
    private String name;
    private String syntax;
    private String description;
    private Calendar creationDate = Calendar.getInstance();
    private boolean isActive = false;

    public Rule()
    {
        this.name = "Not set yet";
        this.syntax = "No syntax yet";
    }

    public Rule(String name, String description, String syntax)
    {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
    }

    public Rule(String name)
    {
        this();
        this.name = name;
    }

    @Override
    public String toString()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return name + " created at: " + dateFormat.format(creationDate.getTime());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule)o;

        if (isActive != rule.isActive) return false;
        if (!creationDate.equals(rule.creationDate)) return false;
        if (name != null ? !name.equals(rule.name) : rule.name != null)
            return false;
        if (syntax != null ? !syntax.equals(rule.syntax) : rule.syntax != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (syntax != null ? syntax.hashCode() : 0);
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String displayName()
    {
        return name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSyntax()
    {
        return syntax;
    }

    public void setSyntax(String syntax)
    {
        this.syntax = syntax;
    }

    public Calendar getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate)
    {
        this.creationDate = creationDate;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
