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

import groupchat_extended.CustomMessage;

import javax.jms.*;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * User: Falk Alexander
 * Date: 17.05.12
 * Time: 16:33
 * <p/>
 * To get content to chat with other people we need some input. So we read from
 * the commandline and send it as a message to all other people.
 */
public class SendMessagesCmd implements Runnable {

    private TopicSession session;
    private Chatter chatter;
    private TopicPublisher publisher;

    public SendMessagesCmd(TopicPublisher publisher, TopicSession session, Chatter chatter) {
        this.publisher = publisher;
        this.session = session;
        this.chatter = chatter;
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        boolean running = true;
        String line;
        while (running) {
            line = in.nextLine();
            if (line.equals(":q")) {
                running = false;
                continue;
            } else if (line.startsWith(":greet")) {
                line = line.split(" ")[1];
                Map<String, String> map = new TreeMap<String, String>();
                map.put("custom_topic", "greetings");
                map.put("target", line);
                this.send(chatter.getName() + " greets " + line, map);
            } else {
                this.send(line);
            }
        }
    }
    private void send(String message) {
        // I just needed an empty map.
        Map<String, String> map = new TreeMap<String, String>();
        this.send(message, map);
    }
    private void send(String message, Map<String, String> additionalProperties) {
        CustomMessage cm = new CustomMessage(chatter.toString(), message);
        try {
            ObjectMessage om = session.createObjectMessage(cm);
            om.setStringProperty("instance", cm.getClass().getName());
            om.setStringProperty("author", chatter.getName());
            for (String key : additionalProperties.keySet()) {
                om.setStringProperty(key ,additionalProperties.get(key));
            }
            //System.out.println(om.getStringProperty("custom_topic").length());
            publisher.publish(om);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
