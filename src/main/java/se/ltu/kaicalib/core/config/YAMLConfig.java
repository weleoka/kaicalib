package se.ltu.kaicalib.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that would implement application configuration from file.
 *
 * Currently not used as implementation is sufficiently static to not warrant it.
 */
@Configuration
//@EnableConfigurationProperties
@ConfigurationProperties("bibsys")
//@Validated
@Data
public class YAMLConfig {

    private String name;
    private String environment;
    private List<String> servers = new ArrayList<>();
}
