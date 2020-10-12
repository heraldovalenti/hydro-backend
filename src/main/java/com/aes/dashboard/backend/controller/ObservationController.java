package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.repository.ObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/observations")
public class ObservationController {

    @Autowired
    private ObservationRepository observationRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Observation>> list() {
        return ResponseEntity.ok(observationRepository.findAll());
    }
}

