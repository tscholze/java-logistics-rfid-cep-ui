package hw_encapsulater.model;

import com.alien.enterpriseRFID.notify.MessageListener;
import com.alien.enterpriseRFID.tags.Tag;

import de.hsa.enums.Instruction;
import de.hsa.enums.SettingParameter;
import de.hsa.jmsconnectiontools.TopicMgr;

import java.io.Serializable;
import java.util.Observable;
import java.util.TreeMap;
import java.util.logging.Level;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * A GenericModel provides data to listening classes. The data inherits in the
 * TagList and can be obtained through calling the getTagList method.
 * 
 * @author Falk Alexander
 *
 */
public abstract class GenericModel extends Observable implements com.alien.enterpriseRFID.notify.MessageListener, javax.jms.MessageListener, Runnable, Serializable {
	
    /**
     * Returns the current TagList.
     * 
     * @return Tag[]
     */
    public abstract Tag[] getTagList();

    /**
     * Call this method to run the Model. It although runs the needed services.
     */
    public abstract void start();

    /**
     * Stops the Model and terminates the depending services.
     */
    public abstract void stop();

    /**
     * Set the state of debug messages to standard out.
     * 
     * @param stdOut
     */
    public abstract void setStdOut(boolean stdOut);

    /**
     * Set the state of simple logging.
     * 
     * @param simpleLog
     */
    public abstract void setSimpleLog(boolean simpleLog);
    
    /**
     * Sets the current state of the program.
     * 
     * @param running
     *            true, when the reading progress should start, else false.
     */
    public abstract void startStopReader(boolean running);

    /**
     * Execute the instruction that has been received over JMS (onmessage).
     * @param instruction
     * @param message
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

    protected abstract void setDefaultSettings();

	protected abstract void applysettings(ObjectMessage message);

	protected abstract void shutdown();

	protected abstract void exit();

	protected abstract void setup();

}