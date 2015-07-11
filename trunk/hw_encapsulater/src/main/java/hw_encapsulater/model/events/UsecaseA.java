package hw_encapsulater.model.events;

import com.alien.enterpriseRFID.tags.Tag;
import hw_encapsulater.model.entities.Hanutas;
import hw_encapsulater.model.entities.Persons;
import hw_encapsulater.model.entities.Gummibaerchen;
import hw_encapsulater.model.localtestmodel.GenericTestModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This usecase has one Person Tag ("E200 3411 B802 0110 2608 7679")
 * And the user may carry only two items where one must be a Gummibaerchen
 *
 * @author Felix Wagner
 *
 */

@SuppressWarnings("serial")
public class UsecaseA extends GenericTestModel{
	@Override
    public long notifyFrequency() {
        return 2000;
    }

    @Override
    public void modifyTags() {
        for (Tag tag : tagList) {
            tag.setRenewCount(tag.getRenewCount() + 1);
            System.out.println(tag.toLongString());
        }
    }
    @Override

    public void initializeTagList() {
        Gummibaerchen gum = new Gummibaerchen();
    	Hanutas han = new Hanutas();
    	Persons one = new Persons();

    	/*
    	 * Get three Tag ID's from Hanutas and get one Person
    	 * located in ../model/entities
    	 */

    	List<String> gummibaerchen = Arrays.asList(gum.getOneGummibaerchen());
    	List<String> hanutas = Arrays.asList(han.getOneHanuta());
    	List<String> visitor = Arrays.asList(one.getPersonA());

    	/*
    	 * Create a new List out of the two above Not very nice
    	 */

        List<String> tags = new ArrayList<String>();
    	tags.addAll(hanutas);
        tags.addAll(gummibaerchen);
    	tags.addAll(visitor);

    	/*
    	 * Set the tagList length
    	 *
    	 */

    	this.tagList = new Tag[tags.size()];

    	/*
    	 * Go through the Tags and create them
    	 */

        for (int i = 0; i < tags.size(); i++) {
        	Tag newTag = new Tag("E200 3411 B802 0110 2608 " + tags.get(i));
    		newTag.setRenewCount(0);
    		tagList[i] = newTag;
        }
    }
}
