This is the simplest implementation of the messaging system, involving pure Java and ActiveMQ. No framework used.
The goal here is to simply establish a connection, create a consumer and interpret a message.

The `ConnectionFactory`, `Connection`, `Session` and `Destination` elements serve the same purpose that in the producer,
so the goals and details of them are in the raw-producer.md file.

## Walk-through

### Create a connection factory.
You can use different protocols for the producer and consumer, as long as the broker is configured to accept connections
on these protocols. For example, you might have a producer sending messages using a TCP connection while a consumer
reads messages over an SSL/TLS connection. This could be useful if you have different security requirements for 
different parts of your system. In most practical scenarios, though, you'd likely use the same protocol for both
producers and consumers connecting to a given broker, unless you have specific reasons to do otherwise.
```
ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
```

### Create and start a connection.
```
Connection connection = factory.createConnection();
connection.start();
```

### Create a session.
The ACK Mode here is relevant for how the consumer is going to handle acknowledgments:
- `Session.AUTO_ACKNOWLEDGE`: With this mode, the session automatically acknowledges the receipt of a message when it has 
been successfully received.
- `Session.CLIENT_ACKNOWLEDGE`: With this mode, the client acknowledges messages manually by calling the acknowledge 
method on the received message.
- `Session.DUPS_OK_ACKNOWLEDGE`: This mode is similar to `AUTO_ACKNOWLEDGE`, but it allows for the possibility of duplicate 
messages. It's more lenient and can be more performant in some situations at the cost of potentially processing messages
more than once.
```
Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
Destination destination = session.createQueue("my-queue");
```

### Create a MessageProducer from the Session to the Topic or Queue
A `MessageConsumer` is responsible for receiving messages sent to a specific destination, such as a queue or topic. It 
effectively acts as the receiving endpoint for messages in a JMS communication setup.

You can create a `MessageConsumer` with a message selector, which is a String that specifies criteria messages must meet
to be received by the consumer. For example, you might only want to receive messages with a particular property set to a
certain value. This is done using the `session.createConsumer(Destination destination, String messageSelector)` method.

In the context of topics (pub/sub messaging), you can create a durable subscriber, which means that messages published 
to the topic are kept until the subscriber consumes them, even if the subscriber is not actively connected at the time 
the messages are sent.
```
MessageConsumer consumer = session.createConsumer(destination);
```

### Create a listener.
You can use the `MessageConsumer` to receive messages either synchronously or asynchronously.
1. **In synchronous mode**, you'd call the `receive` method, which blocks the thread until a message arrives or a 
timeout occurs.
2. **In asynchronous mode**, you'd register a MessageListener with the consumer, and the listener's `onMessage` method would
be called whenever a message arrives. This way, you can process messages as they come in without blocking the rest of 
your application.

Depending on the client library and configuration, messages might be buffered on the client side until the listener is 
ready to process them. This allows the client to handle bursts of messages more efficiently but must be managed 
carefully to avoid memory issues.

If an exception is thrown from the onMessage method, it's usually handled according to a provider-specific policy, which
might include logging the error or invoking an exception listener.

Let's assume a scenario where a broker has messages A,B,C,D to deliver in a queue, and there are two consumers, C1 and C2.
- The broker delivers A for C1 and B for C2, but A is not acknowledged.

The exact behavior can depend on the configuration of the broker and the consumer, including settings related to 
acknowledgment mode, redelivery policy, and transactions.
- **Immediate Redelivery**: If the broker is configured for immediate redelivery, it might attempt to redeliver A to the
same consumer (C1) or another consumer right away. This could lead to A being prioritized over other messages like C and D.
- **Redelivery Delay**: If there's a redelivery delay configured, the broker might wait for a specified amount of time 
before attempting to redeliver A. During this delay, other messages like C and D might be delivered.
- **Redelivery Limit**: If there's a redelivery limit, and it's reached, the broker might move the message to a dead-letter
queue or take other action instead of continuing to attempt redelivery.
- **Transactional Session**: If the session is transactional and the transaction is rolled back, the message would
typically be returned to the queue, and its delivery order might be preserved or not, depending on the broker's 
configuration.

By default, ActiveMQ will attempt to redeliver a message that was not acknowledged. Here's how it generally works:
- **Redelivery Attempts**: If a message is not acknowledged, ActiveMQ will attempt to redeliver it. By default, ActiveMQ
will try to redeliver the message up to 6 times.
- **Redelivery Delay**: There is no delay between redelivery attempts by default, so the broker might attempt to 
redeliver the message quickly.
- **Dead Letter Queue:** If the maximum redelivery attempts are reached and the message is still not acknowledged, 
ActiveMQ can move the message to a dead letter queue (DLQ). The DLQ is a special queue that stores messages that could 
not be delivered successfully.
- **Ordering**: The redelivery may not necessarily preserve the original order of messages. If message A is being 
redelivered, other messages like C and D might be delivered to other consumers in the meantime.
- **Configuration**: These behaviors can be configured through ActiveMQ's redelivery policy. You can specify settings 
like the maximum redelivery attempts, redelivery delay, and whether to use exponential back-off between attempts.

The application will keep running as long as there is no explicit call to close the connection or session.
```
consumer.setMessageListener(message -> {
    if (message instanceof TextMessage textMessage) {
        try {
            System.out.println("Received: " + textMessage.getText());
        } catch (JMSException e) {
            System.err.println("An error occurred while reading the message: " + e.getMessage());
        }
    } else {
        System.err.println("Received non-text message");
    }
});
```
## Code Snippet
This is the full code snippet.
```
package consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class RawOrderConsumer {
public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("my-queue");

        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage textMessage) {
                try {
                    System.out.println("Received: " + textMessage.getText());
                } catch (JMSException e) {
                    System.err.println("An error occurred while reading the message: " + e.getMessage());
                }
            } else {
                System.err.println("Received non-text message");
            }
        });
        
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
```