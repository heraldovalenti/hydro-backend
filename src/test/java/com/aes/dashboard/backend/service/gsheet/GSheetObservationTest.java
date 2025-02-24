package com.aes.dashboard.backend.service.gsheet;

import com.aes.dashboard.backend.config.GlobalConfigs;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.aes.dashboard.backend.model.date.DateConversor.toUTCLocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class GSheetObservationTest {

    @Test
    void argentinaMidnightShouldBe3AMInUTC() {
        ZonedDateTime midnight = ZonedDateTime.of(
                LocalDateTime.of(2025,1,1,0,0),
                ZoneId.of(GlobalConfigs.SALTA_ZONE_ID));
        LocalDateTime threeAM = LocalDateTime.of(2025,1,1,3,0);
        GSheetObservation observation = new GSheetObservation();
        observation.setDate(midnight);
        assertEquals(threeAM, toUTCLocalDateTime(observation.getDate()));
    }
}