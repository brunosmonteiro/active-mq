package inventory.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfig {
    private static final String BROKER_URL = "tcp://localhost:61616";

    @Bean
    public ConnectionFactory connectionFactory() {
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(final ConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        jmsTemplate.setPubSubDomain(false);
        return jmsTemplate;
    }
}

