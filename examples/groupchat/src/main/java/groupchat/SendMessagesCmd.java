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
import javax.jms.*;
import java.util.Scanner;

/**
 * User: Falk Alexander
 * Date: 17.05.12
 * Time: 16:33
 *
 * To get content to chat with other people we need some input. So we read from
 * the commandline and send it as a message to all other people.
 */
public class SendMessagesCmd implements Runnable{

    private MessageProducer producer;
    private Session session;
    private Chatter chatter;

    public SendMessagesCmd(MessageProducer producer, Session session, Chatter chatter) {
        this.producer = producer;
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
                break;
            }
            CustomMessage cm = new CustomMessage(chatter.toString(),line);
            try {
                ObjectMessage om = session.createObjectMessage(cm);
                producer.send(om);
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }
}
