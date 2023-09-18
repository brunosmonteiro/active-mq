package beer.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {
    private static final String BROKER_URL = "tcp://localhost:61616";

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
    public DefaultJmsListenerContainerFactory queueListenerFactory(
            final ConnectionFactory connectionFactory,
            final MessageConverter messageConverter) {
        final var factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);
        factory.setMessageConverter(messageConverter);
        factory.setSessionTransacted(true);
        return factory;
    }
}

