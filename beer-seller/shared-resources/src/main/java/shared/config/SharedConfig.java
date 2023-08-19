package shared.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("shared.repository")
@ComponentScan(basePackages = {"shared"})
@EntityScan("shared.entity")
public class SharedConfig {
}

