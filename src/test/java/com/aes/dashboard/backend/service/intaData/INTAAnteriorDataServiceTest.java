package com.aes.dashboard.backend.service.intaData;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class INTAAnteriorDataServiceTest {

    @Autowired
    private INTAAnteriorDataService intaAnteriorDataService;

    @Test
    public void ema_ollerosStationTest() {
        List<INTAAnteriorDataItem> result = intaAnteriorDataService.getObservationData("ema_olleros");
        Assertions.assertFalse(result.isEmpty());

    }

    @Test
    public void ema_bicentenarioStationTest() {
        List<INTAAnteriorDataItem> result = intaAnteriorDataService.getObservationData("ema_bicentenario");
        Assertions.assertFalse(result.isEmpty());

    }

    @Test
    public void ema_spmfStationTest() {
        List<INTAAnteriorDataItem> result = intaAnteriorDataService.getObservationData("ema_spmf");
        Assertions.assertFalse(result.isEmpty());

    }

    @Test
    public void ema_lavinaStationTest() {
        List<INTAAnteriorDataItem> result = intaAnteriorDataService.getObservationData("ema_lavina");
        Assertions.assertFalse(result.isEmpty());

    }

    @Test
    public void ema_desdeelsurStationTest() {
        List<INTAAnteriorDataItem> result = intaAnteriorDataService.getObservationData("ema_desdeelsur");
        Assertions.assertFalse(result.isEmpty());

    }

}