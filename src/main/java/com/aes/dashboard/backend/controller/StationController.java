package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationController {

    @Autowired
    private StationRepository stationRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Station>> list() {
        return ResponseEntity.ok(stationRepository.findAll());
    }

}
