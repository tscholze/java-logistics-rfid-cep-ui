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

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.client.*;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;

import java.util.Date;
import java.util.HashMap;

public class App 
{

    public static void main( String[] args ) throws Exception {
        HashMap map = new HashMap();
        map.put("host", "192.168.178.98");
        map.put("port", 6666);
        TransportConfiguration config = new TransportConfiguration(NettyConnectorFactory.class.getName(), map, "netty");

        ServerLocator serverLocator = HornetQClient.createServerLocatorWithoutHA(config);
        ClientSessionFactory sf = serverLocator.createSessionFactory(config);
        ClientSession coreSession = sf.createSession(false, false, false);
        final String queueName = "queue.exampleQueue";
        coreSession.createQueue(queueName, queueName, true);
        coreSession.close();
        ClientSession session = sf.createSession();

        ClientProducer producer = session.createProducer(queueName);
        ClientMessage message = session.createMessage(false);
        final String propName = "myprop";
        message.putStringProperty(propName, "Hello sent at " + new Date());
        System.out.println("Sending the message.");
        producer.send(message);
        ClientConsumer messageConsumer = session.createConsumer(queueName);
        session.start();
        ClientMessage messageReceived = messageConsumer.receive(1000);
        System.out.println("Received TextMessage:" + messageReceived.getStringProperty(propName));
        if (sf != null)
        {
            sf.close();
        }



    }
}
