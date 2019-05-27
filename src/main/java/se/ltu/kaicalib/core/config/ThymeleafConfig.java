package se.ltu.kaicalib.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;


/**
 * Configurations relevant to Thymeleaf templateing engine
 *
 * @author
 */
@Configuration
public class ThymeleafConfig {

    /**
     * This allows the "sec" notation in HTML to be interacted correctly
     * between Thymeleaf - Spring.
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect( ){
        return new SpringSecurityDialect();
    }
}
