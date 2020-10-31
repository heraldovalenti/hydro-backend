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
        List<LatestDataItem> result = service.getLatestData();
        Assertions.assertEquals(16, result.size());
        result.stream()
                .filter(item -> item.getFileName().startsWith("cachi_"))
                .forEach(item -> item.getData().stream()
                        .forEach(data -> System.out.println(data.getValue()))
                );
    }

}