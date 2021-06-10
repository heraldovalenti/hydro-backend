package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.Observation;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AesRainAccumulator extends RainAccumulator {

    @Override
    public double accumulate(List<Observation> observations) {
        List<Observation> purgedList = observations.stream()
                .filter(o -> o.getValue() >= 0) // filter out observations with negative value
                .collect(Collectors.toList());
        if (purgedList.size() == 1) return 0;
        LinkedList<Observation> sorted = new LinkedList<>(this.sortObservations(purgedList));
        double firstObservation = sorted.getFirst().getValue();
        double lastObservation = sorted.getLast().getValue();
        double diff = lastObservation - firstObservation;
        return (diff < 0) ? 0.0 : diff;
    }
}
