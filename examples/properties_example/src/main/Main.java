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

import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyLoader pl=null;
		String myPropertiesFilename="example"; //look at example.properties
		try {
			pl = new PropertyLoader(myPropertiesFilename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if(pl!=null){
			//Read some properties
			pl.readProperties();
			String firstProperty = pl.getProperties().getProperty("first"); //Parameter bei getProperty muss exakt so heissen wie in der properties file
			String secondProperty = pl.getProperties().getProperty("second");
		
			System.out.println(firstProperty);
			System.out.println(secondProperty);
			
			//Write some properties
			String[][] newPropertyCollection=new String[2][2];
			
			//key - value den er schreiben soll
			String[] firstNewProperty = {"ip","10.20.30.40:5060"};
			String[] secondNewProperty = {"port","1337"};
			
			//daten hinzuf�gen
			newPropertyCollection[0] = firstNewProperty;
			newPropertyCollection[1] = secondNewProperty;
			
			//schreiben
			pl.writeProperties(newPropertyCollection);
			
			//test: wieder lesen:
			pl.readProperties();
			String ip = pl.getProperties().getProperty("ip");
			String port = pl.getProperties().getProperty("port");
			
			System.out.println(ip);
			System.out.println(port);
		}
	}

}
