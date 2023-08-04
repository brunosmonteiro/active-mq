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
