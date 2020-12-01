package com.aes.dashboard.backend.service.intaData;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class INTADataServiceTest {

    @Autowired
    private INTADataService intaDataService;

    @Test
    public void testRetrieveVars() {
        String vars = intaDataService.retrieveStationBaseUrl();
        System.out.println(vars);
    }

    @Test
    public void seclantasStationTest() {
        List<INTADataItem> result = intaDataService.getObservationData("352");
        Assertions.assertEquals(135, result.size());
    }

}