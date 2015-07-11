package java_gui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.hsa.enums.*;

/**
 * The Graphical-User-Interface for the Settings.
 * @author michi, manu
 * @version 1.0
 */

public class SettingsGui {
	private JFrame frame;
	private TreeMap<SettingParameter, String[]> settings;
	private Gui gui;
	private JPanel input;
	
	/**
	 * Constructor of the settings-GUI.
	 * @param settings The settings that are currently set.
	 * @param gui the GUI from which the settings-GUI is being called.
	 */
	public SettingsGui(TreeMap<SettingParameter, String[]> settings, Gui gui){
		this.settings = settings;
		this.gui = gui;
		frame = new JFrame("Settings");
		frame.setSize(500, 500);
		init();
		frame.setVisible(true);
	}
	
	/**
	 * Creates the Layout of the GUI.
	 */
	private void init(){
		JButton applyBtn = new JButton("Apply");
		JButton cancelBtn = new JButton("Cancel");
		JButton defaultBtn = new JButton("Default");
		
		JPanel controls = new JPanel();
		controls.add(applyBtn);
		controls.add(cancelBtn);
		controls.add(defaultBtn);
		
        applyBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ev){
        		getData();
        		gui.applySettings(settings);
        		frame.setVisible(false);
        		frame.dispose();
        	}
        });
        
        cancelBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ev){
        		gui.cancelSetting();
        		frame.setVisible(false);
        		frame.dispose();
        	}
        });
        
        defaultBtn.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ev){
        		gui.controltopicPublish(null, Instruction.DEFAULT.name());
        	}
        });
        
        frame.add(controls, BorderLayout.SOUTH);
        input = createFields();        
        frame.add(input, BorderLayout.CENTER);  
	}
	
	/**
	 * Dynamically creates the Text-fields in which the settings are entered.
	 * @return A JPanel Object, which contains the settingParameter with the values.
	 */
	public JPanel createFields(){
        input = new JPanel(new GridLayout(settings.size(),0));
        
        for (SettingParameter s : settings.keySet()) {
        	JPanel row = new JPanel(new GridLayout(1,3));
        	JLabel label = new JLabel(s.name()+":");
        	row.add(label);
        	for (int i = 0; i < settings.get(s).length; i++) {
        		JTextField tf = new JTextField(settings.get(s)[i]);
        		row.add(tf);
			}
        	input.add(row);
		}
        
        return input;
	}
	
	/**
	 * Reads the entered values from the Text-fields.
	 */
	public void getData(){
		SettingParameter par = null;
		String[] value = null;
		int index = 0;
		for (Component outterComponent : input.getComponents()) {
			JPanel innerPanel = (JPanel) outterComponent;
			for (Component innterComponent : innerPanel.getComponents()) {
				if(innterComponent instanceof JLabel){
					par = SettingParameter.valueOf(((JLabel) innterComponent).getText().substring(0, ((JLabel) innterComponent).getText().length()-1));
					value = new String[par.getDefault().length];
					index = 0;
				}
				else if(innterComponent instanceof JTextField){
					value[index] = ((JTextField)innterComponent).getText();
					index++;
				}
				if(value.length == index){
					settings.put(par, value);
					par=null;
					value = null;
				}
			}
		}
	}
	
	/**
	 * Overwrites the current settings shown on the GUI.
	 * @param settings New settings to show.
	 */
	public void updateSettingData(TreeMap<SettingParameter, String[]> settings){
		this.settings = settings;
		frame.remove(input);
		input = createFields();
		
		frame.add(input, BorderLayout.CENTER);
		frame.repaint();
		frame.setVisible(true);
	}
}
