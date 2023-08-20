package notification.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@Configuration
public class JmsConfig {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String NOTIFICATION_CONSUMER_ID = "inventory-consumer";

    @Bean
    public ConnectionFactory connectionFactory() {
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        return connectionFactory;
    }

    @Bean
    public JmsListenerContainerFactory<?> topicListenerFactory(final ConnectionFactory connectionFactory) {
        final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true); // Set to true for topics
        factory.setSubscriptionDurable(true);
        factory.setClientId(NOTIFICATION_CONSUMER_ID); // Set a client ID
        return factory;
    }
}

