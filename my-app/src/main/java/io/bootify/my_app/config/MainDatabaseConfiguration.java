package io.bootify.my_app.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "io.bootify.planning.domain")
@EnableJpaRepositories(
    transactionManagerRef = "mainTransactionManager",
    entityManagerFactoryRef = "mainEntityManagerFactory",
    basePackages = "io.bootify.planning.repos"
)
public class MainDatabaseConfiguration {

    @Bean
    @ConfigurationProperties("main.datasource")
    public DataSourceProperties partnerDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("main.datasource")
    public DataSource partnerDataSource() {
        return partnerDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "mainEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

        LocalContainerEntityManagerFactoryBean emf = builder
            .dataSource(partnerDataSource())
            .packages("io.bootify.planning.domain")
            .persistenceUnit("main")
            .build();
        emf.setJpaProperties(properties);
        return emf;
    }

    @Bean(name = "mainTransactionManager")
    public JpaTransactionManager db2TransactionManager(@Qualifier("mainEntityManagerFactory") final EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
    @Bean
    @Primary
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                new HashMap<String, Object>(),
                null
        );

    }
}
