package com.aes.dashboard.backend.service.intaData;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class INTASiga2DataServiceTest {

    @Autowired
    private INTASiga2DataService intaSiga2DataService;

    @Test
    public void testRetrieveVars() {
        String vars = intaSiga2DataService.retrieveStationBaseUrl();
        System.out.println(vars);
    }

    @Test
    public void seclantasStationTest() {
        List<INTASiga2DataItem> result = intaSiga2DataService.getObservationData("352");
        Assertions.assertEquals(135, result.size());
    }

    @Test
    public void palmaSolaStationTest() {
        List<INTASiga2DataItem> result = intaSiga2DataService.getObservationData("395");
        Assertions.assertEquals(0, result.size());
    }

}