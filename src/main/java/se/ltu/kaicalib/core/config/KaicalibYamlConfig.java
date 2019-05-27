package se.ltu.kaicalib.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

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
    @ConfigurationProperties("kaicalib.account.jpa")
    public static class AccountJpaProperties {

        @NotEmpty
        @Value("${kaicalib.account.jpa.ddl_auto}")
        private String ddl_auto;

        @NotEmpty
        @Value("${kaicalib.account.jpa.dialect}")
        private String dialect;

        @NotNull
        @Value("${kaicalib.account.load_initial}")
        private boolean load_initial;

        public Map<String, Object> getJpaProperties() {
            Map<String, Object> props = new HashMap<>();
            props.put("hibernate.hbm2ddl.auto", this.ddl_auto);
            props.put("hibernate.dialect",  this.dialect);

            return props;
        }

        public boolean getLoad_initial() {
            return load_initial;
        }
    }

    @Component
    @Validated
    @ConfigurationProperties("kaicalib.core.jpa")
    public static class CoreJpaProperties {

        @NotEmpty
        @Value("${kaicalib.core.jpa.ddl_auto}")
        private String ddl_auto;

        @NotEmpty
        @Value("${kaicalib.core.jpa.dialect}")
        private String dialect;

        @NotNull
        @Value("${kaicalib.core.load_initial}")
        private boolean load_initial;

        public Map<String, Object> getJpaProperties() {
            Map<String, Object> props = new HashMap<>();
            props.put("hibernate.hbm2ddl.auto", this.ddl_auto);
            props.put("hibernate.dialect",  this.dialect);
            // org.hibernate.dialect.MySQL5InnoDBDialect //MySQL5Dialect.

            return props;
        }

        public boolean getLoad_initial() {
            return load_initial;
        }
    }
}
