package hw_encapsulater.model.alienmodel;

import javax.jms.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.hornetq.api.core.HornetQException;

import hw_encapsulator.reflectionMgr.*;

import com.alien.enterpriseRFID.notify.Message;
import com.alien.enterpriseRFID.notify.MessageListenerService;
import com.alien.enterpriseRFID.tags.Tag;

import de.hsa.RfidTag;
import de.hsa.jmsconnectiontools.*;

import de.hsa.enums.*;
import hw_encapsulater.model.*;

/**
 * Model-Part of the VMC-Design Pattern. Holds the data and observes them to the
 * GUI. When started, the Model receives the Tags over the
 * MessageListener-Service Stream from the Alien-Reader.
 * 
 * @author Manu, Michi
 * @version 4.0
 */
public class Model extends GenericModel implements javax.jms.MessageListener {

	private boolean running = false;
	private GenericReader reader;
	private MessageListenerService service;
	private Tag[] tagList = new Tag[0];
	private Log log;
	private Logger exceptionLogger;
	private boolean stdOut = false;
	private boolean simpleLog = false;
	private TreeMap<SettingParameter, String[]> settings;
	private int adminID;
	private TopicMgr controltopic;
	private boolean exit;

	/**
	 * Constructor of the Model class.
	 * @throws HornetQException 
	 */
	public Model(TopicMgr control) {
		log = new Log("log.txt");
		adminID=-1;
		this.exit = false;
		exceptionLogger = Logger.getLogger(this.getClass().getName());
		
		this.controltopic = control;
		try {
			controltopic.setSubscriber(this);
		} catch (JMSException e) {
			System.out.println(e.getMessage());
			exceptionLogger.log(Level.SEVERE, "JMS-Exception", e);
			exit = true;
		}
		
		initDefaultSettings();
		
		System.out.println("Model started.");
	}
	
	/**
	 * Instantiates the Service with the defined Service-Port.
	 * After that makes the Model MessageListener of the Service.
	 * @param port The Service Port where the Messages arrive.
	 */
	public void setServicePort(String port){
		service = new MessageListenerService(Integer.parseInt(port));
		service.setMessageListener(this);
	}

	/**
	 * A single Message has been received from a Reader.
	 * 
	 * @param message
	 *            the notification message received from the reader
	 */
	public void messageReceived(Message message) {
		if (stdOut)
			System.out.println("Stream Data Received:");
		if (running) {
			if (message.getTagCount() == 0) {
				if (stdOut)
					System.out.println("no Tags");
				tagList = new Tag[0];
			} else {
				tagList = message.getTagList();
				if (simpleLog)
					log.log(tagList);
				if (stdOut) {
					for (int i = 0; i < message.getTagCount(); i++) {
						Tag tag = message.getTag(i);
						System.out.println(tag.toLongString());
					}
				}
			}
			setChanged();
			notifyObservers(tagList);
		}
	}
	
	/**
	 * Converts the Tag[] (which is NOT serializable) to an RfidTag[], which is serializable.
	 * @param tags The Tag[] that should be converted.
	 * @return The RfidTag[] containing the data of the converted Tag[].
	 */
	public RfidTag[] castTags(Tag[] tags){
		RfidTag[] newTags  = new RfidTag[tags.length];
		for(int i = 0; i < tags.length;i++){
			newTags[i] = new RfidTag(tags[i]);
		}
		return newTags;
	}
	
	public Tag[] getTagList(){
		return tagList;
	}

	/**
	 * Makes sure that the Thread stays alive while service is streaming data or
	 * the running-flag is true.
	 * 
	 * @throws InterruptedException
	 */
	private void waitWhileReading() {
		while (service.isRunning() && running) {
			if (stdOut)
				System.out.println("running...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
				exceptionLogger.log(Level.SEVERE, "Exception in Thread.sleep occured", e);
				controltopicPublish("Thread Exception while waiting for Tags.", "exception");
			}
			if(exit){
				service.stopService();
				return;
			}
		}
	}
	
