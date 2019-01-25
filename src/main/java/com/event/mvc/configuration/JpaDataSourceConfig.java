package com.event.mvc.configuration;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@PropertySource("classpath:jdbc.properties")
@ComponentScan(basePackages = {"com.event.mvc.entity"})
@EnableTransactionManagement
public class JpaDataSourceConfig {
    @Value("${database.h2.url}")
    private String url;

    @Bean
    public DataSource dataSource() {
        JdbcDataSource h2DataSource = new JdbcDataSource();
        h2DataSource.setURL(url);

        return h2DataSource;
    }

    private Properties hibernateProperties() throws IOException {
        InputStream resource = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
        Properties properties = new Properties();
        if (resource != null) {
            properties.load(resource);
        } else throw new IOException("Cannot find a resource");
        return properties;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean("entityManager")
    public EntityManagerFactory entityManagerFactory() throws IOException {
        LocalContainerEntityManagerFactoryBean localContainer = new LocalContainerEntityManagerFactoryBean();
        localContainer.setPackagesToScan("com.event.mvc/entity");
        localContainer.setDataSource(dataSource());
        localContainer.setJpaProperties(hibernateProperties());
        localContainer.setJpaVendorAdapter(jpaVendorAdapter());
        localContainer.afterPropertiesSet();

        return localContainer.getNativeEntityManagerFactory();
    }

    @Bean
    @DependsOn("entityManager")
    public PlatformTransactionManager jpaTransactionManager() throws IOException {
        return new JpaTransactionManager(entityManagerFactory());
    }


}