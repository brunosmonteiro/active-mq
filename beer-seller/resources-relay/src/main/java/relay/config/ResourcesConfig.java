package relay.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("relay.repository")
@ComponentScan(basePackages = {"relay"})
@EntityScan("relay.entity")
public class ResourcesConfig {
}
