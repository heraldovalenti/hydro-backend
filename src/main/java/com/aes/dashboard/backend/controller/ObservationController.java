package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.dto.ObservationWithStation;
import com.aes.dashboard.backend.dto.RequestTimePeriod;
import com.aes.dashboard.backend.exception.EntityNotFound;
import com.aes.dashboard.backend.model.MeasurementDimension;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.model.accumulation.RainObservationAccumulation;
import com.aes.dashboard.backend.repository.MeasurementDimensionRepository;
import com.aes.dashboard.backend.repository.ObservationRepository;
import com.aes.dashboard.backend.repository.StationRepository;
import com.aes.dashboard.backend.service.MeasurementUnitService;
import com.aes.dashboard.backend.service.ObservationService;
import com.aes.dashboard.backend.service.RainAccumulationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/observations")
public class ObservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObservationController.class);

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ObservationService observationService;

    @Autowired
    private MeasurementUnitService measurementUnitService;

    @Autowired
    private MeasurementDimensionRepository measurementDimensionRepository;

    @Autowired
    private RainAccumulationService rainAccumulationService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Observation> listAll(
            @PageableDefault(value = 20, page = 0)
            @SortDefault(sort = "time", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return observationRepository.findAll(pageable);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{stationId}")
    public Page<Observation> listByStation(
            @PathVariable()
            @NotBlank(message = "Station ID is required")
                    Long stationId,
            @PageableDefault(value = 20, page = 0)
            @SortDefault(sort = "time", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        Optional<Station> station = stationRepository.findById(stationId);
        Page<Observation> results = observationRepository.findByStation(
                station.orElseThrow(() -> new EntityNotFound(stationId, Station.class)), pageable);
        measurementUnitService.normalizeMeasurementUnits(results);
        return results;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{stationId}")
    public Page<Observation> listByStationAndPeriod(
            @PathVariable
                    Long stationId,
            @PageableDefault(value = 20, page = 0)
            @SortDefault(sort = "time", direction = Sort.Direction.DESC)
                    Pageable pageable,
            @RequestBody RequestTimePeriod requestTimePeriod) {
        Optional<Station> station = stationRepository.findById(stationId);
        Page<Observation> results = observationRepository.findByStationAndBetweenTime(
                station.orElseThrow(() -> new EntityNotFound(stationId, Station.class)),
                requestTimePeriod.getFrom(), requestTimePeriod.getTo(), pageable);
        measurementUnitService.normalizeMeasurementUnits(results);
        return results;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{stationId}/{dimensionId}")
    public Page<Observation> listByStationAndDimensionAndPeriod(
            @PathVariable
                    Long stationId,
            @PathVariable
                    Long dimensionId,
            @RequestParam(defaultValue = "false", name = "useHQModel") boolean useHQModel,
            @PageableDefault(value = 20, page = 0)
            @SortDefault(sort = "time", direction = Sort.Direction.DESC)
                    Pageable pageable,
            @RequestBody RequestTimePeriod requestTimePeriod) {
        Optional<Station> station = stationRepository.findById(stationId);
        Optional<MeasurementDimension> dimension = measurementDimensionRepository.findById(dimensionId);
        Page<Observation> results = observationService.listByStationAndDimensionAndPeriod(
                station.orElseThrow(() -> new EntityNotFound(stationId, Station.class)),
                dimension.orElseThrow(() -> new EntityNotFound(dimensionId, MeasurementDimension.class)),
                useHQModel, requestTimePeriod.getFrom(), requestTimePeriod.getTo(), pageable);
        measurementUnitService.normalizeMeasurementUnits(results);
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{stationId}/accumulated")
    public RainObservationAccumulation accumulated(
            @PathVariable()
            @NotBlank(message = "Station ID is required")
                    Long stationId,
            @RequestParam(defaultValue = "24", name = "hours")
            @NotBlank(message = "Hours amount is required")
                    Long hours) {
        LOGGER.debug("{} hours accumulated rain for station {}", hours, stationId);
        Optional<Station> stationOpt = stationRepository.findById(stationId);
        Station station = stationOpt.orElseThrow(() -> new EntityNotFound(stationId, Station.class));
        List<Observation> observationList = rainAccumulationService.rainObservationsForStation(station, hours);
        measurementUnitService.normalizeMeasurementUnits(observationList);
        LOGGER.debug("observations for accumulation: {}", observationList.size());
        RainObservationAccumulation result = new RainObservationAccumulation(observationList);
        LOGGER.debug("rain accumulation: {}", result.toString());
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updateObservations")
    public void updateObservations() {
        observationService.updateAesObservations();
        observationService.updateWeatherUndergroundObservations();
        observationService.updateINTASiga2Observations();
        observationService.updateINTAAnteriorObservations();
        observationService.updateSNIHObservations();
        observationService.updateWeatherlinkObservations();
        observationService.updateAesIbuObservations();
        observationService.updateWeatherCloudObservations();
        observationService.updatePWSWeatherObservations();
        observationService.updateRP5Observations();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updateAesObservations")
    public void updateAesObservations() {
        observationService.updateAesObservations();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updateAesIbuObservations")
    public void updateAesIbuObservations() {
        observationService.updateAesIbuObservations();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updateWeatherCloudObservations")
    public void updateWeatherCloudObservations() {
        observationService.updateWeatherCloudObservations();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updatePWSWeatherObservations")
    public void updatePWSWeatherObservations(
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to) {
        if (!from.isEmpty() && !to.isEmpty()) {
            RequestTimePeriod period = RequestTimePeriod.of(from, to);
            observationService.updatePWSWeatherObservations(period, false);
        } else {
            observationService.updatePWSWeatherObservations();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updateWeatherUndergroundObservations")
    public void updateWeatherUndergroundObservations() {
        observationService.updateWeatherUndergroundObservations();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updateINTASiga2Observations")
    public void updateINTASiga2Observations() {
        observationService.updateINTASiga2Observations();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updateINTAAnteriorObservations")
    public void updateINTAAnteriorObservations() {
        observationService.updateINTAAnteriorObservations();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updateRP5Observations")
    public void updateRP5Observations() {
        observationService.updateRP5Observations();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updateSNIHObservations")
    public void updateSNIHObservations(
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to) {
        if (!from.isEmpty() && !to.isEmpty()) {
            RequestTimePeriod period = RequestTimePeriod.of(from, to);
            observationService.updateSNIHObservations(period, false);
        } else {
            observationService.updateSNIHObservations();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updateWeatherlinkObservations")
    public void updateWeatherlinkObservations() {
        observationService.updateWeatherlinkObservations();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lastObservation/{stationId}/{dimensionId}")
    public Observation lastObservation(
            @PathVariable Long stationId,
            @PathVariable Long dimensionId) {
        Optional<Station> station = stationRepository.findById(stationId);
        Optional<MeasurementDimension> dimension = measurementDimensionRepository.findById(dimensionId);
        Optional<Observation> result = observationRepository.findFirstByStationAndDimensionOrderByTimeDesc(
                station.orElseThrow(() -> new EntityNotFound(stationId, Station.class)),
                dimension.orElseThrow(() -> new EntityNotFound(dimensionId, MeasurementDimension.class))
        );
        LOGGER.info("Last observation for station {} and dimension {} found? {}", stationId, dimensionId, result.isPresent());
        return result.orElse(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/latestObservations/deprecated/{dimensionId}")
    public List<ObservationWithStation> latestObservationsDeprecated(
            @PathVariable Long dimensionId,
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to) {
        Long start = System.currentTimeMillis();
        RequestTimePeriod period = RequestTimePeriod.of(from, to);
        Optional<MeasurementDimension> dimension = measurementDimensionRepository.findById(dimensionId);
        List<Observation> observations = observationService.latestObservations(
                dimension.orElseThrow(() -> new EntityNotFound(dimensionId, MeasurementDimension.class)),
                period.getFrom(), period.getTo());
        List<ObservationWithStation> result = observations.stream()
                .map(o -> ObservationWithStation.fromObservation(o))
                .collect(Collectors.toList());
        Long end = System.currentTimeMillis();
        LOGGER.debug("latestObservations execution time: {}", (end - start));
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/latestObservations/{dimensionId}")
    public List<ObservationWithStation> latestObservationsV2(
            @PathVariable Long dimensionId,
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to) {
        RequestTimePeriod period = RequestTimePeriod.of(from, to);
        Optional<MeasurementDimension> dimension = measurementDimensionRepository.findById(dimensionId);
        List<Observation> observations = observationService.latestObservationsV2(
                dimension.orElseThrow(() -> new EntityNotFound(dimensionId, MeasurementDimension.class)),
                period.getFrom(), period.getTo());
        List<ObservationWithStation> result = observations.stream()
                .map(o -> ObservationWithStation.fromObservation(o))
                .collect(Collectors.toList());
        return result;
    }
}

