package notification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import shared.config.SharedConfig;

@Configuration
@Import(SharedConfig.class)
public class SharedConfigModule {
}
