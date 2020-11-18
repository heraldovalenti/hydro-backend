package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.Observation;

import java.util.List;

public class AesRainAccumulator extends RainAccumulator {

    @Override
    public double accumulate(List<Observation> observations) {
        if (observations.size() == 1) return 0;
        List<Observation> sorted = this.sortObservations(observations);
        double firstObservation = sorted.get(0).getValue();
        double lastObservation = sorted.get(observations.size() - 1).getValue();
        double diff = lastObservation - firstObservation;
        return (diff < 0) ? 0.0 : diff;
    }
}
