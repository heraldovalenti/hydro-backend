package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.Observation;

import java.util.List;

public class AesRainAccumulator extends RainAccumulator {

    @Override
    public double accumulate(List<Observation> observations) {
        double min = observations.stream()
                .map(o -> o.getValue())
                .min(Double::compare).get();
        return observations.stream()
                .map(o -> o.getValue() - min)
                .reduce(0.0, (x,y) -> x + y);
    }
}
