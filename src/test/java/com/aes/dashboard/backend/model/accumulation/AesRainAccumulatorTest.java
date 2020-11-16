package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.Observation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class AesRainAccumulatorTest {

    @Test
    public void basicAesAcumTest() {
        List<Observation> observations = List.of(
                observation(1.0),
                observation(2.0),
                observation(3.0),
                observation(4.0)
        );
        AesRainAccumulator aesRainAccumulator = new AesRainAccumulator();
        double result = aesRainAccumulator.accumulate(observations);
        Assertions.assertEquals(6, result);
    }

    @Test
    public void oneObservationTest() {
        List<Observation> observations = List.of(
                observation(1.0)
        );
        AesRainAccumulator aesRainAccumulator = new AesRainAccumulator();
        double result = aesRainAccumulator.accumulate(observations);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void allSameObservationTest() {
        List<Observation> observations = List.of(
                observation(1.0),
                observation(1.0),
                observation(1.0)
        );
        AesRainAccumulator aesRainAccumulator = new AesRainAccumulator();
        double result = aesRainAccumulator.accumulate(observations);
        Assertions.assertEquals(0, result);
    }

    private Observation observation(double value) {
        Observation o = new Observation();
        o.setValue(value);
        return o;
    }

}