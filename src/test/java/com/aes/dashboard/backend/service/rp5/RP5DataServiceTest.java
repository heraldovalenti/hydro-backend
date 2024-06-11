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
        LocalDateTime now = LocalDateTime.now();
        Assertions.assertEquals(now.getDayOfYear(), result.get(0).getDateTime().getDayOfYear());
    }

    @Test
    void getEOYObservationData() {
        LocalDateTime ldt = LocalDateTime.of(2024, 1, 15, 0, 0); // 15.01.2024
        List<RP5Row> result = service.getObservationData(stationId, ldt, RP5Period.MONTH, 0);
        Assertions.assertEquals(110, result.size());
        Assertions.assertEquals(
                LocalDateTime.of(2024, 1, 16, 0, 0),
                result.get(0).getDateTime());
        Assertions.assertEquals(
                LocalDateTime.of(2023, 12, 17, 6, 0),
                result.get(109).getDateTime());
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