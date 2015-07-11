package hw_encapsulater.model.entities;

import java.util.Random;

/**
 * A simple Class containing all Duplo Tags
 *
 * @author Felix Wagner
 *
 */


public class Gummibaerchen {
    public Gummibaerchen(){}

    /**
     * Simple Function returning all known Hanuta Tags
     *
     */

    public String[] getAllGummibaerchen() {
        String[] myStrings = new String[] {"7586", "7587",
                                           "7588", "7589",
                                           "7590", "7591",
                                           "7592", "7593",
                                           "7594"};
		return myStrings;
	}

    /**
     * Simple Function returning only three known Hanuta Tags
     *
     */

    public String[] getSomeGummibaerchen() {
        String[] myStrings = new String[] {"7586","7592"};
        return myStrings;
    }

    public String[] getOneGummibaerchen() {
        Random rand = new Random();
        String[] myStrings = new String[] {"7586", "7587",
                                           "7588", "7589",
                                           "7590", "7591",
                                           "7592", "7593",
                                           "7594"};
        String[] randomElement = new String[] {myStrings[rand.nextInt(myStrings.length)]};
        return randomElement;
    }

}
