package java_gui.engine;

import de.hsa.RuleContainer;
import java_gui.view.Gui;

import javax.jms.JMSException;
import java.util.Observable;
import java.util.Observer;

/**
 * Control layer.
 *
 * The controller handles user input from the GUI (new rules and commands) and
 * communicates with CEP.  It also sets up the observers.
 *
 * @author Thomas Hipp
 */
public class ButtonObserver implements Observer {
	
	/**
	 * Constructor.
	 *
	 * The constructor sets up the observers.
	 *
	 * @param view A Gui instance.
	 * @param producer A JMS producer to a queue. The queue is needed to send
     *                 messages to the cep server.
     * @param session The JSM session the producer belongs to. This is needed
     *                to create new JMS ObjectMessages that will be send to the
     *                cep server.
	 */
    ListenerManager listenerManager;
    Gui gui;

    public ButtonObserver(Gui gui, ListenerManager listenerManager) {
        // set the observers
        gui.addObserver(this);
        this.listenerManager = listenerManager;
        this.gui = gui;
    }

    /**
	 * Receive updates.
	 *
	 * Receive instructions and new rules from the GUI.  If a new rule is
	 * received, the CEP layer will be notified which will then create a new
	 * rule and UpdateListener.
	 *
	 * @param o Observable instance.
	 * @param arg Received data.
	 */
	@Override
	public void update(Observable o, Object arg) {
		// double check
		if (o instanceof Gui) {
			if (arg instanceof String) {
				String rule = (String) arg;
				
				if(rule.equals("rule_1")){
					rule_4();
				}
				else if(rule.equals("rule_2")){
					rule_2();
				}
				else if(rule.equals("rule_3")){
					rule_3();
				}
				else if(rule.equals("rule_4")){
					rule_4();
				}
			}
		}
	}
	
	public void rule_1(){
        RuleContainer rc = new RuleContainer("all tags", "select * from TagEvent", 
        		"classes/cep_server/control", "cep_server.control.MyUpdateListenerImpl");
        
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}
	
	public void rule_2(){
	       RuleContainer rc = new RuleContainer("one tag",
					"select * from TagEvent where " +
					"'E200 3411 B802 0110 2608 0003' in (ids)",
					"classes/cep_server/control",
					"cep_server.control.MyUpdateListenerImpl");
	       
	       try {
	            listenerManager.addRule(rc, gui);
	        } catch (JMSException e) {
	            e.printStackTrace();
	        }
	}
	
