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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
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
    entityManagerFactoryRef = "coreEntityManagerFactory",
    transactionManagerRef = "coreTransactionManager",
    basePackages = {"se.ltu.kaicalib.core"}
)
public class CoreDbConfig {

    private KaicalibYamlConfig.CoreJpaProperties coreJpaProperties;

    @Autowired
    public CoreDbConfig(
        KaicalibYamlConfig.CoreJpaProperties coreJpaProperties
    ) {
        this.coreJpaProperties = coreJpaProperties;
    }


    @Bean(name="coreDatasourceProperties")
    @ConfigurationProperties("datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }


    @Primary
    @Bean(name = "coreDataSource")
    @ConfigurationProperties("datasource.configuration")
    public DataSource coreDataSource() {
        DataSourceProperties dsp = dataSourceProperties();
        return dsp.initializeDataSourceBuilder()
            .type(HikariDataSource.class).build();
    }


/*    @Bean(name="coreTransactionManager")
    DataSourceTransactionManager transactionManager(DataSource datasource) {
        DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
        return txm;
    }*/

    @Primary
    @Bean(name="coreTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(coreEntityManagerFactory(builder).getObject());
        tm.setDataSource(coreDataSource());
        return tm;
    }

    @Primary
    @Bean(name = "coreEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean coreEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(coreDataSource())
            .packages(new String[] {"se.ltu.kaicalib.core"})
            .persistenceUnit("kaicalib-core")
            .properties(coreJpaProperties.getJpaProperties())
            //.setPersistenceProviderClass(org.hibernate.ejb.HibernatePersistence.class);
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
