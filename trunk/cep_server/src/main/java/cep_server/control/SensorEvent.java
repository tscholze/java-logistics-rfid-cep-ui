package cep_server.control;

import de.hsa.RfidTag;

import java.io.Serializable;

/**
 * SensorEvent.
 *
 * This event is used for the light barrier.
 *
 * @author Thomas Hipp
 */
public class SensorEvent {
	private long timeStamp;
	private String name;
	private boolean isTriggered;

	public SensorEvent(long timeStamp, String name, Serializable status) {
		this.timeStamp = timeStamp;
		this.name = name;
		this.isTriggered = (Boolean) status;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public String getName() {
		return name;
	}

	public boolean isTriggered() {
		return isTriggered;
	}
}
