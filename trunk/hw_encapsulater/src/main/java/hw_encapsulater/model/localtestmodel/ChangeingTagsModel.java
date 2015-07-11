package hw_encapsulater.model.localtestmodel;

import com.alien.enterpriseRFID.tags.Tag;
import de.hsa.jmsconnectiontools.TopicMgr;

import javax.jms.MessageListener;

/**
 * Sends up to 6 Tags with unique IDs every 3 seconds. It starts with 0 Tags
 * and every time one will be added. If the maximum of Tags is reached, the
 * next time no Tag will be send. And it starts again at the beginning.
 * 
 * @author Falk Alexander
 *
 */
public class ChangeingTagsModel extends GenericTestModel implements MessageListener {
    
    private int roundModifier = 0;
    private int numberOfTags = 6;
    private Tag[] completeTagList = new Tag[numberOfTags];
    private TopicMgr controltopic;
    
    public ChangeingTagsModel(TopicMgr control) {
    	super();
    	
        this.initializeTagList(); //to Davy Jones with you Java, arrr!
    }

    @Override
    public long notifyFrequency() {
        return 3000; //about 3s
    }

    @Override
    public void modifyTags() {
        Tag[] newTagAr = new Tag[roundModifier];
        for (int i = 0; i < newTagAr.length; i++) {
            newTagAr[i] = completeTagList[i];
        }
        
        roundModifier++;
        if (roundModifier > numberOfTags)
            roundModifier = 0;
        this.tagList = newTagAr;
    }

    @Override
    public void initializeTagList() {
        for (int i = 0; i < this.numberOfTags; i++) {
            this.completeTagList[i] = new Tag("E200 3411 B802 0110 2608 000" + i);
        }
        this.tagList = this.completeTagList;
    }
}
