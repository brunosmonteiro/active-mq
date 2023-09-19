package beer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import shared.config.JmsBaseConfig;
import shared.config.JmsProducerConfig;
import shared.config.JmsQueueListenerConfig;

@Configuration
@Import({JmsBaseConfig.class, JmsProducerConfig.class, JmsQueueListenerConfig.class})
public class JmsConfig {
}
