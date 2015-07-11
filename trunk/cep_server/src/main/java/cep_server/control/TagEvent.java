package cep_server.control;

import de.hsa.RfidTag;

/**
 * Event type.
 *
 * TagEvent is the event type for Esper.  It includes a Tag array.
 *
 * @author Thomas Hipp
 */
public class TagEvent {
	private RfidTag[] tagList;
	String[] ids;
	private boolean isFilled;
	
	/**
	 * Constructor.
	 *
	 * The constructor takes a Tag array which can later be obtained with
	 * getTagList().
	 *
	 * @param tagList Tag array.
	 * @see getTagList
	 */
	public TagEvent(RfidTag[] tagList) {
		this.tagList = tagList;
		this.ids = new String[tagList.length];
		for (int i = 0; i < tagList.length; i++)
			ids[i] = tagList[i].getTagID();
		isFilled = tagList.length > 0 ? true : false;
	}

	/**
	 * Get tag list.
	 *
	 * Return the Tag array.
	 *
	 * @return Tag array.
	 */
	public RfidTag[] getTagList() {
		return this.tagList;
	}

	public String[] getIds() {
		return ids;
	}

	public boolean isFilled() {
		return isFilled;
	}
}