	/**
	 * The main Thread hat keeps the Model running when it is in Idle-Mode.
	 * When the Stream is started, the waitWhileReading-Method is being called.
	 * When running equals false, the waitWhileReading-Thread terminates, and 
	 * the waitIdle-Method keeps the Thread alive.
	 * @throws InterruptedException
	 */
	private void waitIdle() {
		while(!exit){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
				exceptionLogger.log(Level.SEVERE, "Exception in Thread.sleep occured", e);
				controltopicPublish("Thread Exception while waiting for tasks.", "exception");
			}
			if(service != null){
				if(service.isRunning() && running){
					waitWhileReading();
				}
			}
		}
		boolean success = shutDown();
		if(!success){
			System.out.println("Model could not be shut down successfully.");
			exceptionLogger.log(Level.SEVERE, "Model couldnt be shut down successfully");
		}
	}

	/**
	 * Sets the current state of the program.
	 * @param running True, when the reading progress should start, else false.
	 * @throws Exception 
	 */
	public void startStopReader(boolean running) {
		this.running = running;
		
		if (!running) {
			if(reader == null){
				return;
			}
			if(simpleLog){
				try {
					log.write();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			try {
				reader.turnOffReader();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			service.stopService();
		} 
		else {
				if(settings==null){
					try {
						setDefaultSettings();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					applySettings(settings);
				}
				try {
					service.startService();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	/**
	 * Call this method to make the AlienReader read Tags.
	 * @throws Exception 
	 */
	public void start() {
		this.startStopReader(true);
	}

	/**
	 * Makes the AlienReader not reading Tags.
	 * @throws Exception 
	 */
	public void stop() {
		this.startStopReader(false);
	}

	/**
	 * Returns the debug-status.
	 * @return boolean of the state
	 */
	public boolean isStdOut() {
		return stdOut;
	}

	/**
	 * Set the state of debug messages to standard out.
	 * @param stdOut
	 */
	public void setStdOut(boolean stdOut) {
		this.stdOut = stdOut;
	}

	/**
	 * Returns the state of simple logging set.
	 * @return boolean
	 */
	public boolean isSimpleLog() {
		return simpleLog;
	}

	/**
	 * Set the state of simple logging.
	 * @param simpleLog
	 */
	public void setSimpleLog(boolean simpleLog) {
		this.simpleLog = simpleLog;
	}
	
	/**
	 * Receives a Dictionary of Setting-Parameters. Iterates through the List of 
	 * Parameters, sets them in the Data-Layer and on the Reader.
	 * @param settings TreeMap containing the SettingParameter-Enum as Key and the
	 * value of this Parameter
	 * @throws Exception 
	 */
	public void applySettings(TreeMap<SettingParameter, String[]> settings){
		
		try {
			reader = new Reader(SettingParameter.SETALIENIP.getDefault()[0]+":"+SettingParameter.SETALIENIP.getDefault()[1]);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			exceptionLogger.log(Level.SEVERE, "AlienClass1Reader could not be found.", e);
			controltopicPublish("Could not instantiate Reader Object. Shutting down.", "exception");
			exit = true;
		}
		
		this.settings=settings;
		
		ReflectionManager<Model> rm = new ReflectionManager<Model>(this, MethodType.ALL);
		
		for (SettingParameter settingparam : settings.keySet()) {
			try {
				rm.invokeMethodsFromMethodList(settingparam.name(), settings.get(settingparam));
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				exceptionLogger.log(Level.SEVERE, "IllegalArgumentException (applySettings): "+settingparam.name(), e);
				controltopicPublish("Argument false for parameter: "+settingparam.name(), "exception");
			} catch (IllegalAccessException e) {
				System.out.println(e.getMessage());
				exceptionLogger.log(Level.SEVERE, "IllegalAccessException (applySettings): "+settingparam.name(), e);
				controltopicPublish("Cannot access object for parameter: "+settingparam.name(), "exception");
			} catch (InvocationTargetException e) {
				System.out.println(e.getMessage());
				exceptionLogger.log(Level.SEVERE, "InvocationTargetException (applySettings): "+settingparam.name(), e);
				controltopicPublish("Cannot invoke method for parameter: "+settingparam.name(), "exception");
			}
		}
		
		try {
			reader.setSettings(settings);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			exceptionLogger.log(Level.SEVERE, "AlienReader apply-settings Exception", e);
			controltopicPublish("A problem occured while trying to set new settings on AlienReader.", "exception");
		}
	}
	
	/**
	 * Getter for Settings
	 * @return TreeMap of Setting Parameters
	 */
	public TreeMap<SettingParameter, String[]> getSettings(){
		return settings;
	}
	
	/**
	 * Resets the setting-values to the standard values defined in the SettingParameter Enum.
	 */
	public void initDefaultSettings(){
		settings = new TreeMap<SettingParameter, String[]>();
		
		for (SettingParameter sp : SettingParameter.values()){
			settings.put(sp, sp.getDefault());
		}
	}
	
	/**
	 * Sets the Default-settings on the AlienReader, that are saved 
	 * in the SettingParameter-enum as Default.
	 */
	public void setDefaultSettings() {
		initDefaultSettings();
		applySettings(settings);
		if(stdOut)
			System.out.println("Default set");
		
		try {
			controltopic.publish(settings, "settings");
		} catch (JMSException e) {
			System.out.println(e.getMessage());
			exceptionLogger.log(Level.SEVERE, "JMS-Exception", e);
			exit = true;
		}
	}

	public void run() {
		waitIdle(); // idle-mode
		
		System.out.println("terminated");
	}
	
	/**
	 * Terminates the Model-Thread after shutting down all services and Topic-connections correctly.
	 * @return True if successfully, else false.
	 */
	public boolean shutDown(){
		running = false;
		
		if(service != null){
			service.stopService();
		}
		service = null;
		if(reader != null){
			try {
				reader.turnOffReader();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				exceptionLogger.log(Level.SEVERE, "AlienReader Exception while turning off.", e);
				return false;
			}
		}
		
		try {
			controltopic.publish(null, "modelshutdown");
		} catch (JMSException e1) {
			System.out.println(e1.getMessage());
			exceptionLogger.log(Level.SEVERE, "JMS-Exception", e1);
		}
		
		try {
			controltopic.shutDown();
		} catch (JMSException e) {
			System.out.println(e.getMessage());
			exceptionLogger.log(Level.SEVERE, "JMS-Exception", e);
			return false;
		} catch (NamingException e) {
			System.out.println(e.getMessage());
			exceptionLogger.log(Level.SEVERE, "Naming false (Topic/Queue)", e);
			return false;
		}
		return true;
	}
	
	/**
	 * Looks which Instruction fits and executes needed actions.
	 * @param instruction The Instruction-value.
	 * @param message The JMS-Message (converted into Object-Message) that might contains data, if required.
	 */
	public void doInstruction(Instruction instruction, ObjectMessage message){
		switch (instruction) {
		case START:
			start();
			break;
			
		case STOP:
			stop();
			break;
			
		case DEFAULT:
			setDefaultSettings();
			break;
			
		case SETUP:
			setup();
			break;
			
		case APPLYSETTINGS:
			applysettings(message);
			break;
			
		case EXIT:
			exit();
			break;
			
		case SHUTDOWN:
			shutdown();
			break;
			
		default:
			break;
		}
	}
	
	/**
	 * Receives the Messages send over JMS. Differentiates by the Property-Value, executes the required action, 
	 * or calls the corresponding method. The property-value is necessary, and defined as String.
	 */
	@Override
	public void onMessage(javax.jms.Message jmsMsg) {
		String property = null;
		try {
			property = jmsMsg.getStringProperty("property").toLowerCase();
		} catch (JMSException e) {
			System.out.println(e.getMessage());
			exceptionLogger.log(Level.SEVERE, "JMS-Exception", e);
			exit = true;
		}
		
		System.out.println(property);
		
		ObjectMessage message = (ObjectMessage) jmsMsg; 
		
		if(property.equals("adminrequest")){
			int id = -1;
			try {
				id = Integer.parseInt((String)message.getObject());
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
				exceptionLogger.log(Level.SEVERE, "False ID.", e);
				controltopicPublish("Gui ID not in conform Format. Restart Gui.", "exception");
			} catch (JMSException e) {
				System.out.println(e.getMessage());
				exceptionLogger.log(Level.SEVERE, "JMS-Exception", e);
				exit = true;
			}
			
			if(this.adminID == -1){
				this.adminID = id;
				try {
					controltopic.publish(String.valueOf(id), "adminvalidation");
				} catch (JMSException e) {
					System.out.println(e.getMessage());
					exceptionLogger.log(Level.SEVERE, "JMS-Exception", e);
					exit = true;
				}
			}
			else {
				try {
					controltopic.publish(String.valueOf(id), "admindenied");
				} catch (JMSException e) {
					System.out.println(e.getMessage());
					exceptionLogger.log(Level.SEVERE, "JMS-Exception", e);
					exit = true;
				}
			}
		}
		else {
			Instruction instruction = null;
				instruction = Instruction.valueOf(property.toUpperCase());
			
			if(instruction != null)
				doInstruction(instruction, message);
		}
	}
	
	public void controltopicPublish(Object o, String property){
		try {
			controltopic.publish(o, property);
		} catch (JMSException e) {
			System.out.println(e.getMessage());
			exceptionLogger.log(Level.SEVERE, "JMS-Exception", e);
			exit = true;
		}
	}

	@Override
	protected void applysettings(ObjectMessage message) {
		TreeMap<SettingParameter, String[]> s = null;
		try {
			s = (TreeMap<SettingParameter, String[]>) message.getObject();
		} catch (JMSException e1) {
			System.out.println(e1.getMessage());
			exceptionLogger.log(Level.SEVERE, "JMS-Exception", e1);
			exit = true;
		}
		if(s != null)
			applySettings(s);
	}

	@Override
	protected void shutdown() {
		exit = true;
	}

	@Override
	protected void exit() {
		try {
			controltopic.publish(null, "adminexit");
			adminID = -1;
		} catch (JMSException e) {
			System.out.println(e.getMessage());
			exceptionLogger.log(Level.SEVERE, "JMS-Exception", e);
			exit = true;
		}
	}

	@Override
	protected void setup() {
		try {
			controltopic.publish(getSettings(), "settings");
		} catch (JMSException e) {
			System.out.println(e.getMessage());
			exceptionLogger.log(Level.SEVERE, "JMS-Exception", e);
			exit = true;
		}
	}
}
