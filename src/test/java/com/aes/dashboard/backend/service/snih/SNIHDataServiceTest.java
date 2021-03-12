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
    public void guemesRainTest() {
        List<SNIHObservation> result = service.getLatestData(10687, SNIHDataService.RAIN_CODE,
                LocalDateTime.of(2021, 3, 1,0,0),
                LocalDateTime.of(2021, 3, 1,0,0));
        Assertions.assertEquals(50, result.size());
        for (SNIHObservation o : result) {
            System.out.println(o.toString());
        }
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void guemesNowRainTest() {
        List<SNIHObservation> result = service.getLatestData(10687, SNIHDataService.RAIN_CODE);
        for (SNIHObservation o : result) {
            System.out.println(o.toString());
        }
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void heightStationsTest() {
        Assertions.assertFalse(service.getLatestData(10036, SNIHDataService.HEIGHT_CODE).isEmpty()); //FRAILE PINTADO
        Assertions.assertFalse(service.getLatestData(10646, SNIHDataService.HEIGHT_CODE).isEmpty()); //CACHI
        Assertions.assertFalse(service.getLatestData(10706, SNIHDataService.HEIGHT_CODE).isEmpty()); //LA MAROMA
        Assertions.assertFalse(service.getLatestData(10622, SNIHDataService.HEIGHT_CODE).isEmpty()); //CABRA CORRAL
        Assertions.assertFalse(service.getLatestData(10626, SNIHDataService.HEIGHT_CODE).isEmpty()); // EL TUNAL
        Assertions.assertFalse(service.getLatestData(10684, SNIHDataService.HEIGHT_CODE).isEmpty()); // FINCA AGROPECUARIA
        Assertions.assertFalse(service.getLatestData(10686, SNIHDataService.HEIGHT_CODE).isEmpty()); // FINCA AGROPECUARIA
        Assertions.assertFalse(service.getLatestData(10695, SNIHDataService.HEIGHT_CODE).isEmpty()); // EL QUEBRACHAL
        Assertions.assertFalse(service.getLatestData(10696, SNIHDataService.HEIGHT_CODE).isEmpty()); // EL QUEBRACHAL
        Assertions.assertFalse(service.getLatestData(10699, SNIHDataService.HEIGHT_CODE).isEmpty()); // MACAPILLO
        Assertions.assertFalse(service.getLatestData(10815, SNIHDataService.HEIGHT_CODE).isEmpty()); // CANAL DE DIOS
        Assertions.assertFalse(service.getLatestData(10611, SNIHDataService.HEIGHT_CODE).isEmpty()); // LA PUNILLA

        // no current data
        Assertions.assertTrue(service.getLatestData(10701, SNIHDataService.HEIGHT_CODE).isEmpty()); //EL ENCON
        Assertions.assertTrue(service.getLatestData(10705, SNIHDataService.HEIGHT_CODE).isEmpty()); // DESEMBOCADURA
    }



}