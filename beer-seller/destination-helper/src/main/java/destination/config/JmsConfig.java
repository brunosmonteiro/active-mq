package destination.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import shared.config.JmsBaseConfig;
import shared.config.JmsProducerConfig;

@Configuration
@Import({JmsBaseConfig.class, JmsProducerConfig.class})
public class JmsConfig {
}

