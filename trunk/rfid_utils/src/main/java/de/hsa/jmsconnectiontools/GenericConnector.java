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

package de.hsa.jmsconnectiontools;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * @author : Falk Alexander
 *         Date: 18.05.12
 *         Time: 20:13
 *
 * This class connects to a Queue on a JMS. Just the queue name is needed.
 */
@Deprecated
public class GenericConnector {

    Session session;
    MessageConsumer consumer;
    MessageProducer producer;
    Connection connection;
    InitialContext initialContext;
    String connectionName; // e.g.: "/topic/GuiInstructionTopic"
    Destination type;

    public MessageConsumer getConsumer() throws JMSException {
        if (consumer == null)
            consumer = session.createConsumer(type);
        return consumer;
    }

    public Session getSession() {
        return session;
    }

    public MessageProducer getProducer() throws JMSException {
        if (producer == null)
            producer = session.createProducer(type);
        return producer;
    }

    /**
     * Connects to the given queue. All settings are hard coded. To change them
     * you must edit the source.
     *
     * @param connectionName the queue name at the server als a normal string.
     * @throws NamingException This is thrown of the JDNI can not find its sever.
     * @throws JMSException This is thrown due to some internal error. Sorry the
     * JMS API dose not provide more information to this. :(
     */
    public GenericConnector(String connectionName) throws NamingException, JMSException {
    this.connectionName = connectionName;
    this.initialize();
    }

    public void closeConnection() throws JMSException, NamingException {
        connection.stop();
        if (consumer != null) consumer.close();
        if (producer != null) producer.close();
        session.close();
        connection.close();
        initialContext.close();
    }

    /**
     * @TODO: Fetch all of the settings from a property file or something else.
     * @TODO: The hard coded way is a bad idea. :(
     */
    private void initialize() throws JMSException, NamingException {
        Hashtable<Object, String> env = new Hashtable<Object, String>();
        env.put(Context.PROVIDER_URL, "jnp://localhost:1099");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        initialContext = new InitialContext(env);
        type = (Destination) initialContext.lookup(connectionName);
        ConnectionFactory cf = (ConnectionFactory)initialContext.lookup("/ConnectionFactory");
        connection = cf.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        connection.start();
    }
}
