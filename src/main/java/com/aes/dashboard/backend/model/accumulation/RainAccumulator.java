package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.DataOrigin;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aes.dashboard.backend.config.GlobalConfigs.*;

public abstract class RainAccumulator {

    private static final Map<DataOrigin, RainAccumulator> dataOriginAccumulatorMap = Map.of(
            new DataOrigin(DATA_ORIGIN_AES), new AesRainAccumulator(),
            new DataOrigin(DATA_ORIGIN_WU), new WURainAccumulator(),
            new DataOrigin(DATA_ORIGIN_INTA_SIGA), new SimpleRainAccumulator(),
            new DataOrigin(DATA_ORIGIN_INTA_ANTERIOR), new SimpleRainAccumulator()
    );

    private static final Map<Station, RainAccumulator> stationAccumulatorMap = Map.of(
            new Station(11), new SimpleRainAccumulator(),
            new Station(16), new SimpleRainAccumulator(),
            new Station(76), new SimpleRainAccumulator(),
            new Station(6), new SimpleRainAccumulator()
    );

    public static Optional<RainAccumulator> accumulatorForObservations(List<Observation> observationList) {
        Optional<Station> observationStation = observationList
                .stream().map(o -> o.getStation()).distinct().findFirst();
        if (observationStation.isEmpty()) return Optional.empty();
        Station station = observationStation.get();
        return stationAccumulatorMap.containsKey(station) ?
                Optional.of(stationAccumulatorMap.get(station)) : Optional.empty();
    }

    public static Optional<RainAccumulator> accumulatorForDataOrigin(DataOrigin dataOrigin) {
        return dataOriginAccumulatorMap.containsKey(dataOrigin) ?
            Optional.of(dataOriginAccumulatorMap.get(dataOrigin)) : Optional.empty();
    }

    protected List<Observation> sortObservations(List<Observation> observations) {
        return observations.stream().sorted((o1, o2) -> {
            if (o1.getTime().isAfter(o2.getTime())) return 1;
            else if (o1.getTime().isBefore(o2.getTime())) return -1;
            return 0;
        }).collect(Collectors.toList());
    }

    public abstract double accumulate(List<Observation> observations);
}
