package hw_encapsulater.model.entities;

import java.util.Random;

/**
 * A simple Class containing all Duplo Tags
 *
 * @author Felix Wagner
 *
 */


public class Snickers {
    public Snickers (){}

    /**
     * Simple Function returning all known Hanuta Tags
     *
     */

    public String[] getAllSnickers() {
        String[] myStrings = new String[] {"7637", "7638",
                                           "7639", "7640",
                                           "7643", "7644",
                                           "7646", "7647",
                                           "7648", "7649"};
		return myStrings;
	}

    /**
     * Simple Function returning only three known Hanuta Tags
     *
     */

    public String[] getSomeSnickers() {
        String[] myStrings = new String[] {"7648","7638"};
        return myStrings;
    }

    public String[] getOneSnickers() {
        Random rand = new Random();
        String[] myStrings = new String[] {"7637", "7638",
                                           "7639", "7640",
                                           "7643", "7644",
                                           "7646", "7647",
                                           "7648", "7649"};
        String[] randomElement = new String[] {myStrings[rand.nextInt(myStrings.length)]};
        return randomElement;
    }

}