	public void rule_3(){
        RuleContainer rc = new RuleContainer("two tags",
				"select * from TagEvent where " +
				"'E200 3411 B802 0110 2608 0004' in (ids) and " +
				"'E200 3411 B802 0110 2608 0005' in (ids)",
				"classes/cep_server/control",
				"cep_server.control.MyUpdateListenerImpl");
        
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

	public void rule_4() {
        RuleContainer rc = new RuleContainer("hanuta",
				"select * from pattern [every " +
				"SensorEvent(triggered, name='A') -> " +
				"SensorEvent(not triggered, name='A') -> " +
				"middleevent=TagEvent until " + 
				"SensorEvent(triggered, name='B') -> " +
				"SensorEvent(not triggered, name='B')]",
				"classes/cep_server/control",
				"cep_server.control.HanutaUpdateListener");
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

	public void rule_5() {
		/*
		 * This rule will be triggered when ONE specific tag is NOT
		 * found.
		 * Also there have to be tags found within 1 second.
		 */
        RuleContainer rc = new RuleContainer("stealonehanuta",
				"select * from pattern [every " +
				"SensorEvent(triggered, name='A') -> " +
				"SensorEvent(not triggered, name='A') -> " +
				"startevent=TagEvent(filled) where timer:within(1 sec) ->" +
				"middleevent=TagEvent('E200 3411 B802 0110 2608 0006' not in (ids)) until " + 
				"SensorEvent(triggered, name='B') -> " +
				"SensorEvent(not triggered, name='B')]",
				"classes/cep_server/control",
				"cep_server.control.HanutaUpdateListener");
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

	public void rule_6() {
		/*
		 * This rule will be triggered when something or someone enters the
		 * scan area and no tags are presented within 1 second.
		 */
        RuleContainer rc = new RuleContainer("notagsonenter",
				"select * from pattern [every " +
				"SensorEvent(triggered, name='A') -> " +
				"SensorEvent(not triggered, name='A') -> " +
				"startevent=TagEvent(not filled) where timer:within(1 sec)]",
				"classes/cep_server/control",
				"cep_server.control.HanutaUpdateListener");
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

	public void rule_7() {
		/*
		 * This rule will be triggered when one enters and leaves the scan
		 * area without any tags.
		 */
        RuleContainer rc = new RuleContainer("notagspassthrough",
				"select * from pattern [every " +
				"SensorEvent(triggered, name='A') -> " +
				"SensorEvent(not triggered, name='A') -> " +
				"startevent=TagEvent(not filled) until " +
				"SensorEvent(triggered, name='B') -> " +
				"SensorEvent(not triggered, name='B')]",
				"classes/cep_server/control",
				"cep_server.control.HanutaUpdateListener");
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

	public void rule_8() {
		/*
		 * This rule will be triggered when TWO specific tags are NOT
		 * found.
		 * Also there have to be tags found within 1 second.
		 */
        RuleContainer rc = new RuleContainer("stealtwohanutas",
				"select * from pattern [every " +
				"SensorEvent(triggered, name='A') -> " +
				"SensorEvent(not triggered, name='A') -> " +
				"startevent=TagEvent(filled) where timer:within(1 sec) ->" +
				"middleevent=TagEvent('E200 3411 B802 0110 2608 0006' not in (ids), " +
				"'E200 3411 B802 0110 2608 0007' not in (ids)) until " + 
				"SensorEvent(triggered, name='B') -> " +
				"SensorEvent(not triggered, name='B')]",
				"classes/cep_server/control",
				"cep_server.control.HanutaUpdateListener");
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

	public void rule_9() {
        RuleContainer rc = new RuleContainer("stream",
				"select * from pattern [every " +
				"SensorEvent(triggered, name='A') -> " +
				"SensorEvent(not triggered, name='A') -> " +
				"startevent=TagEvent(filled) where timer:within(1 sec) ->" +
				"middleevent=TagEvent('E200 3411 B802 0110 2608 0006' not in (ids), " +
				"'E200 3411 B802 0110 2608 0007' not in (ids)) until " + 
				"SensorEvent(triggered, name='B') -> " +
				"SensorEvent(not triggered, name='B')]",
				"classes/cep_server/control",
				"cep_server.control.HanutaUpdateListener");
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}
	
	public void rule_10() {
		String gummibaerchenStream = 
			"insert into GummibaerchenStream select tags from pattern [" +
			"SensorEvent(triggered, name='A') -> " +
			"SensorEvent(not triggered, name='A') -> " +
			"tags=TagEvent(" +
			"'E200 3411 B802 0110 2608 7586' in (ids) or " +
			"'E200 3411 B802 0110 2608 7587' in (ids) or " +
			"'E200 3411 B802 0110 2608 7588' in (ids) or " +
			"'E200 3411 B802 0110 2608 7589' in (ids) or " +
			"'E200 3411 B802 0110 2608 7590' in (ids) or " +
			"'E200 3411 B802 0110 2608 7591' in (ids) or " +
			"'E200 3411 B802 0110 2608 7592' in (ids) or " +
			"'E200 3411 B802 0110 2608 7593' in (ids) or " +
			"'E200 3411 B802 0110 2608 7594' in (ids)) until " +
			"SensorEvent(triggered, name='B') -> " +
			"SensorEvent(not triggered, name='B')]";

		String hanutaStream = 
			"insert into HanutaStream select tags from pattern [" +
			"SensorEvent(triggered, name='A') -> " +
			"SensorEvent(not triggered, name='A') -> " +
			"tags=TagEvent(" +
			"'E200 3411 B802 0110 2608 7598' in (ids) or " +
			"'E200 3411 B802 0110 2608 7606' in (ids) or " +
			"'E200 3411 B802 0110 2608 7608' in (ids) or " +
			"'E200 3411 B802 0110 2608 7609' in (ids) or " +
			"'E200 3411 B802 0110 2608 7611' in (ids) or " +
			"'E200 3411 B802 0110 2608 7612' in (ids) or " +
			"'E200 3411 B802 0110 2608 7613' in (ids) or " +
			"'E200 3411 B802 0110 2608 7614' in (ids) or " +
			"'E200 3411 B802 0110 2608 7615' in (ids)) until " +
			"SensorEvent(triggered, name='B') -> " +
			"SensorEvent(not triggered, name='B')]";

		String knoppersStream = 
			"insert into KnoppersStream select tags from pattern [" +
			"SensorEvent(triggered, name='A') -> " +
			"SensorEvent(not triggered, name='A') -> " +
			"tags=TagEvent(" +
			"'E200 3411 B802 0110 2608 7617' in (ids) or " +
			"'E200 3411 B802 0110 2608 7618' in (ids) or " +
			"'E200 3411 B802 0110 2608 7619' in (ids) or " +
			"'E200 3411 B802 0110 2608 7620' in (ids) or " +
			"'E200 3411 B802 0110 2608 7621' in (ids) or " +
			"'E200 3411 B802 0110 2608 7624' in (ids) or " +
			"'E200 3411 B802 0110 2608 7626' in (ids) or " +
			"'E200 3411 B802 0110 2608 7627' in (ids) or " +
			"'E200 3411 B802 0110 2608 7628' in (ids)) until " +
			"SensorEvent(triggered, name='B') -> " +
			"SensorEvent(not triggered, name='B')]";

		String duploStream = 
			"insert into DuploStream select tags from pattern [" +
			"SensorEvent(triggered, name='A') -> " +
			"SensorEvent(not triggered, name='A') -> " +
			"tags=TagEvent(" +
			"'E200 3411 B802 0110 2608 7629' in (ids) or " +
			"'E200 3411 B802 0110 2608 7630' in (ids) or " +
			"'E200 3411 B802 0110 2608 7631' in (ids) or " +
			"'E200 3411 B802 0110 2608 7632' in (ids) or " +
			"'E200 3411 B802 0110 2608 7633' in (ids) or " +
			"'E200 3411 B802 0110 2608 7634' in (ids) or " +
			"'E200 3411 B802 0110 2608 7635' in (ids)) until " +
			"SensorEvent(triggered, name='B') -> " +
			"SensorEvent(not triggered, name='B')]";

		String snickersStream = 
			"insert into SnickersStream select tags from pattern [" +
			"SensorEvent(triggered, name='A') -> " +
			"SensorEvent(not triggered, name='A') -> " +
			"tags=TagEvent(" +
			"'E200 3411 B802 0110 2608 7637' in (ids) or " +
			"'E200 3411 B802 0110 2608 7638' in (ids) or " +
			"'E200 3411 B802 0110 2608 7639' in (ids) or " +
			"'E200 3411 B802 0110 2608 7640' in (ids) or " +
			"'E200 3411 B802 0110 2608 7643' in (ids) or " +
			"'E200 3411 B802 0110 2608 7644' in (ids) or " +
			"'E200 3411 B802 0110 2608 7646' in (ids) or " +
			"'E200 3411 B802 0110 2608 7647' in (ids) or " +
			"'E200 3411 B802 0110 2608 7648' in (ids) or " +
			"'E200 3411 B802 0110 2608 7649' in (ids)) until " +
			"SensorEvent(triggered, name='B') -> " +
			"SensorEvent(not triggered, name='B')]";

		String marsStream = 
			"insert into MarsStream select tags from pattern [" +
			"SensorEvent(triggered, name='A') -> " +
			"SensorEvent(not triggered, name='A') -> " +
			"tags=TagEvent(" +
			"'E200 3411 B802 0110 2608 7650' in (ids) or " +
			"'E200 3411 B802 0110 2608 7651' in (ids) or " +
			"'E200 3411 B802 0110 2608 7652' in (ids) or " +
			"'E200 3411 B802 0110 2608 7653' in (ids) or " +
			"'E200 3411 B802 0110 2608 7654' in (ids) or " +
			"'E200 3411 B802 0110 2608 7655' in (ids) or " +
			"'E200 3411 B802 0110 2608 7656' in (ids) or " +
			"'E200 3411 B802 0110 2608 7659' in (ids) or " +
			"'E200 3411 B802 0110 2608 7661' in (ids) or " +
			"'E200 3411 B802 0110 2608 7662' in (ids) or " +
			"'E200 3411 B802 0110 2608 7663' in (ids)) until " +
			"SensorEvent(triggered, name='B') -> " +
			"SensorEvent(not triggered, name='B')]";

		String bonbonStream = 
			"insert into BonbonStream select tags from pattern [" +
			"SensorEvent(triggered, name='A') -> " +
			"SensorEvent(not triggered, name='A') -> " +
			"tags=TagEvent(" +
			"'E200 3411 B802 0110 2608 7666' in (ids) or " +
			"'E200 3411 B802 0110 2608 7667' in (ids) or " +
			"'E200 3411 B802 0110 2608 7672' in (ids) or " +
			"'E200 3411 B802 0110 2608 7675' in (ids) or " +
			"'E200 3411 B802 0110 2608 7676' in (ids) or " +
			"'E200 3411 B802 0110 2608 7677' in (ids) or " +
			"'E200 3411 B802 0110 2608 7678' in (ids)) until " +
			"SensorEvent(triggered, name='B') -> " +
			"SensorEvent(not triggered, name='B')]";

		String personenStream = 
			"insert into PersonenStream select tags from pattern [" +
			"SensorEvent(triggered, name='A') -> " +
			"SensorEvent(not triggered, name='A') -> " +
			"tags=TagEvent(" +
			"'E200 3411 B802 0110 2608 7679' in (ids) or " +
			"'E200 3411 B802 0110 2608 7680' in (ids) or " +
			"'E200 3411 B802 0110 2608 7681' in (ids)) until " +
			"SensorEvent(triggered, name='B') -> " +
			"SensorEvent(not triggered, name='B')]";

        try {
			listenerManager.addRule(new RuleContainer("gummibaerchen", gummibaerchenStream, "", ""), gui);
			listenerManager.addRule(new RuleContainer("hanutas", hanutaStream, "", ""), gui);
			listenerManager.addRule(new RuleContainer("knoppers", knoppersStream, "", ""), gui);
			listenerManager.addRule(new RuleContainer("duplo", duploStream, "", ""), gui);
			listenerManager.addRule(new RuleContainer("snickers", snickersStream, "", ""), gui);
			listenerManager.addRule(new RuleContainer("mars", marsStream, "", ""), gui);
			listenerManager.addRule(new RuleContainer("bonbon", bonbonStream, "", ""), gui);
			listenerManager.addRule(new RuleContainer("personen", personenStream, "", ""), gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

	/*
	 * ========================================================================
	 *
	 * Usecases for project day
	 *
	 * ========================================================================
	 */

	public void rule_11() {
        RuleContainer rc = new RuleContainer("usecase_a",
				"select * from pattern [every " +
				"SensorEvent(triggered, name='A') -> " +
				"SensorEvent(not triggered, name='A') -> " +
				"startevent=TagEvent('E200 3411 B802 0110 2608 7679' in (ids)) where timer:within(1 sec) ->" +
				"middleevent=TagEvent until " + 
				"SensorEvent(triggered, name='B') -> " +
				"SensorEvent(not triggered, name='B')]",
				"classes/cep_server/control",
				"cep_server.control.UsecaseAListener");
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

	public void rule_12() {
        RuleContainer rc = new RuleContainer("usecase_b",
				"select * from pattern [every " +
				"SensorEvent(triggered, name='A') -> " +
				"SensorEvent(not triggered, name='A') -> " +
				"startevent=TagEvent('E200 3411 B802 0110 2608 7680' in (ids)) where timer:within(1 sec) ->" +
				"middleevent=TagEvent until " + 
				"SensorEvent(triggered, name='B') -> " +
				"SensorEvent(not triggered, name='B')]",
				"classes/cep_server/control",
				"cep_server.control.UsecaseBListener");
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

	public void rule_13() {
        RuleContainer rc = new RuleContainer("usecase_c",
				"select * from pattern [every " +
				"SensorEvent(triggered, name='A') -> " +
				"SensorEvent(not triggered, name='A') -> " +
				"startevent=TagEvent('E200 3411 B802 0110 2608 7681' in (ids)) where timer:within(1 sec) ->" +
				"middleevent=TagEvent until " + 
				"SensorEvent(triggered, name='B') -> " +
				"SensorEvent(not triggered, name='B')]",
				"classes/cep_server/control",
				"cep_server.control.UsecaseCListener");
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

	public void rule_14() {
        RuleContainer rc = new RuleContainer("stock_in",
				"select * from pattern [every " +
				"SensorEvent(triggered, name='A') -> " +
				"SensorEvent(not triggered, name='A') -> " +
				"startevent=TagEvent(filled) where timer:within(1 sec) ->" +
				"middleevent=TagEvent until " + 
				"SensorEvent(triggered, name='B') -> " +
				"SensorEvent(not triggered, name='B')]",
				"classes/cep_server/control",
				"cep_server.control.MyUpdateListenerImpl");
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

	public void rule_15() {
        RuleContainer rc = new RuleContainer("stock_out",
				"select * from pattern [every " +
				"SensorEvent(triggered, name='B') -> " +
				"SensorEvent(not triggered, name='B') -> " +
				"startevent=TagEvent(filled) where timer:within(1 sec) ->" +
				"middleevent=TagEvent until " + 
				"SensorEvent(triggered, name='A') -> " +
				"SensorEvent(not triggered, name='A')]",
				"classes/cep_server/control",
				"cep_server.control.MyUpdateListenerImpl");
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}

	public void rule_16() {
        RuleContainer rc = new RuleContainer("all_tags",
				"select * from pattern [every " +
				"SensorEvent(triggered, name='A') -> " +
				"SensorEvent(not triggered, name='A') -> " +
				"startevent=TagEvent(filled) where timer:within(1 sec) ->" +
				"middleevent=TagEvent until " + 
				"SensorEvent(triggered, name='B') -> " +
				"SensorEvent(not triggered, name='B')]",
				"classes/cep_server/control",
				"cep_server.control.MyUpdateListenerImpl");
        try {
            listenerManager.addRule(rc, gui);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}
}

// vim:set ts=4 sts=4 sw=4 noet:
