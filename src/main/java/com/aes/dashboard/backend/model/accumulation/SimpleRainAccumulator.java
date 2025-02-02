package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.Observation;

import java.util.List;

public class SimpleRainAccumulator extends RainAccumulator {

    @Override
    public double accumulate(List<Observation> observations) {
        return observations.stream().mapToDouble(o -> o.getValue()).sum();
    }
}
