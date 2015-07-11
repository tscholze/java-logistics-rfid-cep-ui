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

import java.io.Serializable;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TopicMgr {
	private InitialContext initialContext;
	private TopicSession session;
	private TopicSubscriber subscriber;
	private TopicPublisher publisher;
	private Topic topic;
	private boolean isSubscriber = false;
	private boolean isPublisher = false;
    
	public TopicMgr(String hornetIP, String hornetPort, String topicName, boolean isSubscriber, boolean isPublisher) throws JMSException, NamingException{
		this.isSubscriber = isSubscriber;
		this.isPublisher = isPublisher;
		
		init(hornetIP, hornetPort, topicName);
	}
	
	public TopicMgr(String hornetIP, String hornetPort, String topicName, boolean isSubscriber, boolean isPublisher, MessageListener listener) throws JMSException, NamingException{
		this.isSubscriber = isSubscriber;
		this.isPublisher = isPublisher;
		
		init(hornetIP, hornetPort, topicName);
		setSubscriber(listener);
	}
	
	public void init(String hornetIP, String hornetPort, String topicName) throws JMSException, NamingException{
        Hashtable<Object, String> env = new Hashtable<Object, String>();

        env.put(Context.PROVIDER_URL, "jnp://"+hornetIP+":"+hornetPort);

        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");

        initialContext = new InitialContext(env);

        topic = (Topic)initialContext.lookup(topicName);

        TopicConnectionFactory cf = (TopicConnectionFactory) initialContext.lookup("/ConnectionFactory");
        
        TopicConnection connection = null;
        connection = cf.createTopicConnection();

        session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        if(isPublisher){
        	publisher = session.createPublisher(topic);
        }

        // Step 9. Create a JMS Message Consumer
        if(isSubscriber){
//        	subscriber = session.createSubscriber(topic);
        	subscriber = session.createSubscriber(topic, null, true);
        }
     
        // Step 10. Start the Connection
        connection.start();
	}
	
	public void setSubscriber(MessageListener listener) throws JMSException{
		subscriber.setMessageListener(listener);
	}
	
	public void setSubscriberWithQuery(MessageListener listener, String query) throws JMSException{
		TopicSubscriber ts = session.createSubscriber(topic, "uniqueName = '" + query + "'", false);
		ts.setMessageListener(listener);
	}
	
	public void publish(Object data, String propertyValue) throws JMSException{
		ObjectMessage om = session.createObjectMessage((Serializable) data);
		om.setStringProperty("property", propertyValue);
		om.setJMSType("topic");
        publisher.publish(om);
	}
	
	public void publish(Object data, String propertyName, String propertyValue) throws JMSException{
		ObjectMessage om = session.createObjectMessage((Serializable) data);
		om.setStringProperty(propertyName, propertyValue);
		om.setJMSType("topic");
        publisher.publish(om);
	}

	public void shutDown() throws JMSException, NamingException{
		if(publisher!=null){
			publisher.close();
		}
		if(subscriber!=null){
			subscriber.close();
		}
		if(session!=null){
			session.close();
		}
		if(initialContext!=null){
			initialContext.close();
		}
	}
	
}
