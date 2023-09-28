package com.aes.dashboard.backend.service.weatherlinkData;


import com.aes.dashboard.backend.service.AppConfigService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

@SpringBootTest
public class WeatherlinkDataServiceTests {

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private WeatherlinkDataService weatherlinkDataService;

    @ParameterizedTest
    @ValueSource(strings = {
            "ae2d126e-8524-427c-8757-25197f533276", // el carril
            "6ae0d38c-0f2c-46b9-8b84-263174d9f837", // moldes
            "17d6d435-6e0f-439d-a252-a474ccaa9382", // PLR 02 Cerro
            "4a824103-5e7d-467d-b6bc-8434fce4c44a", // Martillo Este
            "9c4e35eb-9121-4c27-acac-f0901d51c5db", // Metan
            "bd59bd4c-cbec-40e1-a2d4-36f5feea3475", // ERA 01 - Las Tolas
            "70d018b1-cfae-4ca6-b9b2-598bcd796cb3", // EMA Sur - Pampa Energía CTG
            "a9e1a996-c5ea-4ae1-a582-b46c64403c99", // EMA Norte - Pampa Energía CTG
            "f0805dcb-4b23-4e81-9a73-5e31c5e212dc", // EMA Nitratos Austin
            "6ba85f40-404a-4661-bcfe-93cbcac16031"  // EMA - Esc. Agrot. Payogasta

    })
    void testStationObservationsWithTS(String stationId) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of(UTC_ZONE_ID));
        Optional<WeatherlinkResult> opt = weatherlinkDataService.getObservationData(stationId, now);
        Assertions.assertTrue(opt.isPresent());
        WeatherlinkResult result = opt.get();
        LocalDateTime resultDate = result.getObservationTime();
        Assertions.assertTrue(now.minusMinutes(2).isBefore(resultDate));
        Assertions.assertTrue(now.plusMinutes(2).isAfter(resultDate));
        Assertions.assertEquals(0.0, result.getObservationValue());
    }

}
