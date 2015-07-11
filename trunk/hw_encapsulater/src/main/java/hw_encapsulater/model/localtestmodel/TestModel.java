package hw_encapsulater.model.localtestmodel;

import com.alien.enterpriseRFID.tags.Tag;

/**
 * A simple model, whose tagList contains 4 entries. With a unique ID, whose
 * last number is between 0 and 3. Every 2 seconds the RenewCount is increased
 * by one.
 * 
 * @author Falk Alexander
 *
 */
public class TestModel extends GenericTestModel {

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
        int numberOfTags = 4;
        this.tagList = new Tag[numberOfTags];
        for (int i = 0; i < tagList.length; i++) {
            // TODO: improve this!
            Tag newTag = new Tag("E200 3411 B802 0110 2608 000" + i);
            newTag.setRenewCount(0);
            tagList[i] = newTag;
        }
    }


}
