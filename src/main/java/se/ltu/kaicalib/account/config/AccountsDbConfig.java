package se.ltu.kaicalib.account.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "accountsEntityManagerFactory",
    transactionManagerRef = "accountsTransactionManager",
    basePackages = {"se.ltu.kaicalib.account"}
)
public class AccountsDbConfig {

    @Autowired
    private DataSource mainDataSource;

    @Bean(name="accountsDatasourceProperties")
    @ConfigurationProperties("accounts-datasource")
    public DataSourceProperties accountsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "accountsDataSource")
    @ConfigurationProperties("accounts-datasource.configuration")
    public DataSource accountsDataSource() {
        DataSourceProperties dsp = accountsDataSourceProperties();

        if (dsp.getUrl() != null) {
            DataSource ds = dsp.initializeDataSourceBuilder().type(HikariDataSource.class).build();

            return ds;
        }

        return mainDataSource;
    }

/*
    @Bean(name = "accountsDataSource")
    @ConfigurationProperties(prefix = "accounts-datasource.configuration")
    public DataSource accountsDataSource() {
        return DataSourceBuilder.create().build();
    }
*/

/*
    @Bean(name="accountsTransactionManager")
    @Autowired
    DataSourceTransactionManager accountsTransactionManager(@Qualifier("accountsDataSource") DataSource datasource) {
        DataSourceTransactionManager txm  = new DataSourceTransactionManager(datasource);
        return txm;
    }
*/

    @Bean(name="accountsTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(accountsEntityManagerFactory(builder).getObject());
        tm.setDataSource(accountsDataSource());
        return tm;
    }

    /**
     * These are here because when not autoconfiguring with Spring then the
     * application.yml is not parsed for settings, only the actual datasource parameters are read.
     *
     * It's a big pickle due to parts of Spring not being up to date with YAML as of 20 May 2019.
     *
     * todo Move out to application.yml when time allows
     *
     * @return properties
     */
    //@ConfigurationProperties("accounts-datasource.configuration")
    private Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        // naming strategy to put underscores instead of camel case
        // as per auto JPA Configuration
        //props.put("hibernate.ejb.naming_strategy", new SpringNamingStrategy());
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return props;
    }

    @Bean(name = "accountsEntityManagerFactory")
    @PersistenceContext(unitName = "bibsys-account")
    public LocalContainerEntityManagerFactoryBean accountsEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(accountsDataSource())
            .packages(new String[] {"se.ltu.kaicalib.account"})
            .persistenceUnit("bibsys-account")
            .properties(jpaProperties())
            .build();
    }
}


/*
    @Bean(name = "accountsEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(accountsDataSource());
        em.setPackagesToScan(new String[] {"se.ltu.kaicalib.account"});
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalJpaProperties());
        em.setPersistenceUnitName("bibsys-account");

        return em;
    }*/
