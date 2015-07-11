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
    Session session;
    MessageProducer producer;
    MessageConsumer messageConsumer;
    InitialContext initialContext;
    
    public Chatter(String name) throws NamingException, JMSException {
        this.name = name;
        this.init();
        Thread ts = new Thread(new SendMessagesCmd(producer, session, this));
        ts.start();
        ReceiveMessageCmd rmc = new ReceiveMessageCmd();
        messageConsumer.setMessageListener(rmc);
        while (ts.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // cleaning up is essential!
        producer.close();
        messageConsumer.close();
        session.close();
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
        // This topic must be initialized through hornetq-jms.xml. It looks very similar to a queque.
        // Have you seen this? It's a topic. :D
        Topic topic = (Topic)initialContext.lookup("/topic/Chatting");

        // Step 3. Perform a lookup on the Connection Factory
        ConnectionFactory cf = (ConnectionFactory)initialContext.lookup("/ConnectionFactory");

        Connection connection = null;
        // Step 4.Create a JMS Connection
        connection = cf.createConnection();

        // Step 5. Create a JMS Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Step 6. Create a JMS Message Producer
        producer = session.createProducer(topic);

        // Step 9. Create a JMS Message Consumer
        messageConsumer = session.createConsumer(topic);

        // Step 10. Start the Connection
        connection.start();
    }

    @Override
    public String toString() {
        return this.getClass().getName()+":"+name;
    }
}
