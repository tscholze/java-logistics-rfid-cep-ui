package de.hsa.database.services;

import de.hsa.database.daos.BaseDao;
import de.hsa.database.daos.TagDao;
import de.hsa.database.entities.Event;
import de.hsa.database.entities.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 1:22 PM
 */
public class TagService
        extends BaseDao<Tag>
{
    private TagDao dao;

    public TagService()
    {
        super(Tag.class);
        dao = new TagDao();
    }

    public Tag insert(Tag newTag)
    {
        return dao.insert(newTag);
    }

    public List<Tag> listAll()
    {
        return dao.listAll();
    }

    public List<Tag> loadByEpc(String epc)
    {
        return dao.loadByEpc(epc);
    }


    public Tag update(Tag tag)
    {
        return dao.update(tag);
    }

    public void dumpTagList()
    {
        System.out.println("--- Dump Tag List ---");
        for (Tag tag : listAll())
        {
            System.out.println("ID: " + tag.toString());
            System.out.println("gehoert zu Produkt: " + tag.getProduct().displayName());
            System.out.println("triggered von Event: " + tag.getEvent().displayName());
            System.out.println("------------------");
        }
    }

    public void dumpTagByEpc(String epc)
    {
        dumpTagList(loadByEpc(epc));
    }

    public void dumpTagList(List<Tag> tagList)
    {
        for (Tag tag : tagList)
        {
            System.out.println(tag.displayName());
        }
    }

    public List<Tag> loadByEvent(Event event)
    {
        return dao.loadByEvent(event);
    }
    
    public List<Tag> filterOnlyHumans(List<Tag> tagList)
    {
        List<Tag> humans = new ArrayList<Tag>();
        
        for (Tag tag : tagList)
        {
            if(tag.getProduct().getName().equals("Besucher 1") || tag.getProduct().getName().equals("Besucher 2") || tag.getProduct().getName().equals("Besucher 3"))
            {
                humans.add(tag);
            }
        }
        return humans;
    }
    
    public List<Tag> filterOnlyProducts(List<Tag> tagList)
    {
        tagList.removeAll(filterOnlyHumans(tagList));
        return  tagList;
    }
}
