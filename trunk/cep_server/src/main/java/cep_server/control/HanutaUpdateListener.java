package cep_server.control;

import de.hsa.RfidTag;

import com.espertech.esper.client.*;

import java.util.Set;
import java.util.HashSet;

/**
 * HanutaUpdateListener.
 *
 * UpdateListener for the Hanuta use case.
 *
 * @author Thomas Hipp
 */
public class HanutaUpdateListener extends MyUpdateListener {
	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {
		Set<RfidTag> tagSet = new HashSet<RfidTag>();
		System.out.println("Event received:");
		TagEvent startTag = null;
		try {
			startTag = (TagEvent) newData[0].get("startevent");
		} catch (PropertyAccessException e) {
			// no need to handle this
		}
		if (startTag != null)
			tagSet.add(startTag.getTagList()[0]);
		try {
			for (TagEvent t: (TagEvent[]) newData[0].get("middleevent"))
				for (RfidTag r: t.getTagList())
					tagSet.add(r);
		} catch (PropertyAccessException e) {
			// no need to handle this
		}
		setChanged();
		notifyObservers(new TagEvent(tagSet.toArray(new RfidTag[0])));
	}
}
