package com.laolu.shipbackend;


import com.laolu.shipbackend.service.StarService;
import com.laolu.shipbackend.service.StarStoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ShipBackendApplicationTests {
    @Autowired
    StarStoreService starStoreService;
    @Test
    void contextLoads() {
        System.out.println(starStoreService.getList(1));
    }

}
