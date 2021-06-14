package com.aes.dashboard.backend.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ObservationTest {

    @Test
    public void latest() {
        Observation o1 = new Observation(),
                o2 = new Observation();
        o1.setTime(LocalDateTime.of(2020, 1, 1, 0, 0));
        o2.setTime(LocalDateTime.of(2020, 1, 2, 0, 0));
        assertEquals(o2, o1.latest(o2));
        assertEquals(o2, o2.latest(o1));
        assertEquals(o1, o1.latest(null));
        assertEquals(o2, o2.latest(null));
    }

}