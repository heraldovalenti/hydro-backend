package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.controller.entities.RequestTimePeriod;
import com.aes.dashboard.backend.model.accumulation.RainAccumulation;
import com.aes.dashboard.backend.exception.EntityNotFound;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.model.accumulation.RainObservationAccumulation;
import com.aes.dashboard.backend.repository.StationRepository;
import com.aes.dashboard.backend.service.RainAccumulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rain-accumulation")
public class RainAccumulationController {

    @Autowired
    private StationRepository stationRepository;

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

}
