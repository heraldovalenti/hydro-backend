package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.MeasurementDimension;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.model.accumulation.RainObservationAccumulation;
import com.aes.dashboard.backend.model.accumulation.StationRainAccumulation;
import com.aes.dashboard.backend.repository.ObservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RainAccumulationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RainAccumulationService.class);

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private MeasurementDimensionService measurementDimensionService;

    @Autowired
    private MeasurementUnitService measurementUnitService;

    @Autowired
    private StationService stationService;

//    public List<RainAccumulation> rainAccumulationForPeriod(LocalDateTime from, LocalDateTime to) {
//        List<Station> stationsWithRainOrigin = stationService.stationsWithRainOrigin();
//        List<RainAccumulation> result = stationsWithRainOrigin.stream().map(s -> {
//            List<Observation> observations = rainObservationsForStation(s, from, to);
//            RainObservationAccumulation a = new RainObservationAccumulation(observations);
//            a.
//        })
//        return null;
//    }

    public List<Observation> rainObservationsForStation(
            Station station, LocalDateTime from, LocalDateTime to) {
        MeasurementDimension rain = measurementDimensionService.getRainDimension();
        List<Observation> result = observationRepository.findByStationAndDimensionAndBetweenTime(station, rain, from, to);
        measurementUnitService.normalizeMeasurementUnits(result);
        return result;
    }

    public List<Observation> rainObservationsForStation(Station station, long hours) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDateTime from = now.minusHours(hours);
        LOGGER.debug("time period for {} hours is from {} to {}", hours, from, now);
        return rainObservationsForStation(station, from, now);
    }

    public List<StationRainAccumulation> rainAccumulations(LocalDateTime from, LocalDateTime to) {
        MeasurementDimension rainDimension = measurementDimensionService.getRainDimension();
        List<Observation> rainObservations = observationRepository.findByDimensionAndBetweenTime(rainDimension, from, to);
        List<Station> stations = stationService.stationsWithRainOrigin();
        List<StationRainAccumulation> results = new LinkedList<>();
        for (Station s : stations) {
            List<Observation> stationObservations = rainObservations
                    .stream().filter(o -> s.equals(o.getStation())).collect(Collectors.toList());
            measurementUnitService.normalizeMeasurementUnits(stationObservations);
            RainObservationAccumulation rainObservationAccumulation = new RainObservationAccumulation(stationObservations);
            StationRainAccumulation stationRainAccumulation =
                    new StationRainAccumulation(s.getId(), rainObservationAccumulation.getRainAccumulations());
            results.add(stationRainAccumulation);
        }
        return results;
    }


}
