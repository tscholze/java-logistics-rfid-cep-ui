package de.hsa.database.entities;

import javax.persistence.Entity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 10:28 PM
 */

@Entity
public class Event extends BaseEntity {

    //@ElementCollection
    //private List<Tag> tagList;
    private Rule rule;
    private Calendar triggerDate = Calendar.getInstance();

    public Event() {}

    public Event(Rule rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //return "Rule: "+rule.toString()+" triggered at: "+dateFormat.format(triggerDate.getTime());
        return rule.displayName();
    }

    @Override
    public String displayName() {
        return toString();
    }

    public Calendar getTriggerDate()
    {
        return triggerDate;
    }

    public void setTriggerDate(Calendar triggerDate)
    {
        this.triggerDate = triggerDate;
    }

    public Rule getRule()
    {
        return rule;
    }

    public void setRule(Rule rule)
    {
        this.rule = rule;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event)o;

        if (!rule.equals(event.rule)) return false;
        if (triggerDate != null ? !triggerDate.equals(event.triggerDate) : event.triggerDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = rule.hashCode();
        result = 31 * result + (triggerDate != null ? triggerDate.hashCode() : 0);
        return result;
    }
}
