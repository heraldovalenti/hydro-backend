package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.Observation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class AesRainAccumulatorTest {

    @Test
    public void basicAesAcumTest() {
        List<Observation> observations = List.of(
                observation(1, 1.0),
                observation(2, 2.0),
                observation(3, 3.0),
                observation(4, 4.0)
        );
        AesRainAccumulator aesRainAccumulator = new AesRainAccumulator();
        double result = aesRainAccumulator.accumulate(observations);
        Assertions.assertEquals(3, result);
    }

    @Test
    public void mixedObservationsTest() {
        List<Observation> observations = List.of(
                observation(1, 0.3),
                observation(2, 0.2),
                observation(3, 0.01),
                observation(4, 0.1)
        );
        AesRainAccumulator aesRainAccumulator = new AesRainAccumulator();
        double result = aesRainAccumulator.accumulate(observations);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void oneObservationTest() {
        List<Observation> observations = List.of(
                observation(1, 1.0)
        );
        AesRainAccumulator aesRainAccumulator = new AesRainAccumulator();
        double result = aesRainAccumulator.accumulate(observations);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void allSameObservationTest() {
        List<Observation> observations = List.of(
                observation(1, 1.0),
                observation(2, 1.0),
                observation(3, 1.0)
        );
        AesRainAccumulator aesRainAccumulator = new AesRainAccumulator();
        double result = aesRainAccumulator.accumulate(observations);
        Assertions.assertEquals(0, result);
    }

    private Observation observation(int position, double value) {
        Observation o = new Observation();
        o.setValue(value);
        o.setTime(LocalDateTime.of(2020, 1, 1, 0 , position));
        return o;
    }

}