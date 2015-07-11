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

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * Most of this example is from the hornetq examples. You may find it distributed
 * with your copy of hornetq in the directory \hornetq-2.2.14.Final\examples\jms\queue
 */
public class App 
{
    public static void main( String[] args ) throws JMSException, NamingException {

        // The environmet for the jndi context.
        Hashtable<Object, String> env = new Hashtable<Object, String>();

        // This address is given in hornetq-beans.xml
        // Make sure that its bind address  is not localhost.
        env.put(Context.PROVIDER_URL, "jnp://192.168.178.98:1099");

        // Copied this from client-jndi.properties
        // I'm not sure what this exactly dose, so FIX ME.
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");

        // I've seen this in soem property data and examples, but I don't know what this means.
        //env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");

        // Step 1. Create an initial context to perform the JNDI lookup.
        InitialContext initialContext = new InitialContext(env);

        // Step 2. Perfom a lookup on the queue
        // This queque must be initialized through hornetq-jms.xml
        Queue queue = (Queue)initialContext.lookup("/queue/ExampleQueue");

        // Step 3. Perform a lookup on the Connection Factory
        ConnectionFactory cf = (ConnectionFactory)initialContext.lookup("/ConnectionFactory");

        Connection connection = null;
        // Step 4.Create a JMS Connection
        connection = cf.createConnection();

        // Step 5. Create a JMS Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Step 6. Create a JMS Message Producer
        MessageProducer producer = session.createProducer(queue);

        // Step 7. Create a Text Message
        TextMessage message = session.createTextMessage("This is a text message");

        System.out.println("Sent message: " + message.getText());

        // Step 8. Send the Message
        producer.send(message);

        // Step 9. Create a JMS Message Consumer
        MessageConsumer messageConsumer = session.createConsumer(queue);

        // Step 10. Start the Connection
        connection.start();

        // Step 11. Receive the message
        TextMessage messageReceived = (TextMessage)messageConsumer.receive(5000);

        System.out.println("Received message: " + messageReceived.getText());

        // Always close resources when you're finished.
        connection.close();
    }
}
