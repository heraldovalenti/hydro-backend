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
    public void eeasalta_cerrillosStationTest() {
        List<INTAAnteriorDataItem> result = intaAnteriorDataService.getObservationData("eeasalta_cerrillos");
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void eeasalta_lavinaStationTest() {
        List<INTAAnteriorDataItem> result = intaAnteriorDataService.getObservationData("eeasalta_lavina");
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void eeasalta_abrasolStationTest() {
        List<INTAAnteriorDataItem> result = intaAnteriorDataService.getObservationData("eeasalta_abrasol");
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void eeasalta_3167StationTest() {
        List<INTAAnteriorDataItem> result = intaAnteriorDataService.getObservationData("eeasalta_3167");
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void eeasalta_3169StationTest() {
        List<INTAAnteriorDataItem> result = intaAnteriorDataService.getObservationData("eeasalta_3169");
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void eeasalta_elbreteStationTest() {
        List<INTAAnteriorDataItem> result = intaAnteriorDataService.getObservationData("eeasalta_elbrete");
        Assertions.assertFalse(result.isEmpty());
    }

}