package se.ltu.kaicalib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import se.ltu.kaicalib.core.config.YAMLConfig;

import javax.annotation.PostConstruct;


@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class KaicalibApplication {

    private static Logger logger = LoggerFactory.getLogger(KaicalibApplication.class);

    @Autowired
    private YAMLConfig myConfig;

    public static void main(String[] args) {
        logger.debug("STARTING APPLICATION!");
        SpringApplication.run(KaicalibApplication.class, args); // "--debug");
    }

    @PostConstruct
    private void init(){
        logger.info("Spring Boot - @ConfigurationProperties annotation example");
        logger.info(myConfig.toString());
    }

}
