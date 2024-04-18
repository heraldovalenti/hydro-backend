package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.exception.EntityNotFound;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stations")
public class StationController {

    @Autowired
    private StationRepository stationRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/actives")
    public ResponseEntity<List<Station>> actives() {
        return ResponseEntity.ok(stationRepository.findAllByActive(Boolean.TRUE));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Station>> list(Pageable pageable) {
        Page<Station> pages = stationRepository.findAll(pageable);
        return ResponseEntity.ok(pages);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{stationId}")
    public Station stationById(
            @PathVariable()
            @NotBlank(message = "Station ID is required")
                    Long stationId) {
        Optional<Station> station = stationRepository.findById(stationId);
        return station.orElseThrow(() -> new EntityNotFound(stationId, Station.class));
    }

}
