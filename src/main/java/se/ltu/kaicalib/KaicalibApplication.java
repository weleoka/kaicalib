package se.ltu.kaicalib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import se.ltu.kaicalib.core.config.KaicalibYamlConfig;

import javax.annotation.PostConstruct;


@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class KaicalibApplication extends SpringBootServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(KaicalibApplication.class);

    @Autowired
    private KaicalibYamlConfig yamlConfig;


    /**
     * Used when run as JAR
     */
    public static void main(String[] args) {
        logger.debug("STARTING APPLICATION!");
        SpringApplication.run(KaicalibApplication.class, args); // "--debug");
    }


    /**
     * Used when run as WAR
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KaicalibApplication.class);
    }


    @PostConstruct
    private void init(){
        logger.info(yamlConfig.toString());
    }
}
