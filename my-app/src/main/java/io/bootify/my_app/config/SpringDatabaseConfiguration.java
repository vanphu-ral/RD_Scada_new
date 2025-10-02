package io.bootify.my_app.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "io.bootify.my_app.domain")
@EnableJpaRepositories(
    transactionManagerRef = "springTransactionManager",
    entityManagerFactoryRef = "springEntityManagerFactory",
    basePackages = "io.bootify.my_app.repos"
)
public class SpringDatabaseConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties partner2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource springDataSource() {
        return partner2DataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "springEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        LocalContainerEntityManagerFactoryBean emf = builder
            .dataSource(springDataSource())
            .packages("io.bootify.my_app.domain")
            .persistenceUnit("spring")
            .build();
        emf.setJpaProperties(properties);
        return emf;
    }

    @Bean(name = "springTransactionManager")
    public JpaTransactionManager db2TransactionManager(@Qualifier("springEntityManagerFactory") final EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}

