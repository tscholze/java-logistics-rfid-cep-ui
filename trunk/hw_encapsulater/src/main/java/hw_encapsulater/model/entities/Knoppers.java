package hw_encapsulater.model.entities;

import java.util.Random;


/**
 * A simple Class containing all Knopper Tags
 *
 * @author Felix Wagner
 *
 */

public class Knoppers {
	public Knoppers(){}

	public String[] getAllKnoppers() {
		String[] myStrings = new String[] {"7617", "7618", "7619",
                                           "7620", "7621", "7624",
                                           "7626", "7627", "7628"};
		return myStrings;
	}

    public String[] getSomeKnoppers() {
        String[] myStrings = new String[] {"7617", "7628"};
        return myStrings;
    }

    public String[] getOneKnoppers() {
        Random rand = new Random();
        String[] myStrings = new String[] {"7617", "7618", "7619",
                                           "7620", "7621", "7624",
                                           "7626", "7627", "7628"};
        String[] randomElement = new String[] {myStrings[rand.nextInt(myStrings.length)]};
        return randomElement;
    }

}
