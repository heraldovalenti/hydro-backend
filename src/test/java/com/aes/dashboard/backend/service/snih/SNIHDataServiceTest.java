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
        List<SNIHObservation> result = service.getLatestData("10687", SNIHDataCode.RAIN_CODE,
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
        List<SNIHObservation> result = service.getLatestData("10687", SNIHDataCode.RAIN_CODE);
        for (SNIHObservation o : result) {
            System.out.println(o.toString());
        }
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void heightStationsTest() {
//        Assertions.assertFalse(service.getLatestData("10036", SNIHDataCode.HEIGHT_CODE).isEmpty()); // FRAILE PINTADO
        Assertions.assertFalse(service.getLatestData("10646", SNIHDataCode.HEIGHT_CODE).isEmpty()); // CACHI
        Assertions.assertFalse(service.getLatestData("10706", SNIHDataCode.HEIGHT_CODE).isEmpty()); // LA MAROMA
        Assertions.assertFalse(service.getLatestData("10622", SNIHDataCode.HEIGHT_CODE).isEmpty()); // CABRA CORRAL
        Assertions.assertFalse(service.getLatestData("10626", SNIHDataCode.HEIGHT_CODE).isEmpty()); // EL TUNAL
        Assertions.assertFalse(service.getLatestData("10684", SNIHDataCode.HEIGHT_CODE).isEmpty()); // FINCA AGROPECUARIA
        Assertions.assertFalse(service.getLatestData("10686", SNIHDataCode.HEIGHT_CODE).isEmpty()); // FINCA AGROPECUARIA
        Assertions.assertFalse(service.getLatestData("10695", SNIHDataCode.HEIGHT_CODE).isEmpty()); // EL QUEBRACHAL
        Assertions.assertFalse(service.getLatestData("10696", SNIHDataCode.HEIGHT_CODE).isEmpty()); // EL QUEBRACHAL
        Assertions.assertFalse(service.getLatestData("10699", SNIHDataCode.HEIGHT_CODE).isEmpty()); // MACAPILLO
        Assertions.assertFalse(service.getLatestData("10815", SNIHDataCode.HEIGHT_CODE).isEmpty()); // CANAL DE DIOS
        Assertions.assertFalse(service.getLatestData("10611", SNIHDataCode.HEIGHT_CODE).isEmpty()); // LA PUNILLA
        Assertions.assertFalse(service.getLatestData("10705", SNIHDataCode.HEIGHT_CODE).isEmpty()); // DESEMBOCADURA

        // no current data
        Assertions.assertTrue(service.getLatestData("10701", SNIHDataCode.HEIGHT_CODE).isEmpty()); // EL ENCON
    }



}