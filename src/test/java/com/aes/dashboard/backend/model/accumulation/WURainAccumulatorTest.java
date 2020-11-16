package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.Observation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;


class WURainAccumulatorTest {

    @Test
    public void singleObservationTest() {
        List<Observation> observations = List.of(observation(0, 1));
        WURainAccumulator accumulator = new WURainAccumulator();
        double result = accumulator.accumulate(observations);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void noVariationTest() {
        List<Observation> observations = List.of(
                observation(0, 1),
                observation(1, 1),
                observation(2, 1));
        WURainAccumulator accumulator = new WURainAccumulator();
        double result = accumulator.accumulate(observations);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void basicVariationTest() {
        List<Observation> observations = List.of(
                observation(0, 1),
                observation(1, 2),
                observation(2, 3));
        WURainAccumulator accumulator = new WURainAccumulator();
        double result = accumulator.accumulate(observations);
        Assertions.assertEquals(2, result);
    }

    @Test
    public void dayResetWithoutVariationAfterResetTest() {
        List<Observation> observations = List.of(
                observation(0, 1),
                observation(1, 0), // reset
                observation(2, 0));
        WURainAccumulator accumulator = new WURainAccumulator();
        double result = accumulator.accumulate(observations);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void dayResetWithVariationFromZeroAfterResetTest() {
        List<Observation> observations = List.of(
                observation(0, 1),
                observation(1, 0), // reset
                observation(2, 1));
        WURainAccumulator accumulator = new WURainAccumulator();
        double result = accumulator.accumulate(observations);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void dayResetWithVariationAfterResetTest() {
        List<Observation> observations = List.of(
                observation(0, 3),
                observation(1, 2), // reset and observed 2
                observation(2, 3));
        WURainAccumulator accumulator = new WURainAccumulator();
        double result = accumulator.accumulate(observations);
        Assertions.assertEquals(3, result);
    }

    @Test
    public void dayResetWithVariationBeforeDayReset() {
        List<Observation> observations = List.of(
                observation(0, 0),
                observation(1, 1),
                observation(2, 2),
                observation(3, 0));
        WURainAccumulator accumulator = new WURainAccumulator();
        double result = accumulator.accumulate(observations);
        Assertions.assertEquals(2, result);
    }

    @Test
    public void dayResetWithVariationBeforeAndAfterDayReset() {
        List<Observation> observations = List.of(
                observation(0, 0),
                observation(1, 1),
                observation(2, 2),
                observation(3, 0),
                observation(4, 1),
                observation(5, 2));
        WURainAccumulator accumulator = new WURainAccumulator();
        double result = accumulator.accumulate(observations);
        Assertions.assertEquals(4, result);
    }

    @Test
    public void dayResetWithVariationBeforeAndAfterDayReset2() {
        List<Observation> observations = List.of(
                observation(0, 0),
                observation(1, 1),
                observation(2, 2),
                observation(3, 1),
                observation(4, 2),
                observation(5, 3));
        WURainAccumulator accumulator = new WURainAccumulator();
        double result = accumulator.accumulate(observations);
        Assertions.assertEquals(5, result);
    }

    private Observation observation(int position, double value) {
        Observation o = new Observation();
        o.setValue(value);
        o.setTime(LocalDateTime.of(2020, 1, 1, 0 , position));
        return o;
    }

}