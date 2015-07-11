package hw_encapsulater.model;

import java.util.Observable;

/**
 * A simple Class which implements the data that will be observed.
 *
 * @author Felix Wagner
 *
 */

public class LightSensor {
    private boolean interrupted;
    private OpenNotifier oNotify = new OpenNotifier();
    private CloseNotifier cNotify = new CloseNotifier();
    
    public LightSensor() {
    	interrupted = false;
    }

    public void open() { // interrupted
    	interrupted = true;
    	oNotify.notifyObservers(interrupted);
    	cNotify.open();
    }

    public void close() { // not interrupted
    	interrupted = false;
    	cNotify.notifyObservers(interrupted);
    	oNotify.close();
    }

    public Observable connected() {
    	return oNotify;
    }

    public Observable disconnected() {
    	return cNotify;
    }

    private class OpenNotifier extends Observable {
    	private boolean alreadyOpen = false;
	
    	@Override
        public void notifyObservers(Object arg) {
    		if (interrupted && !alreadyOpen) {
    			setChanged();
    			super.notifyObservers(arg);
    			alreadyOpen = true;
    		}
    	}

    	public void close() {
    		alreadyOpen = false;
    	}
    }

    private class CloseNotifier extends Observable {
    	private boolean alreadyClosed = false;

        @Override
    	public void notifyObservers(Object arg) {
    		if (!interrupted && !alreadyClosed) {
    			setChanged();
    			super.notifyObservers(arg);
    			alreadyClosed = true;
    		}
    	}

    	public void open() {
    		alreadyClosed = false;
    	}
    }
}
