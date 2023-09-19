package shared.config;

import jakarta.jms.ConnectionFactory;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MessageConverter;

public class JmsTopicListenerFactory {
    public static JmsListenerContainerFactory<?> defaultTopicListenerFactory(
            final ConnectionFactory connectionFactory,
            final MessageConverter messageConverter,
            final String clientId) {
        final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true);
        factory.setMessageConverter(messageConverter);
        factory.setClientId(clientId);
        factory.setSessionTransacted(true);
        return factory;
    }
}
