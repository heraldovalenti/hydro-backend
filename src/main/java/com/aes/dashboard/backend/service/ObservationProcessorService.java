package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.MeasurementDimension;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.model.accumulation.*;
import com.aes.dashboard.backend.repository.ObservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ObservationProcessorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObservationProcessorService.class);

    @Autowired
    private ObservationService observationService;

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private StationService stationService;

    @Autowired
    private MeasurementUnitService measurementUnitService;

    @Autowired
    private MeasurementDimensionService measurementDimensionService;

    @Autowired
    private DataOriginService dataOriginService;

    public void generateDiffs() {
        MeasurementDimension rainDimension = measurementDimensionService.getRainDimension();
        List<Station> stations = stationService.stationsWithDimension(rainDimension);
        for (Station station : stations) {
            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "time"));
            List<Observation> observations = observationRepository.findByStation(station, pageable).toList();
            generateObservationDiffs(station, observations);
        }
    }

    public void generateDiffsForStation(long stationId) {
        Station station = stationService.find(stationId);
        MeasurementDimension rainDimension = measurementDimensionService.getRainDimension();
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "time"));
        List<Observation> observations = observationRepository
                .findByStationAndDimension(station, rainDimension, pageable).toList();
        generateObservationDiffs(station, observations);
    }

    public void generateDiffsForPeriod(LocalDateTime from, LocalDateTime to) {
        MeasurementDimension rainDimension = measurementDimensionService.getRainDimension();
        List<Long> stationIds = observationRepository.stationIdByDimensionAndBetweenTime(rainDimension, from, to);
        for (Long stationId : stationIds) {
            generateDiffsForStationAndPeriod(stationId, from, to);
        }
    }

    public void generateDiffsForStationAndPeriod(long stationId, LocalDateTime from, LocalDateTime to) {
        MeasurementDimension rainDimension = measurementDimensionService.getRainDimension();
        Station station = stationService.find(stationId);
        List<Observation> observations = observationRepository.findByStationAndDimensionAndBetweenTimeOrderByTimeDesc(
                station, rainDimension, from, to
        );
        generateObservationDiffs(station, observations);
    }

    @Transactional
    private void generateObservationDiffs(Station station, List<Observation> observations) {
        LOGGER.debug("generating diffs for station {} ({})", station.getId(), station.getDescription());
        Optional<DiffsGenerator> optGenerator = generatorForStation(station);
        if (optGenerator.isPresent()) {
            optGenerator.get().generateDiffs(observations);
            observationService.saveAll(observations);
        }
    }

    private Optional<DiffsGenerator> generatorForStation(Station station) {
        if (!measurementDimensionService.hasRainDimension(station)) {
            return Optional.empty();
        }
        if (Set.of(6L, 8L, 10L, 11L, 76L, 82L).contains(station.getId())) {
            return Optional.of(new CopyPositiveDiffGenerator(measurementUnitService));
        }
        if (dataOriginService.hasAESDataOrigin(station)) {
            return Optional.of(new SimpleDiffGenerator(measurementUnitService));
        }
        if (dataOriginService.hasWeatherUndergroundDataOrigin(station)
                || dataOriginService.hasWeatherLinkDataOrigin(station)
                || dataOriginService.hasWeatherCloudDataOrigin(station)) {
            return Optional.of(new DailyDiffGenerator(measurementUnitService));
        }
        if (dataOriginService.hasSNIHDataOrigin(station)
                || dataOriginService.hasRP5DataOrigin(station)
                || dataOriginService.hasPWSDataOrigin(station)) {
            return Optional.of(new CopyDiffGenerator(measurementUnitService));
        }
        return Optional.empty();
    }
}
