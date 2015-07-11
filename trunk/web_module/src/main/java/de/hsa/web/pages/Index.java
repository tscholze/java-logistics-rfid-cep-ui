package de.hsa.web.pages;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.hsa.database.entities.Event;
import de.hsa.database.entities.Tag;
import de.hsa.database.services.EventService;
import de.hsa.database.services.TagService;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.alerts.AlertManager;

/**
 * Start page of application RFID-Web-Module.
 */
public class Index
{
    @Property
    @Inject
    @Symbol(SymbolConstants.TAPESTRY_VERSION)
    private String tapestryVersion;

    @Property
    private Tag tag;

    private List<Tag> tagList;
    
    private List<Tag> productList;
    
    private List<Tag> humanList;

    @Property
    private Event event;

    private boolean isHanuta;

    private TagService tagService = new TagService();

    private EventService eventService = new EventService();

    void setupRender()
    {
        setLatestEvent();
        setTagList();
        setHumanList();
        setProductList();
    }

    public Date getCurrentTime()
    {
        return new Date();
    }

    public List<Tag> getTagList()
    {
        return tagList;
    }
    
    public void setTagList()
    {
        tagList = tagService.loadByEvent(event);
    }
    
    public List<Tag> getProductList()
    {
        return productList;
    }

    public void setProductList()
    {
        productList = tagService.filterOnlyProducts(tagList);
        Collections.shuffle(productList);
    }

    public List<Tag> getHumanList()
    {
        return humanList;
    }

    public void setHumanList()
    {
        humanList = tagService.filterOnlyHumans(tagList);
    }

    public void setLatestEvent()
    {
        event = eventService.loadLatest();
    }

    public Event getLatestEvent()
    {
        return event;
    }
    
    public String getRuleName()
    {
        return event.getRule().getName();
    }
    
    public String getEventTriggerDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss @ dd.mm.yyyy");
        return sdf.format(event.getTriggerDate().getTime());
    }

    public String getProductName()
    {
        return tag.getProduct().getName();
    }

    public boolean getIsHanuta()
    {
        if (event.getRule().getName().equals("Hanuta ist boese"))
        {
            if (tag.getProduct().getName().equals("Hanuta"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }
    
    public boolean getIsRobot()
    {
        if(tag.getEpc().equals("E200 3411 B802 0110 2608 7629"))
        {
            return true;
        }

        return false;
    }
}
