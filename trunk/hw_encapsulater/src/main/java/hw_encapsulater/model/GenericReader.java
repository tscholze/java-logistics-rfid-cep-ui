package hw_encapsulater.model;

import java.util.TreeMap;

import de.hsa.enums.SettingParameter;

public interface GenericReader {

	/**
	 * Sets the choosen settings to the reader.
	 * @param settings
	 */
	public abstract void setSettings(TreeMap<SettingParameter, String[]> settings) throws Exception;
	
	/**
	 * Stops streaming the tags.
	 */
	public abstract void turnOffReader() throws Exception;
	
	/**
	 * Sets the Network-address of the alienReader-Object.
	 * @param alienIp IP of the RFID-Reader
	 */
	public abstract void setReaderIp(String alienIp);
}
