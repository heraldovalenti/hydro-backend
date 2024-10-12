package com.aes.dashboard.backend.model.date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.aes.dashboard.backend.config.GlobalConfigs.PWS_WEATHER_DATE_TIME_FORMAT;
import static com.aes.dashboard.backend.model.date.DateConversor.*;

class DateConversorTest {
    @Test
    void testZonedToLocal() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PWS_WEATHER_DATE_TIME_FORMAT);
        ZonedDateTime date3_28pm = ZonedDateTime.parse("2024-10-10T15:28:00-03:00", dtf);
        LocalDateTime result = toUTCLocalDateTime(date3_28pm);
        Assertions.assertEquals(18, result.getHour());
    }
}