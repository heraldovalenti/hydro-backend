package com.aes.dashboard.backend.service.aesLatestData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AESDataServiceTest {

    @Autowired
    private AESDataService service;

    @Test
    public void basicTest() {
        List<DataItem> result = service.getLatestData(true);
        for(DataItem item : result) {
            System.out.println(item.toString());
        }
        Assertions.assertEquals(341, result.size());
    }


}