package cep_server.control;

import de.hsa.RfidTag;

import com.espertech.esper.client.*;

import java.util.Set;
import java.util.HashSet;

/**
 * Demonstration UpdateListener.
 *
 * This UpdateListener passes on the received Tag array.
 *
 * @author Thomas Hipp
 */
public class UsecaseAListener extends MyUpdateListener {
    /**
	 * Get updates from Esper.
	 *
	 * This method is called when the Esper rule takes effect.  It then
	 * notifies all observers about the new tags.
	 *
	 * @param newData new data.
	 * @param oldData old data.
	 */
	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {
        Set<RfidTag> tagSet = new HashSet<RfidTag>();
        System.out.println(newData[0].get("middleevent").getClass().getName());
        try {
            for (TagEvent t: (TagEvent[]) newData[0].get("middleevent"))
                for (RfidTag r: t.getTagList())
                    tagSet.add(r);
        } catch (PropertyAccessException e) {
            // no need to handle this
            return;
        } catch (Exception e) {
            return;
        }
        boolean valid = false;
        for (RfidTag r: tagSet) {
            if (r.getTagID().equals("E200 3411 B802 0110 2608 7679")) {
                valid = true;
            }
        }
        if (valid) {
            if (getPerson(newData, oldData) > 1 ||
                    getGummibaerchen(newData, oldData) != 1 ||
                    getAllFoodTags(newData, oldData) > 2) {
                setChanged();
                notifyObservers(new TagEvent(tagSet.toArray(new RfidTag[0])));
            }
        }
	}
}
