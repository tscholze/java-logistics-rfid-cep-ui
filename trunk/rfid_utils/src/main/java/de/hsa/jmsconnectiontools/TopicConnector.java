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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author : Falk Alexander
 *         Date: 18.05.12
 *         Time: 20:13
 *
 * This class connects to a Queue on a JMS. Just the queue name is needed.
 */
public class TopicConnector {

    TopicSession topicSession;
    List<TopicSubscriber> subscribers = new ArrayList<TopicSubscriber>();
    TopicPublisher publisher;
    TopicConnection connection;
    InitialContext initialContext;
    String connectionName; // e.g.: "/topic/GuiInstructionTopic"
    Topic topic;


    public TopicSubscriber getSubscriber() throws JMSException {
        return this.getSubscriber(null, false);
    }
    public TopicSubscriber getSubscriber(String query, boolean noLocal) throws JMSException {
        if (subscribers.isEmpty()) {
            TopicSubscriber subscriber = topicSession.createSubscriber(topic, query, noLocal);
            subscribers.add(subscriber);
        }
        return subscribers.get(0);
    }

    /**
     * To a topic many subscribers can listen. But they need different queries.
     * This method returns a new subscriber to the topic given in the constructor.
     * @param query A String with a valid query syntax. See JavaEE documentation for more information.
     * @param noLocal If true the subscriber will not listen to its own messages.
     * @return A new TopicSubscriber.
     * @throws JMSException This is thrown due to some internal error.
     */
    public TopicSubscriber addSubscriber(String query, boolean noLocal) throws JMSException {
        TopicSubscriber ts = topicSession.createSubscriber(topic, query, noLocal);
        subscribers.add(ts);
        return ts;
    }

    public TopicSession getTopicSession() {
        return topicSession;
    }

    public TopicPublisher getPublisher() throws JMSException {
        if (publisher == null)
            publisher = topicSession.createPublisher(topic);
        return publisher;
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
    public TopicConnector(String connectionName) throws NamingException, JMSException {
    this.connectionName = connectionName;
    this.initialize();
    }

    public void closeConnection() throws JMSException, NamingException {
        connection.stop();
        for (TopicSubscriber s : subscribers)
            s.close();
        if (publisher != null) publisher.close();
        topicSession.close();
        connection.close();
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
        topic = (Topic) initialContext.lookup(connectionName);
        TopicConnectionFactory cf = (TopicConnectionFactory)initialContext.lookup("/ConnectionFactory");
        connection = cf.createTopicConnection();
        topicSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        connection.start();
    }
}
