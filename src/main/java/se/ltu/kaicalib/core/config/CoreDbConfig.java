package se.ltu.kaicalib.core.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * Persistence layer configuration for the core
 * of the Kaicalib application.
 *
 * @author
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "coreTransactionManager",
    basePackages = {"se.ltu.kaicalib.core"}
)
public class CoreDbConfig {

    @Autowired
    KaicalibYamlConfig.CoreJpaProperties coreJpaProperties;


    @Bean(name="coreDatasourceProperties")
    @ConfigurationProperties("datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties("datasource.configuration")
    public DataSource dataSource() {
        DataSourceProperties dsp = dataSourceProperties();
        return dsp.initializeDataSourceBuilder()
            .type(HikariDataSource.class).build();
    }


    @Bean(name="coreTransactionManager")
    DataSourceTransactionManager transactionManager(DataSource datasource) {
        DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
        return txm;
    }


    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(dataSource())
            .packages(new String[] {"se.ltu.kaicalib.core"})
            .persistenceUnit("bibsys-core")
            .properties(coreJpaProperties.getJpaProperties())
            .build();
    }
}

/*

    */
/*==== DATASOURCE ======================== *//*

    @Bean
    @Primary
    @ConfigurationProperties("datasource")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("datasource.configuration")
    public DataSource firstDataSource() {
        return firstDataSourceProperties().initializeDataSourceBuilder()
            .type(HikariDataSource.class).build();
    }
}
*/
