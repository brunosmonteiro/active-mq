package producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.notification.NotificationDto;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class NotificationProducer {
    public static void main(final String[] args) throws JMSException, JsonProcessingException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Create a connection.
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a session.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("notification-queue");

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Create a message
        final NotificationDto dummy = new NotificationDto();
        dummy.setText("This is a dummy message!");

        final ObjectMapper objectMapper = new ObjectMapper();
        TextMessage message = session.createTextMessage(objectMapper.writeValueAsString(dummy));

        // Send the message
        producer.send(message);

        // Clean up
        session.close();
        connection.close();
    }
}
