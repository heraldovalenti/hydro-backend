package com.aes.dashboard.backend.service.pwsWeatherData.weatherCloudData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.aes.dashboard.backend.config.GlobalConfigs.PWS_WEATHER_DATE_TIME_FORMAT;
import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

public class DateTimeFormatTest {

    @Test
    public void testFormat() {
        String date = "2024-06-05T14:59:13-03:00";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PWS_WEATHER_DATE_TIME_FORMAT);
        ZonedDateTime zdt = ZonedDateTime.parse(date, dtf);
        LocalDateTime utcDt = zdt.withZoneSameInstant(ZoneId.of(UTC_ZONE_ID)).toLocalDateTime();
        Assertions.assertEquals(59, zdt.getMinute());
        Assertions.assertEquals(13, zdt.getSecond());

        Assertions.assertEquals(14, zdt.getHour());
        Assertions.assertEquals(17, utcDt.getHour());
    }
}
