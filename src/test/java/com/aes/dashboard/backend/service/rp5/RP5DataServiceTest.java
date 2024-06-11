package com.aes.dashboard.backend.service.rp5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class RP5DataServiceTest {


    private static final String stationId = "Archivo_de_tiempo_en_Salta_(aeropuerto)";
    @Autowired
    private RP5DataService service;

    @Test
    void getTodayObservationData() {
        List<RP5Row> result = service.getObservationData(stationId);
        Assertions.assertEquals(7, result.size());
    }

    @Test
    void getEOYObservationData() {
        LocalDateTime ldt = LocalDateTime.of(2024, 1, 15, 0, 0); // 15.01.2024
        List<RP5Row> result = service.getObservationData(stationId, ldt, RP5Period.MONTH, 0);
        Assertions.assertEquals(110, result.size());
    }

    @Test
    void getEOYObservationDataWithLimit() {
        LocalDateTime ldt = LocalDateTime.of(2024, 1, 15, 0, 0); // 15.01.2024
        List<RP5Row> result = service.getObservationData(stationId, ldt, RP5Period.MONTH, 48);
        Assertions.assertEquals(11, result.size());
    }

    @Test
    void getWeekObservationData() {
        LocalDateTime ldt = LocalDateTime.of(2024, 1, 15, 0, 0); // 15.01.2024
        List<RP5Row> result = service.getObservationData(stationId, ldt, RP5Period.WEEK, 0);
        Assertions.assertEquals(27, result.size());
    }
}