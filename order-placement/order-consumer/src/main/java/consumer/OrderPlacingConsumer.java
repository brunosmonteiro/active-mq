package consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

public class OrderPlacingConsumer {
    public static void main(String[] args) throws JMSException, IOException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic("order-topic");

        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage textMessage) {
                try {
                    System.out.println("[OrderPlacingConsumer] Received: " + textMessage.getText());
                } catch (JMSException e) {
                    System.err.println("[OrderPlacingConsumer] An error occurred while reading the message: " + e.getMessage());
                }
            } else {
                System.err.println("[OrderPlacingConsumer] Received non-text message");
            }
        });

        // Keep the application alive to receive messages
        System.in.read();

        consumer.close();
        session.close();
        connection.close();
    }
}
