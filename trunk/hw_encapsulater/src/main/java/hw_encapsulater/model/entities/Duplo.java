package hw_encapsulater.model.entities;

import java.util.Random;

/**
 * A simple Class containing all Duplo Tags
 *
 * @author Felix Wagner
 *
 */


public class Duplo {
    public Duplo(){}

    /**
     * Simple Function returning all known Hanuta Tags
     *
     */

    public String[] getDuplos() {
        String[] myStrings = new String[] {"7629", "7630",
                                           "7631", "7632",
                                           "7633", "7634",
                                           "7635"};
		return myStrings;
	}

    /**
     * Simple Function returning only three known Hanuta Tags
     *
     */

    public String[] getSomeDuplos() {
        String[] myStrings = new String[] {"7629","7631"};
        return myStrings;
    }

    public String[] getDuplo() {
        Random rand = new Random();
        String[] myStrings = new String[] {"7629", "7630",
                                           "7631", "7632",
                                           "7633", "7634",
                                           "7635"};
        String[] randomElement = new String[] {myStrings[rand.nextInt(myStrings.length)]};
        return randomElement;
    }

}
