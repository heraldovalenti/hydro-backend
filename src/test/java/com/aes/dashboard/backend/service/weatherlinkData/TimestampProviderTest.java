package com.aes.dashboard.backend.service.weatherlinkData;

import com.aes.dashboard.backend.config.GlobalConfigs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

class TimestampProviderTest {

    @Test
    void testDatesConversionToEpoch() {
        LocalDateTime ld = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        ZoneId zoneId = ZoneId.of(GlobalConfigs.UTC_ZONE_ID);
        long epochMilis = ld.atZone(zoneId).toEpochSecond();
        Assertions.assertEquals(0, epochMilis);
    }

    private static final long THREE_HOURS_IN_SECONDS = 3 * 60 * 60;

    @Test
    void testEpochDateToTimestamp() {
        LocalDateTime ld = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        Assertions.assertEquals(THREE_HOURS_IN_SECONDS, TimestampProvider.getTimestamp(ld));
    }

    @Test
    void testEpochPlus30SecondsDateToTimestamp() {
        LocalDateTime ld = LocalDateTime.of(1970, 1, 1, 0, 0, 30);
        Assertions.assertEquals(THREE_HOURS_IN_SECONDS + 30, TimestampProvider.getTimestamp(ld));
    }

    @Test
    void testEpochMilisToLocalDate() {
        LocalDateTime epochLdt1 = LocalDateTime.of(1970, 1, 1, 0, 0);
        LocalDateTime epochLdt2 = TimestampProvider.getLocalDate(THREE_HOURS_IN_SECONDS);
        Assertions.assertEquals(epochLdt1, epochLdt2);
    }
}