package com.aes.dashboard.backend.service.rp5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.aes.dashboard.backend.config.GlobalConfigs.SALTA_ZONE_ID;
import static org.junit.jupiter.api.Assertions.*;

class RP5RowTest {

    @ParameterizedTest
    @CsvSource({"enero,1","febrero,2","marzo,3", "abril,4", "mayo,5", "junio,6",
    "julio,7", "agosto,8", "septiembre,9", "octubre,10", "noviembre,11", "diciembre,12"})
    void getDateTimeMonthsTest(String monthString, int monthNumber) {
        RP5Row rp5Row = new RP5Row("2024","01",monthString,"09","1.0","24");
        LocalDateTime expected = LocalDateTime.of(2024, monthNumber, 1, 12, 0);
        Assertions.assertEquals(expected, rp5Row.getDateTime());
    }

    @Test
    void hasDataTest() {
        Assertions.assertTrue(
                new RP5Row("2024","01","enero","09","1.0","24").hasData());
        Assertions.assertTrue(
                new RP5Row("2024","01","enero","09","0.1","6").hasData());
        Assertions.assertTrue(
                new RP5Row("2024","01","enero","09","","").hasData());
        Assertions.assertFalse(
                new RP5Row("2024","01","enero","08","","").hasData());
    }

    @Test
    void isValidPeriod() {
        Assertions.assertTrue(
            new RP5Row("2024","01","enero","09","1.0","24").isValidPeriod());
        Assertions.assertTrue(
                new RP5Row("2024","01","enero","09","1.0","6").isValidPeriod());
        Assertions.assertTrue(
                new RP5Row("2024","01","enero","09","1.0","").isValidPeriod());
        Assertions.assertFalse(
                new RP5Row("2024","01","enero","08","1.0","").isValidPeriod());
    }

    @Test
    void isValidRain() {
        Assertions.assertTrue(
                new RP5Row("2024","01","enero","09","1.0","24").isValidRain());
        Assertions.assertTrue(
                new RP5Row("2024","01","enero","09","1.1","6").isValidRain());
        Assertions.assertTrue(
                new RP5Row("2024","01","enero","09","Sin precipitación","24").isValidRain());
        Assertions.assertTrue(
                new RP5Row("2024","01","enero","09","Rastros","24").isValidRain());
        Assertions.assertFalse(
                new RP5Row("2024","01","enero","09","","").isValidRain());
        Assertions.assertFalse(
                new RP5Row("2024","01","enero","09","asdf","").isValidRain());
    }

    @Test
    void getPeriod() {
        Assertions.assertEquals(24,
                new RP5Row("2024","01","enero","09","1.0","24").getPeriod());
        Assertions.assertEquals(6,
                new RP5Row("2024","01","enero","09","1.0","6").getPeriod());
        // periodo de 24 horas cuando la hora es 9:00am por defecto
        Assertions.assertEquals(24,
                new RP5Row("2024","01","enero","09","1.0","").getPeriod());

        Assertions.assertEquals(0,
                new RP5Row("2024","01","enero","08","1.0","").getPeriod());
    }

    @Test
    void getRain() {
        Assertions.assertEquals(0.0,
                new RP5Row("2024","01","enero","09","Sin precipitación","24").getRain());
        Assertions.assertEquals(0.0,
                new RP5Row("2024","01","enero","09","Rastros","24").getRain());
        Assertions.assertEquals(1.0,
                new RP5Row("2024","01","enero","09","1.0","24").getRain());
        Assertions.assertEquals(1.1,
                new RP5Row("2024","01","enero","09","1.1","24").getRain());
    }

    @Test
    void is24HourPeriod() {
        Assertions.assertTrue(
                new RP5Row("2024","01","enero","09","1.0","24").is24HourPeriod());
        Assertions.assertFalse(
                new RP5Row("2024","01","enero","09","1.0","6").is24HourPeriod());
    }
}