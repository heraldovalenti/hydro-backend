package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.dto.IStationRainAccumulation;
import com.aes.dashboard.backend.dto.RequestTimePeriod;
import com.aes.dashboard.backend.exception.EntityNotFound;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.model.accumulation.RainAccumulation;
import com.aes.dashboard.backend.model.accumulation.RainObservationAccumulation;
import com.aes.dashboard.backend.model.accumulation.StationRainAccumulation;
import com.aes.dashboard.backend.repository.StationRepository;
import com.aes.dashboard.backend.service.RainAccumulationService;
import com.aes.dashboard.backend.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rain-accumulation")
public class RainAccumulationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RainAccumulationController.class);

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationService stationService;

    @Autowired
    private RainAccumulationService rainAccumulationService;

    @RequestMapping(method = RequestMethod.POST, value = "/{stationId}/{hours}")
    public List<RainAccumulation> accumulationForStation(
            @PathVariable Long stationId,
            @PathVariable Long hours) {
        Optional<Station> stationOpt = stationRepository.findById(stationId);
        Station station = stationOpt.orElseThrow(() -> new EntityNotFound(stationId, Station.class));
        List<Observation> observationList = rainAccumulationService
                .rainObservationsForStation(station, hours);
        RainObservationAccumulation result = new RainObservationAccumulation(observationList);
        return result.getRainAccumulations();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{stationId}")
    public List<RainAccumulation> accumulationForStation(
            @PathVariable Long stationId,
            @RequestBody RequestTimePeriod requestTimePeriod) {
        Optional<Station> stationOpt = stationRepository.findById(stationId);
        Station station = stationOpt.orElseThrow(() -> new EntityNotFound(stationId, Station.class));
        List<Observation> observationList = rainAccumulationService
                .rainObservationsForStation(station, requestTimePeriod.getFrom(), requestTimePeriod.getTo());
        RainObservationAccumulation result = new RainObservationAccumulation(observationList);
        return result.getRainAccumulations();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{stationId}")
    public List<RainAccumulation> accumulationForStation(
            @PathVariable Long stationId,
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to) {
        RequestTimePeriod period = RequestTimePeriod.of(from, to);
        return accumulationForStation(stationId, period);
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<StationRainAccumulation> accumulations(
            @RequestBody RequestTimePeriod requestTimePeriod) {
        Long start = System.currentTimeMillis();
        LOGGER.debug(requestTimePeriod.toString());
        List<StationRainAccumulation> results = rainAccumulationService.rainAccumulations(
                requestTimePeriod.getFrom(),
                requestTimePeriod.getTo()
        );
        Long end = System.currentTimeMillis();
        LOGGER.debug("accumulations execution time: {}", (end - start));
        return results;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<StationRainAccumulation> accumulations(
        @RequestParam(defaultValue = "") String from,
        @RequestParam(defaultValue = "") String to) {
        RequestTimePeriod period = RequestTimePeriod.of(from, to);
        return accumulations(period);
    }

    @RequestMapping(method = RequestMethod.GET, path = "v2")
    public List<IStationRainAccumulation> rainAccumulationsV2(
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to) {
        RequestTimePeriod requestTimePeriod = RequestTimePeriod.of(from, to);

        Long start = System.currentTimeMillis();
        LOGGER.debug(requestTimePeriod.toString());
        List<IStationRainAccumulation> results =
                rainAccumulationService.rainAccumulationsV2(
                        requestTimePeriod.getFrom(),
                        requestTimePeriod.getTo()
                );
        Long end = System.currentTimeMillis();
        LOGGER.debug("accumulations execution time: {}", (end - start));
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, path = "v2/{stationId}")
    public List<IStationRainAccumulation> stationRainAccumulationsV2(
            @PathVariable Long stationId,
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to) {
        RequestTimePeriod requestTimePeriod = RequestTimePeriod.of(from, to);
        Station s = stationService.find(stationId);
        List<IStationRainAccumulation> results =
                rainAccumulationService.stationRainAccumulationsV2(
                        s,
                        requestTimePeriod.getFrom(),
                        requestTimePeriod.getTo()
                );
        return results;
    }

}
