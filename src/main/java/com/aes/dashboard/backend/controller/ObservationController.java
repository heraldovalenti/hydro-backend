package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.exception.StationNotFound;
import com.aes.dashboard.backend.model.accumulation.RainObservationAccumulation;
import com.aes.dashboard.backend.repository.ObservationRepository;
import com.aes.dashboard.backend.repository.StationRepository;
import com.aes.dashboard.backend.service.MeasurementUnitService;
import com.aes.dashboard.backend.service.ObservationService;
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
                station.orElseThrow(StationNotFound::new), pageable);
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
        Station station = stationOpt.orElseThrow(StationNotFound::new);
        List<Observation> observationList = observationService.rainObservationsForStation(station, hours);
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
}

