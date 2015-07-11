package hw_encapsulater.model.events;

import com.alien.enterpriseRFID.tags.Tag;
import hw_encapsulater.model.entities.Persons;
import hw_encapsulater.model.localtestmodel.GenericTestModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simulation where we have one Person tags
 *
 * @author Felix Wagner
 *
 */

@SuppressWarnings("serial")
public class Visitor extends GenericTestModel{
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
    	Persons one = new Persons();

    	/*
    	 * Get one random Tag ID from Persons class
    	 * located in ../model/entities
    	 */

    	List<String> tags = Arrays.asList(one.getPersonB());

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
