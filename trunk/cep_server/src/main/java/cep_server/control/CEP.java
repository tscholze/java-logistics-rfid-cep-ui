package cep_server.control;

import com.espertech.esper.client.*;
import de.hsa.RfidTag;
import de.hsa.RuleContainer;
import de.hsa.jmsconnectiontools.TopicConnector;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * CEP Layer.
 *
 * This layer uses Esper (CEP Engine) to filter tags which are received
 * from the RFID reader.
 *
 * @Author Thomas Hipp
 */
public class CEP extends Observable implements Observer, MessageListener {

	private Configuration cepConfig;
	private EPServiceProvider cep;
	private EPRuntime cepRT;
	private EPAdministrator cepAdm;
	private UpdateListener cepListener;
    private TopicConnector topicConnector;
    private SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );

    /**
	 * Constructor.
	 *
	 * The constructor sets up Esper and registers "TagEvent" as the desired
	 * event type.
	 */
    public CEP(TopicConnector topicConnector) {
        // setup Esper
        cepConfig = new Configuration();
        cepConfig.addEventType("TagEvent", TagEvent.class.getName());
		cepConfig.addEventType("SensorEvent", SensorEvent.class.getName());
        cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
        cepRT = cep.getEPRuntime();
        cepAdm = cep.getEPAdministrator();
        this.topicConnector = topicConnector;
    }


    /**
	 * Receive updates.
	 *
	 * This method is called when an Observable has new data to share.
	 *
	 * @param o Observable instance.
	 * @param arg Received data.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof InstructionListener) {
			// new rule

            RuleContainer rc = (RuleContainer) arg;
            System.out.println("Received new rule from Controller"+ rc);
			// check whether to add or remove the rule
			if (rc.isAddEvent()) {
				cepListener = loadUpdateListener(rc.getPath(), rc.getClassName(), rc.getUniqueName());
				try {
					// add unique name argument in order to identify the rule.
					cepAdm.createEPL(rc.getRule(), rc.getUniqueName()).addListener(cepListener);
				} catch (EPException e) {
					System.out.println("Error: " + e);
					return;
				}
				// get updates straight from the UpdateListener
				((Observable) cepListener).addObserver(this);
				System.out.println("Added rule: " + rc.getUniqueName());
			} else {
				try {
					cepAdm.getStatement(rc.getUniqueName()).destroy();
				} catch (NullPointerException e) {
					System.out.println("Error: " + e);
					return;
				}
				System.out.println("Removed rule: " + rc.getUniqueName());
			}
		} else if (o instanceof MyUpdateListener) {
            // receive TagEvent from Esper and notify GUI;
            try {
                ObjectMessage om = topicConnector.getTopicSession().createObjectMessage(((TagEvent)arg).getTagList());
                om.setStringProperty("uniqueName", ((MyUpdateListener) o).getUniqueName());
                om.setLongProperty("timeStamp", new Date().getTime());
                om.setStringProperty("class", o.getClass().getName());
                topicConnector.getPublisher().publish(om);
                System.out.println("sending message: " + ((MyUpdateListener) o).getUniqueName());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Got from " + o + " unknown arg: " + arg);
        }
	}

	/**
	 * Dynamic class loader.
	 *
	 * This method loads a class file during runtime, as seen on
	 * http://www.exampledepot.com/egs/java.lang/reloadclass.html.
	 * It is important that the class to load implements Esper's UpdateListener
	 * interface in order to receive events, and Observable in order for others
	 * to receive updates about tags.
	 *
	 * @param path Path to class file, e.g. classes/prototype/control
	 * @param classname Class name, e.g. prototype.control.MyUpdateListener
     * @param uniqueName the unique id or name of a event
	 */
	public UpdateListener loadUpdateListener(String path, String classname, String uniqueName) {
	// Get the directory (URL) of the reloadable class
		URL[] urls = null;
		MyUpdateListener myObj = null;
		try {
			// Convert the file object to a URL
			File dir = new File(path+File.separator);
			URL url = dir.toURL();
			urls = new URL[]{url};
		} catch (MalformedURLException e) {
			System.err.println("__ERROR__: " + e);
		}

		try {
			// Create a new class loader with the directory
			ClassLoader cl = new URLClassLoader(urls);

			// Load in the class
            //System.out.println(classname);
			Class cls = cl.loadClass(classname);

			// Create a new instance of the new class
			myObj = (MyUpdateListener) cls.newInstance();
		} catch (IllegalAccessException e) {
			System.err.println("__ERROR__: " + e);
		} catch (InstantiationException e) {
			System.err.println("__ERROR__: " + e);
		} catch (ClassNotFoundException e) {
			System.err.println("__ERROR__: " + e);
		}
        // Now we have the listener that is called when a rule triggers. Later
        // we need to know which rule it was and what is its id.
        myObj.setUniqueName(uniqueName);
		return myObj;
	}

    @Override
    public void onMessage(Message message) {
        // create new TagEvent and send to Esper
	//System.out.println("Got message: " + message);
        String type = null;
        try {
            type = message.getStringProperty("type");
            //message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        assert type != null;
        if (type.equalsIgnoreCase("RFIDTagArray")) {
            Object[] tags = null;
            try {
                tags = (Object[]) ((ObjectMessage) message).getObject();

            } catch (JMSException e) {
                e.printStackTrace();
            }

            RfidTag[] rfidTags = new RfidTag[tags.length];
            for (int i = 0; i < tags.length; i++) {
                rfidTags[i] = (RfidTag) tags[i];
            }
            cepRT.sendEvent(new TagEvent(rfidTags));
        } else if (type.equalsIgnoreCase("LightSensor")) {

            long timeStamp = 0;
            String name = null;
            Serializable status = null;
            try {
                timeStamp = message.getLongProperty("timeStamp");
                name = message.getStringProperty("sensorName");
                status = ((ObjectMessage) message).getObject();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            /*System.out.println("Got LightSensor Information: " +
                    "timeStamp: " + df.format(timeStamp) + " \n" +
                    "sensorName: " + name + " \n" +
                    "status: " + status);*/
			cepRT.sendEvent(new SensorEvent(timeStamp, name, status));
        } else {
            System.err.println("I got an unknown message type: " + type);
        }
    }
}
