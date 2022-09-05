package com.aes.dashboard.backend.service.weatherlinkData;


import com.aes.dashboard.backend.service.AppConfigService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class WeatherlinkDataServiceTests {

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private WeatherlinkDataService weatherlinkDataService;

    @ParameterizedTest
    @ValueSource(strings = {
            "bd59bd4c-cbec-40e1-a2d4-36f5feea3475",
            "351e2b80-75a7-419d-a081-d8ab6ddd3051",
            "70d018b1-cfae-4ca6-b9b2-598bcd796cb3",
            "a9e1a996-c5ea-4ae1-a582-b46c64403c99",
            "f0805dcb-4b23-4e81-9a73-5e31c5e212dc"
    })
    void testStationObservationsWithTS(String stationId) {
        LocalDateTime now = LocalDateTime.now();
        Optional<WeatherlinkResult> opt = weatherlinkDataService.getObservationData(stationId, now);
        Assertions.assertTrue(opt.isPresent());
        WeatherlinkResult result = opt.get();
        LocalDateTime resultDate = result.getObservationTime();
        Assertions.assertTrue(now.minusMinutes(2).isBefore(resultDate));
        Assertions.assertTrue(now.plusMinutes(2).isAfter(resultDate));
        Assertions.assertEquals(0.0, result.getObservationValue());
    }

}
