package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.exception.StationNotFound;
import com.aes.dashboard.backend.repository.ObservationRepository;
import com.aes.dashboard.backend.repository.StationRepository;
import com.aes.dashboard.backend.service.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequestMapping("/observations")
@Validated
public class ObservationController {

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ObservationService observationService;

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
        return observationRepository.findByStation(
                station.orElseThrow(StationNotFound::new), pageable);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updateAesObservations")
    public void updateAesObservations() {
        observationService.updateAesObservations();
    }
}

