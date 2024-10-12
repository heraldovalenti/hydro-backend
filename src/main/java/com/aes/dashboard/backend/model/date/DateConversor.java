package com.aes.dashboard.backend.model.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.aes.dashboard.backend.config.GlobalConfigs.SALTA_ZONE_ID;
import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

public class DateConversor {

    public static LocalDateTime toUTCLocalDateTime(ZonedDateTime date) {
        return date.withZoneSameInstant(UTCZoneId()).toLocalDateTime();
    }

    public static ZoneId UTCZoneId() {
        return ZoneId.of(UTC_ZONE_ID);
    }

    public static ZoneId saltaZoneId() {
        return ZoneId.of(SALTA_ZONE_ID);
    }
}
