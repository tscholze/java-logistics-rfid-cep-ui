package hw_encapsulater.model.entities;

/**
 * A simple Class containing all our defined human targets
 *
 * @author Felix Wagner
 *
 */

public class Persons {
        public Persons(){}

        public String[] getPersons() {
            String[] myStrings = new String[] {"7679", "7680", "7681"};
            return myStrings;
        }

        public String[] getPersonA() {
            String[] myStrings = new String[] {"7679"};
            return myStrings;
        }

        public String[] getPersonB() {
            String[] myStrings = new String[] {"7680"};
            return myStrings;
        }

        public String[] getPersonC() {
            String[] myStrings = new String[] {"7681"};
            return myStrings;
        }

}
