package com.laolu.shipbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author wanyi.lu
 * @date Created in 2023/1/28 16:35
 */
@Configuration
public class DataSourceConfig {
    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.master")
    public DataSource firstDataSource() {
        return DataSourceBuilder.create().build();
    }
}
