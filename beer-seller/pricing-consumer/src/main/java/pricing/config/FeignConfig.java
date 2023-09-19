package pricing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(shared.config.FeignConfig.class)
public class FeignConfig {
}
