package se.ltu.kaicalib.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import se.ltu.kaicalib.core.config.KaicalibYamlConfig;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


/**
 * Multiple datasources require disabling Spring's DataSourceAutoConfiguration.class so
 * scripts have to be manually loaded.
 *
 * This is for development and testing purposes.
 *
 * Just comment out the Bean annotation and it will stop the loading of sample data.
 *
 *
 * @author
 */
@Component
public class DataLoader {

    private DataSource coreDataSource;
    private DataSource accountsDataSource;

    private KaicalibYamlConfig.CoreJpaProperties coreJpaProperties;
    private KaicalibYamlConfig.AccountJpaProperties accountJpaProperties;

    @Autowired
    public DataLoader(
        DataSource coreDataSource,
        @Qualifier("accountsDataSource")
            DataSource accountsDataSource,
        KaicalibYamlConfig.CoreJpaProperties coreJpaProperties,
        KaicalibYamlConfig.AccountJpaProperties accountJpaProperties)
    {
        this.coreDataSource = coreDataSource;
        this.accountsDataSource = accountsDataSource;
        this.coreJpaProperties = coreJpaProperties;
        this.accountJpaProperties = accountJpaProperties;
    }

    @PostConstruct
    @Bean(name="CoreDataSourceInitializer")
    public DataSourceInitializer coreDataSourceInitializer() { // todo DRY
        if (!coreJpaProperties.getLoad_initial()) { return null; }
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("data-core-mysql.sql"));
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(coreDataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);

        return dataSourceInitializer;
    }

    @PostConstruct
    @Bean(name="AccountDataSourceInitializer")
    public DataSourceInitializer accountDataSourceInitializer() { // todo DRY
        if (!accountJpaProperties.getLoad_initial()) { return null; }
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("data-account-mysql.sql"));
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(accountsDataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);

        return dataSourceInitializer;
    }


/*
    //method invoked after the startup
    @PostConstruct
    public void loadData() {
        accountDataSourceInitializer().

    }
*/
    //method invoked during the shutdown
    //@PreDestroy
    //public void removeData() {userRepository.deleteAll();}
}
