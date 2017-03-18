package au.com.auspost.smartspb;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"au.com.auspost.smartspb.domain"})
@EnableJpaRepositories(basePackages = {"au.com.auspost.smartspb.dao"})
public class RepositoryConfiguration {
}
