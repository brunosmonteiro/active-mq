package order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import shared.config.JmsBaseConfig;
import shared.config.JmsQueueListenerConfig;

@Configuration
@Import({JmsBaseConfig.class, JmsQueueListenerConfig.class})
public class JmsConfig {
}
