package inventory.config;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String INVENTORY_CONSUMER_ID = "inventory-consumer";

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        RedeliveryPolicy policy = new RedeliveryPolicy();
        policy.setMaximumRedeliveries(3); // Number of redelivery attempts
        policy.setInitialRedeliveryDelay(1000); // Delay for the first redelivery
        policy.setUseExponentialBackOff(true); // Increase delay exponentially
        policy.setBackOffMultiplier(2); // Multiplier to increase delay
        connectionFactory.setRedeliveryPolicy(policy);
        return connectionFactory;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory(
            final ConnectionFactory connectionFactory,
            final MessageConverter messageConverter) {
        final var factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false); // Set to false for queues
        factory.setMessageConverter(messageConverter);
        factory.setSessionTransacted(true);
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> topicListenerFactory(
            final ConnectionFactory connectionFactory,
            final MessageConverter messageConverter) {
        final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true); // Set to true for topics
        factory.setSubscriptionDurable(true);
        factory.setClientId(INVENTORY_CONSUMER_ID); // Set a client ID
        factory.setMessageConverter(messageConverter);
        factory.setSessionTransacted(true);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(final ConnectionFactory connectionFactory, final MessageConverter messageConverter){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        jmsTemplate.setPubSubDomain(false);
        jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }
}

