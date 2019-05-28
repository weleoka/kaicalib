package se.ltu.kaicalib.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import se.ltu.kaicalib.KaicalibApplication;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Class implementing application configuration from yaml
 *
 * Implemented due to multiple data sources requiring dedicated configurations
 * not auto-configured by Spring.
 *
 * @author
 */
@Component
public class KaicalibYamlConfig {

    @Component
    @Validated
    @ConfigurationProperties//(prefix = "kaicalib.account")//("kaicalib.account.jpa")
    public static class AccountJpaProperties {

        @NotEmpty
        @Value("${kaicalib.account.jpa.ddl_auto}")
        private String ddl_auto;

        @NotEmpty
        @Value("${kaicalib.account.jpa.dialect}")
        private String dialect;

        //@NotEmpty
        @Value("${kaicalib.account.jpa.storage_engine}")
        private String storage_engine;

        @NotNull
        @Value("${kaicalib.account.load_initial}")
        private boolean load_initial;

        public Map<String, Object> getJpaProperties() {
            Map<String, Object> props = new HashMap<>();
            props.put("hibernate.hbm2ddl.auto", this.ddl_auto);
            props.put("hibernate.dialect",  this.dialect);
            props.put("hibernate.dialect.storage_engine",  this.storage_engine);

            return props;
        }

        public boolean getLoad_initial() {
            return load_initial;
        }
    }

    @Component
    @Validated
    @ConfigurationProperties//("kaicalib.core.jpa")
    public static class CoreJpaProperties {

        @NotEmpty
        @Value("${kaicalib.core.jpa.ddl_auto}")
        private String ddl_auto;

        @NotEmpty
        @Value("${kaicalib.core.jpa.dialect}")
        private String dialect;

        //@NotEmpty
        @Value("${kaicalib.core.jpa.storage_engine}")
        private String storage_engine;

        @NotNull
        @Value("${kaicalib.core.load_initial}")
        private boolean load_initial;

        public Map<String, Object> getJpaProperties() {
            Map<String, Object> props = new HashMap<>();
            props.put("hibernate.hbm2ddl.auto", this.ddl_auto);
            props.put("hibernate.dialect",  this.dialect);
            props.put("hibernate.dialect.storage_engine",  this.storage_engine);

            return props;
        }

        public boolean getLoad_initial() {
            return load_initial;
        }
    }
}
