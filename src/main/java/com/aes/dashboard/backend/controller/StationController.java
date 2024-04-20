package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.service.StationService;
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

@RestController
@RequestMapping("/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @RequestMapping(method = RequestMethod.GET, path = "/actives")
    public ResponseEntity<List<Station>> activeStations() {
        return ResponseEntity.ok(stationService.findActives());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Station>> allStations(Pageable pageable) {
        return ResponseEntity.ok(stationService.findAll(pageable));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{stationId}")
    public ResponseEntity<Station> stationById(
            @PathVariable()
            @NotBlank(message = "Station ID is required")
                    Long stationId) {
        return ResponseEntity.ok(stationService.find(stationId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{stationId}/activate")
    public ResponseEntity<Station> activateStation(
            @PathVariable()
            @NotBlank(message = "Station ID is required")
            Long stationId) {
        return ResponseEntity.ok(stationService.activate(stationId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{stationId}/deactivate")
    public ResponseEntity<Station> deactivateStation(
            @PathVariable()
            @NotBlank(message = "Station ID is required")
            Long stationId) {
        return ResponseEntity.ok(stationService.deactivate(stationId));
    }
}
