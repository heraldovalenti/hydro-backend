package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.dto.RequestTimePeriod;
import com.aes.dashboard.backend.service.ObservationProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/observation-processor")
public class ObservationProcessorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObservationProcessorController.class);

    @Autowired
    private ObservationProcessorService observationProcessorService;

    @RequestMapping(method = RequestMethod.GET, path = "diff/{stationId}")
    public ResponseEntity generateDiff(
            // this can be generalized as a @StationId annotation
            @PathVariable()
            @NotBlank(message = "Station ID is required") Long stationId,
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to) {
        Long start = System.currentTimeMillis();
        if (!from.isEmpty() && !to.isEmpty()) {
            RequestTimePeriod requestTimePeriod = RequestTimePeriod.of(from, to);
            observationProcessorService.generateDiffsForStationAndPeriod(
                    stationId,
                    requestTimePeriod.getFrom(),
                    requestTimePeriod.getTo()
            );
        } else {
            observationProcessorService.generateDiffsForStation(stationId);
        }
        Long end = System.currentTimeMillis();
        LOGGER.info("generate diffs for station {} execution time: {}", stationId, (end - start));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, path = "diff")
    public ResponseEntity generateDiff(
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to) {
        Long start = System.currentTimeMillis();
        RequestTimePeriod requestTimePeriod = RequestTimePeriod.of(from, to);
        LOGGER.debug(requestTimePeriod.toString());
        observationProcessorService.generateDiffsForPeriod(
                requestTimePeriod.getFrom(),
                requestTimePeriod.getTo()
        );
        Long end = System.currentTimeMillis();
        LOGGER.debug("generate diffs execution time: {}", (end - start));
        return ResponseEntity.ok().build();
    }

}
