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

package examples.groupchat_extended.src.main.java.groupchat_extended;

import groupchat_extended.bot.Bot;
import groupchat_extended.bot.ReceiveMessageBot;
import groupchat_extended.bot.SendMessagesBot;
import groupchat_extended.human.Chatter;
import groupchat_extended.human.ReceiveMessageCmd;

import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws NamingException, JMSException {
        if (args.length < 1 || !(args[0] instanceof String)) {
            System.out.println("Pass the name of the chatter as your fist argument.");
            System.exit(1);
        }
        Bot bot0 = new Bot("C3PO");
        bot0.getSubscriber().setMessageListener(new ReceiveMessageBot(bot0));
        bot0.setSendMessagesBot(new SendMessagesBot(bot0));

        Bot bot1 = new Bot("R2D2");
        bot1.getSubscriber().setMessageListener(new ReceiveMessageBot(bot1));
        bot1.setSendMessagesBot(new SendMessagesBot(bot1));

        Bot bot2 = new Bot("Terminator");
        bot2.getSubscriber().setMessageListener(new ReceiveMessageBot(bot2));
        bot2.setSendMessagesBot(new SendMessagesBot(bot2));

        String name = args[0];
        System.out.println("Maybe you want to greet a bot with ':greet <Botname>'");
        Chatter c = new Chatter(name, new ReceiveMessageCmd());
        System.out.println("Shutting down");
        bot0.shutdown();
        bot1.shutdown();
        bot2.shutdown();

        /* Second method...
            Start twice the app - each with a diffrent username

            Chatter c = new Chatter("Foo");
            //Chatter c = new Chatter("Bar");

            Notice: Type something, then press enter!
         */
    }
}
