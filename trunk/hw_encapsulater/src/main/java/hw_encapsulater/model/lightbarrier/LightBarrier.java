package hw_encapsulater.model.lightbarrier;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.util.TooManyListenersException;

public class LightBarrier {
	
	public void listenToLightBarriers(CommPortIdentifier portID)
	{
		try {
			SerialPort p = (SerialPort) portID.open("Demo", 5000);
			p.setDTR(true);
			p.setRTS(false);

			p.notifyOnCarrierDetect(true);			
			p.notifyOnDSR(true);
			LightBarrierSPListener listener = new LightBarrierSPListener("/queue/HwEventQueue", "/topic/HwWatcherTopic");
				
			p.addEventListener(listener);
			while(true)
			{
				Thread.sleep(10);
			}
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}


