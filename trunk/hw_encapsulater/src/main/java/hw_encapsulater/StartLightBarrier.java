package hw_encapsulater;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import hw_encapsulater.model.lightbarrier.LightBarrier;


public class StartLightBarrier {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LightBarrier ls = new LightBarrier();
		
		try {
			ls.listenToLightBarriers(CommPortIdentifier.getPortIdentifier("/dev/ttyUSB0"));
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		}
	}

}


