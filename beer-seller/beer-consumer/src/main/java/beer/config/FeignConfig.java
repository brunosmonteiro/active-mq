package beer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import(shared.config.FeignConfig.class)
@PropertySource("classpath:shared-application.properties")
public class FeignConfig {
}
