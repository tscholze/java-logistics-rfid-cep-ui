package hw_encapsulater.model;

import java.lang.Thread;

/**
 * A simple Class which has two function which simply
 * wait.
 * @author Felix Wagner
 *
 */

public class Wait {
	public static void oneSec() {
		try {
			Thread.currentThread();
			Thread.sleep(1000);
	    } catch (InterruptedException e) {
	    	e.printStackTrace();
	    }
	}

	public static void manySec(long s) {
		try {
			Thread.currentThread();
			Thread.sleep(s * 1000);
	    } catch (InterruptedException e) {
	    	e.printStackTrace();
	    }
	}
}
