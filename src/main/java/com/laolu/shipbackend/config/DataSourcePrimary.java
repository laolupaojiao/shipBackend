package com.laolu.shipbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author wanyi.lu
 * @date Created in 2023/1/28 16:33
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.laolu.shipbackend.jpa",
        entityManagerFactoryRef = "entityManagerFactoryBeanOne",
        transactionManagerRef = "platformTransactionManagerOne")
public class DataSourcePrimary {

    @Resource(name = "primaryDataSource")
    DataSource primaryDataSource;

    @Autowired
    JpaProperties jpaProperties;

    @Resource
    HibernateProperties hibernateProperties;


    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBeanOne(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(primaryDataSource)
                .properties(hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(),new HibernateSettings()))
                .packages("com.laolu.shipbackend.jpa.entity")
                .persistenceUnit("pu1")
                .build();
    }

    @Bean
    @Primary
    PlatformTransactionManager platformTransactionManagerOne(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean factoryOne
                = entityManagerFactoryBeanOne(builder);
        return new JpaTransactionManager(factoryOne.getObject());
    }
}
