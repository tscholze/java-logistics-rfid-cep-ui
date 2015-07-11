/*
 * Visualisierung eines RFID-Scanners
 * Copyright (C) 2012  ss12rfid-Team
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
	
	private Properties defaultProps = new Properties();
	private File file;
	
	public PropertyLoader(String propertieFilename) throws IOException {
		//File muss in diesem Beispiel im gleichen Verzeichnis wie src liegen
		file = new File("./"+propertieFilename + ".properties");
		
	}
	
	public void readProperties(){
		try { 
			// load default properties
			defaultProps.load(new FileReader(file));
		} catch (Exception e) {
			System.err.println("Properties file not found!");
		} 
	}
	
	public Properties getProperties() {
		return defaultProps;
	}
	
	public void writeProperties(String[][] properties){

		for (String[] string : properties) {
			defaultProps.setProperty(string[0], string[1]); //key - value
		}
		
		FileWriter fw;
		
		try {
			fw = new FileWriter(file);
			defaultProps.store(fw, "my comments"); //store-methode �bernimmt das schreiben in den Outputstream
		} catch (FileNotFoundException e) {
			System.err.println("Properties file not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}