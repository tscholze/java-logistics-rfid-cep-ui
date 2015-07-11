package hw_encapsulater.model;

import java.lang.reflect.InvocationTargetException;
import java.util.TreeMap;

import hw_encapsulator.reflectionMgr.*;

import com.alien.enterpriseRFID.reader.AlienClass1Reader;
import com.alien.enterpriseRFID.reader.AlienReaderConnectionException;
import com.alien.enterpriseRFID.reader.AlienReaderConnectionRefusedException;
import com.alien.enterpriseRFID.reader.AlienReaderException;
import com.alien.enterpriseRFID.reader.AlienReaderNotValidException;
import com.alien.enterpriseRFID.reader.AlienReaderTimeoutException;

import de.hsa.enums.*;

/**
 * Creates and initializes the AlienReader-object.
 * Offers methods to change the AlienReader behavior.
 * 
 * @author Michi, Falk
 * @version 1.1
 */
public class Reader implements GenericReader{		
	private AlienClass1Reader alienReader;
	
	/**
	 * Constructor of the Reader-Class.
	 * @param alienIp, the network address of the Alien-Reader.
	 * @throws ClassNotFoundException, the AlienClass1Reader could not be found.
	 */
	public Reader(String alienIp) throws ClassNotFoundException{
		alienReader = new AlienClass1Reader(alienIp);
	}
	
	/**
	 * For each parameter in settingParams, the value will be set on the AlienClass1Reader object. 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public void setSettings(TreeMap<SettingParameter, String[]> settings) throws AlienReaderConnectionRefusedException, AlienReaderNotValidException, AlienReaderTimeoutException, AlienReaderConnectionException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException{
		ReflectionManager<AlienClass1Reader> rm = new ReflectionManager<AlienClass1Reader>(alienReader, MethodType.SET);
		
		alienReader.open();

		for (SettingParameter settingparam : settings.keySet()) {
			System.out.println("Invoking Reader method: "+settingparam.name());
			rm.invokeMethodsFromMethodList(settingparam.name(), settings.get(settingparam));
		}
		alienReader.close();
	}
	
	/**
	 * Reconnect to the reader and turn off AutoMode and TagStreamMode
	 * @throws AlienReaderException 
	 * @throws Exception
	 */
	public void turnOffReader() throws AlienReaderException{
		  System.out.println("\nResetting Reader");
		  alienReader.open();
		  alienReader.autoModeReset();
		  alienReader.setNotifyMode(AlienClass1Reader.OFF);
		  alienReader.close();
	}
	/**
	 * Set the reader IP
	 * @param alienIp, the reader network-ip
	 */
	public void setReaderIp(String alienIp){
		this.alienReader = new AlienClass1Reader(alienIp);
	}
}
