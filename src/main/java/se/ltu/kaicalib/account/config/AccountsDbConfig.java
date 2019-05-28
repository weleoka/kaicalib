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
import se.ltu.kaicalib.core.config.KaicalibYamlConfig;

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
    private KaicalibYamlConfig.AccountJpaProperties accountJpaProperties;
    private DataSource coreDateSource;


    @Autowired
    public AccountsDbConfig(
        KaicalibYamlConfig.AccountJpaProperties accountJpaProperties,
        DataSource coreDateSource
    ) {
        this.accountJpaProperties = accountJpaProperties;
        this.coreDateSource = coreDateSource;
    }


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

        return coreDateSource;
    }


    @Bean(name="accountsTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(accountsEntityManagerFactory(builder).getObject());
        tm.setDataSource(accountsDataSource());
        return tm;
    }


    @Bean(name = "accountsEntityManagerFactory")
    @PersistenceContext(unitName = "bibsys-account")
    public LocalContainerEntityManagerFactoryBean accountsEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(accountsDataSource())
            .packages(new String[] {"se.ltu.kaicalib.account"})
            .persistenceUnit("kaicalib-account")
            .properties(accountJpaProperties.getJpaProperties())
            .build();
    }
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
