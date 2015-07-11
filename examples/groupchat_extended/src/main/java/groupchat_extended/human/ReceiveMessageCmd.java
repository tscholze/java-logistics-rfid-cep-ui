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

package examples.groupchat_extended.src.main.java.groupchat_extended.human;

import javax.jms.*;

/**
 * User: Falk Alexander
 * Date: 17.05.12
 * Time: 16:53
 *
 * This class reacts on incoming messages from a queque or a topic.
 */
public class ReceiveMessageCmd implements MessageListener{

    /**
     * This method is called upon an message. What it dose should be pretty simple.
     */
    @Override
    public void onMessage(Message message) {
        ObjectMessage om = (ObjectMessage) message;
        try {
            System.out.println(om.getObject());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
