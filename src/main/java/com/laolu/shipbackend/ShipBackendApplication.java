package com.laolu.shipbackend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManager;

@SpringBootApplication
@EnableScheduling // 开启定时任务功能
@EnableWebMvc
public class ShipBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShipBackendApplication.class, args);
    }
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

}
