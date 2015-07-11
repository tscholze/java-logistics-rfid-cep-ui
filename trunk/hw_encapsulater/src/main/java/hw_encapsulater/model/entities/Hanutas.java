package hw_encapsulater.model.entities;

import java.util.Random;

/**
 * A simple Class containing all Hanuta Tags
 *
 * @author Felix Wagner
 *
 */


public class Hanutas {
    public Hanutas(){}

    /**
     * Simple Function returning all known Hanuta Tags
     *
     */

    public String[] getAllHanutas() {
        String[] myStrings = new String[] {"7598", "7606",
                                           "7608", "7609",
                                           "7611", "7612",
                                           "7613", "7614",
                                           "7615"};
		return myStrings;
	}

    /**
     * Simple Function returning only three known Hanuta Tags
     *
     */

    public String[] getSomeHanutas() {
        String[] myStrings = new String[] {"7608","7611"};
        return myStrings;
    }

    public String[] getOneHanuta() {
        Random rand = new Random();
        String[] myStrings = new String[] {"7598", "7606",
                                           "7608", "7609",
                                           "7611", "7612",
                                           "7613", "7614",
                                           "7615"};
        String[] randomElement = new String[] {myStrings[rand.nextInt(myStrings.length)]};
        return randomElement;
    }

}
