package com.laolu.shipbackend;


import com.laolu.shipbackend.service.BagService;
import com.laolu.shipbackend.service.StarStoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class ShipBackendApplicationTests {
    @Autowired
    StarStoreService starStoreService;
    @Autowired
    BagService bagService;
    @Test
    @Transactional
    void contextLoads() {
        System.out.println(bagService.getItems(1).getData());
    }

}
