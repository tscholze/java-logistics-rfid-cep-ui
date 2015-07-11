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
public class QueueConnector {

    QueueSession queueSession;
    QueueReceiver queueReceiver;
    QueueSender queueSender;
    QueueConnection queueConnection;
    InitialContext initialContext;
    String connectionName; // e.g.: "/topic/GuiInstructionTopic"
    Queue queue;

    public MessageConsumer getQueueReceiver() throws JMSException {
        if (queueReceiver == null)
            queueReceiver = queueSession.createReceiver(queue);
        return queueReceiver;
    }

    public QueueSession getQueueSession() {
        return queueSession;
    }

    public QueueSender getQueueSender() throws JMSException {
        if (queueSender == null)
            queueSender = queueSession.createSender(queue);
        return queueSender;
    }

    /**
     * Connects to the given queue. All settings are hard coded. To change them
     * you must edit the source.
     *
     * @param connectionName the queue name at the server als a normal string.
     * @throws javax.naming.NamingException This is thrown of the JDNI can not find its sever.
     * @throws javax.jms.JMSException This is thrown due to some internal error. Sorry the
     * JMS API dose not provide more information to this. :(
     */
    public QueueConnector(String connectionName) throws NamingException, JMSException {
    this.connectionName = connectionName;
    this.initialize();
    }

    public void closeConnection() throws JMSException, NamingException {
        queueConnection.stop();
        if (queueReceiver != null) queueReceiver.close();
        if (queueSender != null) queueSender.close();
        queueSession.close();
        queueConnection.close();
        initialContext.close();
    }

    /**
     * @TODO: Fetch all of the settings from a property file or something else.
     * @TODO: The hard coded way is a bad idea. :(
     */
    private void initialize() throws JMSException, NamingException {
        Hashtable<Object, String> env = new Hashtable<Object, String>();
        env.put(Context.PROVIDER_URL, "jnp://192.168.178.98:1099");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        initialContext = new InitialContext(env);
        queue = (Queue) initialContext.lookup(connectionName);
        QueueConnectionFactory cf = (QueueConnectionFactory)initialContext.lookup("/ConnectionFactory");
        queueConnection = cf.createQueueConnection();
        queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

        queueConnection.start();
    }
}
