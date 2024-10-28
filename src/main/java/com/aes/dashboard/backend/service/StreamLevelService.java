package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.dto.RequestTimePeriod;
import com.aes.dashboard.backend.dto.StreamLevel;
import com.aes.dashboard.backend.exception.EntityNotFound;
import com.aes.dashboard.backend.model.HQModel;
import com.aes.dashboard.backend.model.MeasurementDimension;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class StreamLevelService {

    @Autowired
    private StationService stationService;

    @Autowired
    private ObservationService observationService;

    @Autowired
    private MeasurementDimensionService measurementDimensionService;

    public StreamLevel streamLevelForStation(Long stationId, RequestTimePeriod period) {
        Station station = stationService.find(stationId);
        return streamLevels(period)
                .stream().filter(sl -> sl.getObservation().getStation().equals(station))
                .findFirst().orElseThrow(() -> new EntityNotFound(station.getId(), HQModel.class));
    }

    public List<StreamLevel> streamLevels(RequestTimePeriod period) {
        MeasurementDimension levelDimension = measurementDimensionService.getLevelDimension();
        List<Observation> observations = observationService.latestObservations(
                levelDimension, period.getFrom(), period.getTo());
        List<StreamLevel> streamLevels = observations.stream()
            .filter(o -> o.getStation().getHqModel() != null)
            .map(o -> new StreamLevel(o))
            .collect(Collectors.toList());
        return streamLevels;
    }

}
