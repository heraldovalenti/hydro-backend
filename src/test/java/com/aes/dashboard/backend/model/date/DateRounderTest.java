package com.aes.dashboard.backend.model.date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class DateRounderTest {

    @Test
    public void roundDateTests() {
        DateRounder rounder = new DateRounder(5);
        // dateRoundMinutes: 5;
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                rounder.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0))
        );
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 0, 5, 0),
                rounder.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 0, 1))
        );
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 0, 5, 0),
                rounder.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 1, 0))
        );
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 0, 5, 0),
                rounder.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 0, 59))
        );
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 0, 5, 0),
                rounder.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 4, 59))
        );
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 0, 45, 0),
                rounder.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 43, 33))
        );
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 1, 0, 0),
                rounder.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 59, 59))
        );
    }
}