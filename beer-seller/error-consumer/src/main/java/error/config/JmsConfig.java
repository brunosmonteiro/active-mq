package error.config;

import jakarta.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MessageConverter;
import shared.config.JmsBaseConfig;
import shared.config.JmsTopicListenerFactory;

@Configuration
@Import(JmsBaseConfig.class)
public class JmsConfig {
    @Value("$.{jms.consumer.orders.clientId}")
    private String errorClientId;

    @Bean
    public JmsListenerContainerFactory<?> topicListenerFactory(
            final ConnectionFactory connectionFactory,
            final MessageConverter messageConverter) {
        return JmsTopicListenerFactory.defaultTopicListenerFactory(
            connectionFactory,
            messageConverter,
            errorClientId
        );
    }
}

