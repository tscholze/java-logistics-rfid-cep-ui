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

package examples.groupchat_extended.src.main.java.groupchat_extended.bot;

import groupchat_extended.CustomMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

/**
 * User: Falk Alexander
 * Date: 17.05.12
 * Time: 16:33
 * <p/>
 * To get content to chat with other people we need some input. So we read from
 * the commandline and send it as a message to all other people.
 */
public class SendMessagesBot {

    private Bot bot;

    public SendMessagesBot(Bot bot) {
        this.bot = bot;
    }

    public void reply(Message message) throws JMSException {
        String text = "Greetings, " + message.getStringProperty("author") + " !";
        CustomMessage cm = new CustomMessage(bot.toString(), text);
        try {
            ObjectMessage om = bot.getSession().createObjectMessage(cm);
            om.setStringProperty("instance", cm.getClass().getName());
            om.setStringProperty("author", bot.getName());
            bot.getPublisher().publish(om);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
