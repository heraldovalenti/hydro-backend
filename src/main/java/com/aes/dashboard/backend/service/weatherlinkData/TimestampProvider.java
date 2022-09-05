package com.aes.dashboard.backend.service.weatherlinkData;

import com.aes.dashboard.backend.config.GlobalConfigs;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimestampProvider {

    public static Long getTimestamp(LocalDateTime date) {
        ZoneId zoneId = ZoneId.of(GlobalConfigs.SALTA_ZONE_ID);
        long epochSeconds = date.atZone(zoneId).toEpochSecond();
        return epochSeconds;
    }

    public static LocalDateTime getLocalDate(Long epochSeconds) {
        ZoneId zoneId = ZoneId.of(GlobalConfigs.SALTA_ZONE_ID);
        LocalDateTime ldt = Instant.ofEpochSecond(epochSeconds).atZone(zoneId).toLocalDateTime();
        return ldt;
    }
}
