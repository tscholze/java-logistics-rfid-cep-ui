package hw_encapsulater.model.localtestmodel;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;

import com.alien.enterpriseRFID.notify.Message;
import com.alien.enterpriseRFID.tags.Tag;

import de.hsa.enums.Instruction;
import de.hsa.jmsconnectiontools.TopicMgr;

import hw_encapsulater.model.GenericModel;

public class FakeTestModel extends GenericModel {
	private boolean exit;
	private int sleepTime;
	private int maxTagCount;
	private boolean sendTags;
	private String tagBody;
	private TopicMgr controltopic;
	private int adminID;
	
	public FakeTestModel(int sleepTimeMilliSeconds, int maxTagCount, TopicMgr controltopic){
		this.exit = false;
		this.sleepTime = sleepTimeMilliSeconds;
		this.maxTagCount = maxTagCount;
		this.sendTags = false;
		this.tagBody = "E200 3411 B802 0110 2608 00";
		this.adminID = -1;
		
		this.controltopic = controltopic;
		try {
			this.controltopic.setSubscriber(this);
		} catch (JMSException e) {
			exit = true;
		}
	}
	
	@Override
	public void messageReceived(Message arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(javax.jms.Message jmsMsg) {
		String property = null;
		try {
			property = jmsMsg.getStringProperty("property").toLowerCase();
		} catch (JMSException e) {
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
				controltopicPublish("Gui ID not in conform Format. Restart Gui.", "exception");
			} catch (JMSException e) {
				System.out.println(e.getMessage());
				exit = true;
			}
			
			if(this.adminID == -1){
				this.adminID = id;
				controltopicPublish(String.valueOf(id), "adminvalidation");
			}
			else {
				controltopicPublish(String.valueOf(id), "adminvalidation");
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
			exit = true;
		}
	}

	@Override
	public void run() {
		Tag[] tags;
		int i = 0;
		boolean up = true;
		while(!exit){
			if(sendTags){
				tags = new Tag[i];
				for(int a = 0; a < i; a++){
					String tagID = tagBody;
					if(a>=10)
						tagID += 0;
					tagID += a;
					tags[a] = new Tag(tagID);
				}
				System.out.println(tags.toString());
				
				setChanged();
				notifyObservers(tags);
				clearChanged();
				
				if(up){
					if(i == maxTagCount - 1){
						up = false;
						i--;
					}
					else
						i++;
				}
				else{
					if(i == 0){
						up = true;
						i++;
					}
					else
						i--;
				}
			}
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.err.println("You fail!");
			}
		}
	}

	@Override
	public Tag[] getTagList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		this.sendTags=true;
	}

	@Override
	public void stop() {
		this.sendTags=false;
	}

	@Override
	public void setStdOut(boolean stdOut) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSimpleLog(boolean simpleLog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startStopReader(boolean running) {
		this.sendTags = running;
	}

	@Override
	protected void setDefaultSettings() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void applysettings(ObjectMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void shutdown() {
		try {
			controltopic.shutDown();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exit = true;
	}

	@Override
	protected void exit() {
		this.adminID = -1;
	}

	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		
	}

}
