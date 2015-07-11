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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * User: Falk Alexander
 * Date: 17.05.12
 * Time: 15:48
 */
public class Chatter {
    protected String name;
    TopicSession session;
    TopicSubscriber subscriber;
    TopicPublisher publisher;
    InitialContext initialContext;
    TopicConnection connection;

    public String getName() {
        return name;
    }

    public Chatter(String name, MessageListener messageListener) throws NamingException, JMSException {
        this.name = name;
        this.init();
        Thread ts = new Thread(new SendMessagesCmd(publisher, session, this));
        ts.start();

        subscriber.setMessageListener(messageListener);
        while (ts.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // cleaning up is essential!
        subscriber.close();
        publisher.close();
        session.close();
        connection.stop();
        connection.close();
        initialContext.close();
    }

    /**
     * When you look at the comments you should notice from which file the
     * statement came.
     * @throws javax.naming.NamingException
     * @throws JMSException
     */
    protected void init() throws NamingException, JMSException {
        // The environmet for the jndi context.
        Hashtable<Object, String> env = new Hashtable<Object, String>();

        // This address is given in hornetq-beans.xml
        // Make sure that its bind address  is not localhost.
        env.put(Context.PROVIDER_URL, "jnp://192.168.178.98:1099");

        // Copied this from client-jndi.properties
        // I'm not sure what this exactly dose, so FIX ME.
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");

        // Step 1. Create an initial context to perform the JNDI lookup.
        initialContext = new InitialContext(env);

        // Step 2. Perfom a lookup on the queue
        // This topic must be initialized through hornetq-jms.xml. It looks very similar to a queue.
        Topic topic = (Topic)initialContext.lookup("/topic/Chatting");

        // Step 3. Perform a lookup on the Topic Connection Factory
        TopicConnectionFactory cf = (TopicConnectionFactory)initialContext.lookup("/ConnectionFactory");

        // Step 4.Create a JMS TopicConnection
        connection = cf.createTopicConnection();

        // Step 5. Create a JMS TopicSession
        session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        // Step 6. Create a TopicSubscriber
        subscriber = session.createSubscriber(topic);              //  , "test = test", false

        // Step 7. Create a JMS TopicPublisher
        publisher = session.createPublisher(topic);

        // Step 8. Start the Connection
        connection.start();
    }

    @Override
    public String toString() {
        return this.getClass().getName()+":"+name;
    }
}
