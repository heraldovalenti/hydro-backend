package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.controller.entities.RequestTimePeriod;
import com.aes.dashboard.backend.model.MeasurementDimension;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.exception.EntityNotFound;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/observations")
@Validated
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
            @PageableDefault(value = 20, page = 0)
            @SortDefault(sort = "time", direction = Sort.Direction.DESC)
                    Pageable pageable,
            @RequestBody RequestTimePeriod requestTimePeriod) {
        Optional<Station> station = stationRepository.findById(stationId);
        Optional<MeasurementDimension> dimension = measurementDimensionRepository.findById(dimensionId);
        Page<Observation> results = observationRepository.findByStationAndDimensionAndBetweenTime(
                station.orElseThrow(() -> new EntityNotFound(stationId, Station.class)),
                dimension.orElseThrow(() -> new EntityNotFound(dimensionId, MeasurementDimension.class)),
                requestTimePeriod.getFrom(), requestTimePeriod.getTo(), pageable);
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

    @RequestMapping(method = RequestMethod.GET, value = "/updateAesObservations")
    public void updateAesObservations() {
        observationService.updateAesObservations();
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
}

