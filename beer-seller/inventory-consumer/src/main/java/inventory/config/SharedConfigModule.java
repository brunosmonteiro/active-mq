package inventory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import shared.config.FeignConfig;

@Configuration
@Import(FeignConfig.class)
public class SharedConfigModule {
}
