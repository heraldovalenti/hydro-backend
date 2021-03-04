package com.aes.dashboard.backend.service.snih;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class SNIHDataServiceTest {

    @Autowired
    private SNIHDataService service;

    @Test
    public void basicTest() {
        List<SNIHObservation> result = service.getLatestData(10687, SNIHDataService.RAIN_CODE,
                LocalDateTime.of(2021, 3, 1,0,0),
                LocalDateTime.of(2021, 3, 1,0,0));
        Assertions.assertEquals(50, result.size());
        for (SNIHObservation o : result) {
            System.out.println(o.toString());
        }
    }

    @Test
    public void nowBasicTest() {
        List<SNIHObservation> result = service.getLatestData(10706, SNIHDataService.RAIN_CODE);
        for (SNIHObservation o : result) {
            System.out.println(o.toString());
        }
    }

}