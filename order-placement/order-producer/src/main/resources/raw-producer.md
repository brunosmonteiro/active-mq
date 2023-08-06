This is the simplest implementation of the messaging system, involving pure Java and ActiveMQ. No framework used.
The goal here is to simply establish a connection, create a producer and send a message.

## Walk-through

### Create a connection factory.
This wraps the details of how to initiate a connection. 

A factory is associated to only one broker. If more brokers need to be reached, we need to create the same amount of 
ActiveMQConnectionFactory objects.

Here, we define 'tcp' as it is a reliable transport layer protocol. We could use 'udp' if message reliability is not of 
primary concern. We could also use 'http' that would make communication with some firewalls or  other components easy; in the 
end, it would rely on tcp for the connection. Alternatively, we could use 'ssl' or 'tls' that are session layer protocols with
security guarantees, and they would use 'tcp' internally as well.
            
For simplicity purposes, let's keep it tcp for performance consideration.
```
ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
```

### Create and start a connection.
A connection is a heavy object in ActiveMQ. It carries all necessary information to connect to a broker.

A connection can be shared by multiple threads, or used by one thread only. It is recommended that multiple threads use the same
connection object, but it requires coordination and special care, so they don't interfere with each other. Tools like Spring JMS
or Apache Camel can help handle this.
```     
Connection connection = connectionFactory.createConnection();
connection.start();
```

### Create a session.
A session is specific to a single thread, and the way through which a thread sends the message to the broker.

A session encapsulates behavior regarding transaction management, message acknowledgment, message creation, destination handling,
producer and consumer creation, serialization and deserialization, and error handling.

The two parameters refer to
1. **Transactional**: If set to true, the session will be transactional, meaning that you can group a series of sends and 
receives into a single transaction. In a transactional session, messages are sent and received as part of a larger, 
atomic operation, and changes can be committed or rolled back together. If set to false, the session is not
transactional, and sends and receives are handled independently;
2. **ACK Mode**: Not relevant since we are going to only create producers, no consumers.
```
Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
```

### Create the destination (Topic or Queue)
Though the method is called 'createQueue', it is in fact obtaining a reference to the destination, not really creating it.

By having one destination reference per session, we ensure consistency, because, even though the physical destination will be
called by different sessions, ActiveMQ can handle all operations within the scope of each session separately.
```
Destination destination = session.createQueue("order-queue");
```

### Create a MessageProducer from the Session to the Topic or Queue
The `MessageProducer` is the object that establishes a connection to the actual destination.

It carries information from the session but can be customized regarding priority, persistence and ttl, for example. In
the line after the creation, it tells that its messages should be persisted.

Multiple producers can be created for the same destination, if the flexibility is needed.

A producer in JMS typically maintains an open connection with the broker as long as the connection object is open and
active. When you create a connection and start it (e.g., by calling connection.start()), a TCP connection is established
with the broker, and that connection remains open until it's explicitly closed (e.g., by calling connection.close()).

The connection is not specific to a single message send operation. Instead, it represents a long-lived relationship
between the producer application and the broker. Multiple messages can be sent over the same connection, and the
connection remains open even when the producer is idle and not actively sending messages.
```
MessageProducer producer = session.createProducer(destination);
producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
```

### Create a message
There are many types of message types provided by ActiveMQ. The most generic that attends to most scenarios is the
`TextMessage` object. Since objects can be represented in formats like JSON or XML, we can simply convert the object
to one of these representations and send it as string.

The text in this case refers to the body of the message. To set headers, whether already defined by JMS or customized
by the client, you can use methods from the `Message` interface, from where the `TextMessage` inherits its content.
The comments showcase some possibilities.

```
String text = "Order Message!";
TextMessage message = session.createTextMessage(text);
// message.setJMSPriority(9); // Set priority to 9
// message.setJMSExpiration(System.currentTimeMillis() + 60000); // Set expiration to 60 seconds from now
// message.setStringProperty("customProperty", "customValue"); 
```

### Send the message
When calling the `send` message, this is an overview of what happens:
1. **Preparation of Message**: Before sending, the message may be enriched with additional information, such as setting
the JMSTimestamp and JMSMessageID fields if they haven't been set already. Any default delivery parameters on the
producer, like default priority, may also be applied to the message if they haven't been explicitly set. 
2. **Serialization**: The message's properties, headers, and body are converted into a sequence of bytes. 
3. **Client-Side Routing**: The client library determines the appropriate destination for the message based on the
`Destination` object associated with the producer. This destination contains information about where the message should
be sent, such as the queue name or topic name. 
4. **Transmission**: The serialized message is sent to the broker over the network using the underlying protocol (e.g., TCP).
The producer will use the connection and session to manage this communication. 
5. **Acknowledgment (Optional)**: Depending on the acknowledgment mode of the session and any transaction boundaries,
the producer might wait for an acknowledgment from the broker that the message has been received and processed
appropriately. If something goes wrong, an exception will be thrown.

After this, what happens already belongs to the broker and its consumers.
```
producer.send(message);
```

### Clean up
This ensures that the system remains efficient and doesn't leak resources. Here's what happens when each of these is closed:

`session.close()`: Closing a session does the following:
- **Terminates the Session**: Any ongoing work within the session is terminated;
- **Releases Resources**: Any resources that were held by the session, such as producers, and temporary destinations,
are released.

`connection.close()`: Closing a connection does the following:
- **Closes All Sessions**: All sessions created from this connection are closed, which includes everything that occurs 
when calling session.close().
- Releases Resources: Any resources that were allocated for this connection, including threads, sockets, and other 
lower-level resources, are released.
- Notifies the Broker: The broker is notified that the connection has been closed, allowing it to release any resources
it had allocated for this connection as well.
```
session.close();
connection.close();
```

## Code Snippet
This is the full code snippet.
```
package producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class RawOrderProducer {

    public static void main(final String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Create a connection.
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a session.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("order-queue");

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Create a message
        String text = "Order Message!";
        TextMessage message = session.createTextMessage(text);

        // Send the message
        producer.send(message);

        // Clean up
        session.close();
        connection.close();
    }
}
```