package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.Observation;

import java.util.List;

public class SimpleRainAccumulator extends RainAccumulator {

    @Override
    public double accumulate(List<Observation> observations) {
        return observations.stream().mapToDouble(o -> Math.max(0.0, o.getValue())).sum();
    }
}
