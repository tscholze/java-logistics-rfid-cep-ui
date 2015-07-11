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

package groupchat;

import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws NamingException, JMSException {
        if (args.length < 1 && !(args[0] instanceof String)) {
            System.out.println("Pass the name of the chatter as your fist argument.");
            System.exit(1);
        }
        
        System.out.println("Wait 5 secs, then type something...");
        String name = args[0];
        Chatter c = new Chatter(name);

        /* Second method...
            Start twice the app - each with a diffrent username

            Chatter c = new Chatter("Foo");
            //Chatter c = new Chatter("Bar");

            Notice: Type something, then press enter!
         */
    }
}
