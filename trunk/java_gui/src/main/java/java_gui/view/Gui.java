package java_gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.hsa.RfidTag;

import de.hsa.jmsconnectiontools.TopicMgr;

import de.hsa.enums.SettingParameter;
import de.hsa.enums.Instruction;

/**
 * View part of the MVC-Pattern.
 * Is Observer of the Model, and observes data to the control, which contain the commands.
 * Responsible for the Graphical Layout of the Tags.
 * @author manu, Valon
 *
 */
public class Gui extends Observable implements javax.jms.MessageListener, Observer{
	private JFrame frame;
	private TreeMap<String, JPanel> panels;
	private JPanel tags;
	private Gui gui;
	private SettingsGui settingsGui;
	private TreeMap<SettingParameter, String[]> settings;
	private int id;
	private String isAdmin;
	private boolean admin;
	private TopicMgr controltopic;
	private JPanel controls;
	private JButton exitBtn;
	private int width;
	private int height;
	
	/**
	 * Constructor
	 * Initializes the panels-Treemap, which visualizes the Tags.
	 */
	public Gui(TopicMgr control){
		panels = new TreeMap<String, JPanel>();
		tags = new JPanel();
		this.gui=this;
		id = initID();
		isAdmin = null;
		admin = false;
		height = 500;
		width = 800;
		
		this.controltopic = control;
		try {
			controltopic.setSubscriber(this);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initialize(width,height);
		adminConnect();
	}
	
	/**
	 * Initializes the Frame, Buttons and Panels.
	 */
	public void initialize(int width, int height)
	{
		frame = new JFrame("RFID-Visualisierung");
         
        //Add control-buttons
        controls = new JPanel();
        JButton startBtn = new JButton("Start");
        JButton stopBtn = new JButton("Stop");
        JButton setBtn = new JButton("Settings");
        JButton stopModel = new JButton("Shutdown Model");
        JButton rule1 = new JButton("Rule_1");
        JButton rule2 = new JButton("Rule_2");
        JButton rule3 = new JButton("Rule_3");
        exitBtn = new JButton("Exit");
        
        startBtn.setEnabled(admin);
        stopBtn.setEnabled(admin);
        setBtn.setEnabled(admin);
        stopModel.setEnabled(admin);
        rule1.setEnabled(true);
        rule2.setEnabled(true);
        rule3.setEnabled(true);
        exitBtn.setEnabled(true);
        
        controls.add(startBtn);
        controls.add(stopBtn);
        controls.add(setBtn);
        controls.add(stopModel);
        controls.add(rule1);
        controls.add(rule2);
        controls.add(rule3);
        controls.add(exitBtn);
        frame.add(controls, BorderLayout.SOUTH);
        
        //create button-listener for actions
        exitBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ev){
        		if(admin){
					try {
						controltopic.publish(null, Instruction.EXIT.name());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		shutDown();
        	}
        });
       
        startBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ev){
        		try {
					controltopic.publish(null, Instruction.START.name());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
        stopBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ev){
     		try {
				controltopic.publish(null, Instruction.STOP.name());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	}
        });
        setBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ev){
        		try {
					controltopic.publish(null, Instruction.SETUP.name());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}    		
        	}
        });
        
        stopModel.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ev){
        		try {
					controltopic.publish(null, Instruction.SHUTDOWN.name());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}    		
        	}
        });
        
        rule1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ev){
        		setChanged();
        		notifyObservers("rule_1");
        	}
        });
        
        rule2.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ev){
        		setChanged();
        		notifyObservers("rule_2");
        	}
        });
        
        rule3.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ev){
        		setChanged();
        		notifyObservers("rule_3");
        	}
        });

		frame.setSize(width, height);
        
        frame.add(tags, BorderLayout.CENTER);
        
        frame.setVisible(true);
        
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}
	
	public void repaintControls(){
		for (Component c : controls.getComponents()) {
			c.setEnabled(admin);
		}
		frame.repaint();
		frame.setVisible(true);
	}
	
	public int initID(){
		long nanotime = System.nanoTime();
		int id = String.valueOf(nanotime).hashCode();
		return id;		
	}
	
	public void adminConnect(){
		try {
			controltopic.publish(String.valueOf(id), "adminrequest");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double time = System.currentTimeMillis();
		while((System.currentTimeMillis() - time) < 10000){
			if(isAdmin != null){
				admin = isAdmin.equals("true");
				return;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("TimeOutException");
	}

	/**
	 * First colors all Tag-Panels red.
	 * Afterwards checks whether the received Tags are already available as Panel, and if necessary
	 * inserts them into panels TreeMap.
	 * All received Tags are painted green.
	 * @param result All received Tags in an Tag Array.
	 */
	public void updatePanels(RfidTag[] result){
		for(String key : panels.keySet()){
			panels.get(key).setBackground(Color.red);
		}
		
		for (RfidTag tag : result){
			if(!panels.containsValue(tag.getTagID().substring(19))){
				panels.put(tag.getTagID().substring(19), getTag(tag));
			}
			panels.get(tag.getTagID().substring(19)).setBackground(Color.green);
		}
	}

	/**
	 * Method is called when the data in the Model has changed.
	 * Method is updating the Tag-Panel.
	 * @param o The Observable which data has changed
	 * @param arg The Object of data that has changed
	 */
	@SuppressWarnings("unchecked")
	public void update(Observable o, Object arg) {
		if(arg instanceof RfidTag[]){
			frame.remove(tags);
			RfidTag[] result = (RfidTag[])arg;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			updatePanels(result);
			tags = getCompleteTag();
			frame.add(tags, BorderLayout.CENTER);
			tags.validate();
			tags.repaint();
			tags.setVisible(true);
		}
	}
	
	/**
	 * Method gets a Tag and creates a Panel out of it which contains the last four signs of the Key as Label.
	 * @param tag From this Tag a Panel will be created
	 * @return ret Panel from Tag that the method has created
	 */
	public JPanel getTag(RfidTag tag){
		JPanel ret = new JPanel();
		ret.setSize(40,40);
		ret.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
		ret.setBackground(Color.green);
		JLabel id = new JLabel(tag.getTagID().substring(19));
		ret.add(id);
		return ret;
	}
	
	/**
	 * All Tag-panels are put into one Panel.
	 * @return ret Big Panel containing all Tag-Panels
	 */
	public JPanel getCompleteTag(){
		JPanel ret = new JPanel();
		ret.setSize(frame.getSize().width - 2, frame.getSize().height - 60);
		ret.setLayout(new GridLayout(3, 8));
		for (String key : panels.keySet()){
			ret.add(panels.get(key));
		}
		return ret;
	}
	
	public void setSettings(TreeMap<SettingParameter, String[]> settings){
		//Es wurden noch keine Settings eingegeben
		if(settingsGui==null){
			settingsGui = new SettingsGui(settings, gui); //Neue SttingsGUI erstellen
			frame.setEnabled(false); //TagGui nicht anklickbar setzen
		}
		else {
			settingsGui.updateSettingData(settings);
		}
	}
	
	public TreeMap<SettingParameter, String[]> getSettings(){
		return this.settings;
	}
	
	public void applySettings(TreeMap<SettingParameter, String[]> settings){
		this.settings=settings;
		settingsGui=null;
		frame.setEnabled(true);
		try {
			controltopic.publish(settings, "applysettings");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cancelSetting(){
		settingsGui=null;
		frame.setEnabled(true);
	}

	public void onMessage(Message jmsMsg) {	
		String property = null;
		try {
			property = jmsMsg.getStringProperty("property").toLowerCase();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ObjectMessage message = (ObjectMessage) jmsMsg; 

		if(property.equals("adminvalidation")){
			int adminid = -1;
			try {
				adminid = Integer.parseInt((String) message.getObject());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(adminid == id){
				isAdmin = "true";
				admin = true;
				repaintControls();
			}
			else{
				isAdmin = "false";
				admin = false;
			}
		}
		else if(property.equals("modelshutdown")){
			frame.remove(controls);
			JPanel panel = new JPanel();
			JLabel label = new JLabel("Model crashed. YOU FAIL!");
			panel.add(label);
			frame.add(panel, BorderLayout.CENTER);
			controls = new JPanel();
			controls.add(exitBtn);
			frame.add(controls, BorderLayout.SOUTH);
			frame.repaint();
			frame.setVisible(true);
		}
		else if(property.equals("admindenied")){
			int adminid = -1;
			try {
				adminid = Integer.parseInt((String) message.getObject());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(adminid == id){
				isAdmin = "false";
				admin = false;
			}
		}
		else if(property.equals("exception")){
			String msg = null;
			try {
				msg = (String) message.getObject();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane pop = new JOptionPane();
			pop.showMessageDialog(frame, msg, "Error occured", JOptionPane.ERROR_MESSAGE);
		}
		else if(property.equals("adminexit")){
			adminConnect();
		}
		else if(admin){
			if(property.equals("settings")){
				TreeMap<SettingParameter, String[]> getSettings = null;
				try {
					getSettings = (TreeMap<SettingParameter, String[]>) message.getObject();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(getSettings != null){
					setSettings(getSettings);
				}
			}
		}
	}
	
    public void shutDown() {
    	try {
    		controltopic.shutDown();
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	frame.dispose();
    	System.exit(0);
    }
    
    public void controltopicPublish(Object o, String property){
    	try {
			controltopic.publish(o, property);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
