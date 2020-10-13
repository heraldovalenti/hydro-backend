package com.aes.dashboard.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> list() {
        LOGGER.info("health check hit");
        return ResponseEntity.ok("ok");
    }
}
