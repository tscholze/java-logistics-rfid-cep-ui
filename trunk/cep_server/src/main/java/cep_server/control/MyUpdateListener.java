package cep_server.control;

import com.espertech.esper.client.*;
import de.hsa.RfidTag;

import java.util.Collections;
import java.util.Observable;
import java.util.Set;
import java.util.HashSet;

/**
 * MyUpdateListener.
 *
 * Abstract class for each UpdateListener.
 *
 * @author Thomas Hipp
 */
public abstract class MyUpdateListener extends Observable implements UpdateListener {
    /**
     * This class needs the uniqueName of the triggered event to connect the message (that is sent to the gui) with the
     * rule.
     */
    private String uniqueName;

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

	protected int getPerson(EventBean[] newData, EventBean[] oldData) {
        System.out.println("call getPerson");
		int count = 0;
        Set<String> tagSet = new HashSet<String>();
        try {
            for (RfidTag r: ((TagEvent) newData[0].get("startevent")).getTagList()) {
                Collections.addAll(tagSet, r.getTagID());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            for (TagEvent t: (TagEvent[]) newData[0].get("middleevent")) {
                Collections.addAll(tagSet, t.getIds());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
        for (String s: tagSet) {
			if (s.equals("E200 3411 B802 0110 2608 7679") ||
					s.equals("E200 3411 B802 0110 2608 7680") ||
					s.equals("E200 3411 B802 0110 2608 7681"))
				count++;
		}
		return count;
	}

	protected int getMars(EventBean[] newData, EventBean[] oldData) {
		int count = 0;
        Set<String> tagSet = new HashSet<String>();
        try {
            for (RfidTag r: ((TagEvent) newData[0].get("startevent")).getTagList()) {
                Collections.addAll(tagSet, r.getTagID());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            for (TagEvent t: (TagEvent[]) newData[0].get("middleevent")) {
                Collections.addAll(tagSet, t.getIds());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
		for (String s: tagSet) {
			if (s.equals("E200 3411 B802 0110 2608 7650") ||
					s.equals("E200 3411 B802 0110 2608 7651") ||
					s.equals("E200 3411 B802 0110 2608 7652") ||
					s.equals("E200 3411 B802 0110 2608 7653") ||
					s.equals("E200 3411 B802 0110 2608 7654") ||
					s.equals("E200 3411 B802 0110 2608 7655") ||
					s.equals("E200 3411 B802 0110 2608 7656") ||
					s.equals("E200 3411 B802 0110 2608 7659") ||
					s.equals("E200 3411 B802 0110 2608 7661") ||
					s.equals("E200 3411 B802 0110 2608 7662") ||
					s.equals("E200 3411 B802 0110 2608 7663"))
				count++;
		}
		return count;
	}

	protected int getSnickers(EventBean[] newData, EventBean[] oldData) {
		int count = 0;
        Set<String> tagSet = new HashSet<String>();
        try {
            for (RfidTag r: ((TagEvent) newData[0].get("startevent")).getTagList()) {
                Collections.addAll(tagSet, r.getTagID());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            for (TagEvent t: (TagEvent[]) newData[0].get("middleevent")) {
                Collections.addAll(tagSet, t.getIds());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
		for (String s: tagSet) {
			if (s.equals("E200 3411 B802 0110 2608 7637") ||
					s.equals("E200 3411 B802 0110 2608 7638") ||
					s.equals("E200 3411 B802 0110 2608 7639") ||
					s.equals("E200 3411 B802 0110 2608 7640") ||
					s.equals("E200 3411 B802 0110 2608 7643") ||
					s.equals("E200 3411 B802 0110 2608 7644") ||
					s.equals("E200 3411 B802 0110 2608 7646") ||
					s.equals("E200 3411 B802 0110 2608 7647") ||
					s.equals("E200 3411 B802 0110 2608 7648") ||
					s.equals("E200 3411 B802 0110 2608 7649"))
				count++;
		}
		return count;
	}

	protected int getBonbon(EventBean[] newData, EventBean[] oldData) {
		int count = 0;
        Set<String> tagSet = new HashSet<String>();
        try {
            for (RfidTag r: ((TagEvent) newData[0].get("startevent")).getTagList()) {
                Collections.addAll(tagSet, r.getTagID());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            for (TagEvent t: (TagEvent[]) newData[0].get("middleevent")) {
                Collections.addAll(tagSet, t.getIds());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
		for (String s: tagSet) {
			if (s.equals("E200 3411 B802 0110 2608 7666") ||
					s.equals("E200 3411 B802 0110 2608 7667") ||
					s.equals("E200 3411 B802 0110 2608 7672") ||
					s.equals("E200 3411 B802 0110 2608 7675") ||
					s.equals("E200 3411 B802 0110 2608 7676") ||
					s.equals("E200 3411 B802 0110 2608 7677") ||
					s.equals("E200 3411 B802 0110 2608 7678"))
				count++;
		}
		return count;
	}

	protected int getHanuta(EventBean[] newData, EventBean[] oldData) {
		int count = 0;
        Set<String> tagSet = new HashSet<String>();
        try {
            for (RfidTag r: ((TagEvent) newData[0].get("startevent")).getTagList()) {
                Collections.addAll(tagSet, r.getTagID());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            for (TagEvent t: (TagEvent[]) newData[0].get("middleevent")) {
                Collections.addAll(tagSet, t.getIds());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
		for (String s: tagSet) {
			if (s.equals("E200 3411 B802 0110 2608 7598") ||
					s.equals("E200 3411 B802 0110 2608 7606") ||
					s.equals("E200 3411 B802 0110 2608 7608") ||
					s.equals("E200 3411 B802 0110 2608 7609") ||
					s.equals("E200 3411 B802 0110 2608 7611") ||
					s.equals("E200 3411 B802 0110 2608 7612") ||
					s.equals("E200 3411 B802 0110 2608 7613") ||
					s.equals("E200 3411 B802 0110 2608 7614") ||
					s.equals("E200 3411 B802 0110 2608 7615"))
				count++;
		}
		return count;
	}

	protected int getKnoppers(EventBean[] newData, EventBean[] oldData) {
		int count = 0;
        Set<String> tagSet = new HashSet<String>();
        try {
            for (RfidTag r: ((TagEvent) newData[0].get("startevent")).getTagList()) {
                Collections.addAll(tagSet, r.getTagID());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            for (TagEvent t: (TagEvent[]) newData[0].get("middleevent")) {
                Collections.addAll(tagSet, t.getIds());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
		for (String s: tagSet) {
			if (s.equals("E200 3411 B802 0110 2608 7617") ||
					s.equals("E200 3411 B802 0110 2608 7618") ||
					s.equals("E200 3411 B802 0110 2608 7619") ||
					s.equals("E200 3411 B802 0110 2608 7620") ||
					s.equals("E200 3411 B802 0110 2608 7621") ||
					s.equals("E200 3411 B802 0110 2608 7624") ||
					s.equals("E200 3411 B802 0110 2608 7626") ||
					s.equals("E200 3411 B802 0110 2608 7627") ||
					s.equals("E200 3411 B802 0110 2608 7628"))
				count++;
		}
		return count;
	}

	protected int getDuplo(EventBean[] newData, EventBean[] oldData) {
		int count = 0;
        Set<String> tagSet = new HashSet<String>();
        try {
            for (RfidTag r: ((TagEvent) newData[0].get("startevent")).getTagList()) {
                Collections.addAll(tagSet, r.getTagID());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            for (TagEvent t: (TagEvent[]) newData[0].get("middleevent")) {
                Collections.addAll(tagSet, t.getIds());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
		for (String s: tagSet) {
			if (s.equals("E200 3411 B802 0110 2608 7629") ||
					s.equals("E200 3411 B802 0110 2608 7630") ||
					s.equals("E200 3411 B802 0110 2608 7631") ||
					s.equals("E200 3411 B802 0110 2608 7632") ||
					s.equals("E200 3411 B802 0110 2608 7633") ||
					s.equals("E200 3411 B802 0110 2608 7634") ||
					s.equals("E200 3411 B802 0110 2608 7635"))
				count++;
		}
		return count;
	}

	protected int getGummibaerchen(EventBean[] newData, EventBean[] oldData) {
        System.out.println("Gummi start");
		int count = 0;
        Set<String> tagSet = new HashSet<String>();
        try {
            for (RfidTag r: ((TagEvent) newData[0].get("startevent")).getTagList()) {
                Collections.addAll(tagSet, r.getTagID());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
        try {
            for (TagEvent t: (TagEvent[]) newData[0].get("middleevent")) {
                Collections.addAll(tagSet, t.getIds());
            }
        } catch (PropertyAccessException e) {
            System.out.println(e.getMessage());
        }
		for (String s: tagSet) {
			if (s.equals("E200 3411 B802 0110 2608 7586") ||
					s.equals("E200 3411 B802 0110 2608 7587") ||
					s.equals("E200 3411 B802 0110 2608 7588") ||
					s.equals("E200 3411 B802 0110 2608 7589") ||
					s.equals("E200 3411 B802 0110 2608 7590") ||
					s.equals("E200 3411 B802 0110 2608 7591") ||
					s.equals("E200 3411 B802 0110 2608 7592") ||
					s.equals("E200 3411 B802 0110 2608 7593") ||
					s.equals("E200 3411 B802 0110 2608 7594"))
				count++;
		}
		return count;
	}

	protected int getAllFoodTags(EventBean[] newData, EventBean[] oldData) {
		int count = 0;
		count += getMars(newData, oldData);
		count += getSnickers(newData, oldData);
		count += getBonbon(newData, oldData);
		count += getHanuta(newData, oldData);
		count += getKnoppers(newData, oldData);
		count += getDuplo(newData, oldData);
		count += getGummibaerchen(newData, oldData);
		return count;
	}

    /**
	 * Get updates from Esper.
	 *
	 * This method is called when the Esper rule takes effect.  It then
	 * notifies all observers about the new tags.
	 *
	 * @param newData new data.
	 * @param oldData old data.
	 */
	public void update(EventBean[] newData, EventBean[] oldData) {}
}
