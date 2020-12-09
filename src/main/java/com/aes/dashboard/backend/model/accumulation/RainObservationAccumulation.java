package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.DataOrigin;
import com.aes.dashboard.backend.model.Observation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RainObservationAccumulation {

    private static final Logger LOGGER = LoggerFactory.getLogger(RainObservationAccumulation.class);

    private List<Observation> observationSeries;

    private List<RainAccumulation> rainAccumulations;

    public RainObservationAccumulation(List<Observation> observationSeries) {
        this.rainAccumulations = new LinkedList<>();
        this.observationSeries = observationSeries;
        updateAccumulations();
    }

    private void updateAccumulations() {
        List<DataOrigin> dataOrigins = this.observationSeries.stream()
                .map(x -> x.getDataOrigin())
                .distinct()
                .collect(Collectors.toList());
        LOGGER.debug("dataOrigins={}", dataOrigins);
        for (DataOrigin dataOrigin : dataOrigins) {
            Optional<RainAccumulator> accumulator = RainAccumulator.accumulatorForObservations(this.observationSeries);
            if (accumulator.isEmpty()) {
                accumulator = RainAccumulator.accumulatorForDataOrigin(dataOrigin);
            }
            LOGGER.debug("accumulator for dataOrigin {} found?: {}", dataOrigin, accumulator.isPresent());
            List<Observation> observations = this.observationSeries.stream()
                    .filter(x -> x.getDataOrigin().equals(dataOrigin))
                    .collect(Collectors.toList());
            LOGGER.debug("observations to accumulate are: {}", observations);
            double accumulation = 0;
            if (accumulator.isPresent()) {
                accumulation = accumulator.get().accumulate(observations);
                LOGGER.debug("accumulation result with {} is {}", accumulator.get(), accumulation);
            }
            rainAccumulations.add(new RainAccumulation(dataOrigin, accumulation));
        }
    }

    public List<RainAccumulation> getRainAccumulations() {
        return rainAccumulations;
    }

    public void setRainAccumulations(List<RainAccumulation> rainAccumulations) {
        this.rainAccumulations = rainAccumulations;
    }

    public List<Observation> getObservationSeries() {
        return observationSeries;
    }

    public void setObservationSeries(List<Observation> observationSeries) {
        this.observationSeries = observationSeries;
    }

    @Override
    public String toString() {
        return "RainObservationAccumulation{" +
                "observationSeries=" + observationSeries +
                ", rainAccumulations=" + rainAccumulations +
                '}';
    }
}
