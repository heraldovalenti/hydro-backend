package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.DataOrigin;
import com.aes.dashboard.backend.model.Observation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;
import java.util.stream.Collectors;

public class RainObservationAccumulation {

    @JsonIgnore
    private List<Observation> observationSeries;

    private List<RainAccumulation> rainAccumulations;

    @JsonIgnore
    private Map<DataOrigin, RainAccumulator> accumulatorMap;

    public RainObservationAccumulation(List<Observation> observationSeries) {
        this.accumulatorMap = new HashMap<>();
        this.rainAccumulations = new LinkedList<>();
        this.observationSeries = observationSeries;
        updateAccumulations();
    }

    private void updateAccumulations() {
        List<DataOrigin> dataOrigins = this.observationSeries.stream()
                .map(x -> x.getDataOrigin())
                .distinct()
                .collect(Collectors.toList());
        for (DataOrigin dataOrigin : dataOrigins) {
            Optional<RainAccumulator> accumulator = RainAccumulator.accumulatorForDataOrigin(dataOrigin);
            List<Observation> observations = this.observationSeries.stream()
                    .filter(x -> x.getDataOrigin().equals(dataOrigin))
                    .collect(Collectors.toList());
            double accumulation = 0;
            if (accumulator.isPresent()) {
                accumulation = accumulator.get().accumulate(observations);
            }
            rainAccumulations.add(new RainAccumulation(dataOrigin, accumulation));

        }
    }
}
