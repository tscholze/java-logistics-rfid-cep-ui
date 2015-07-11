package hw_encapsulater.model.events;

import com.alien.enterpriseRFID.tags.Tag;
import hw_encapsulater.model.entities.Snickers;
import hw_encapsulater.model.entities.Persons;
import hw_encapsulater.model.entities.Knoppers;
import hw_encapsulater.model.localtestmodel.GenericTestModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This usecase has one Person Tag ("E200 3411 B802 0110 2608 7680")
 * And the user may carry only a max of 4 items where 2 must always be
 * the same
 *
 * @author Felix Wagner
 *
 */

@SuppressWarnings("serial")
public class UsecaseB extends GenericTestModel{
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
        Snickers snick = new Snickers();
    	Knoppers knop = new Knoppers();
    	Persons one = new Persons();

    	/*
    	 * Get three Tag ID's from Hanutas and get one Person
    	 * located in ../model/entities
    	 */

    	List<String> snickers = Arrays.asList(snick.getSomeSnickers());
    	List<String> knoppers = Arrays.asList(knop.getSomeKnoppers());
    	List<String> visitor = Arrays.asList(one.getPersonB());

    	/*
    	 * Create a new List out of the two above Not very nice
    	 */

        List<String> tags = new ArrayList<String>();
    	tags.addAll(snickers);
        tags.addAll(knoppers);
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
