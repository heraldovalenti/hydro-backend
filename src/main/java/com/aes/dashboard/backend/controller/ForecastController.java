package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.model.Forecast;
import com.aes.dashboard.backend.model.ForecastSnapshot;
import com.aes.dashboard.backend.service.forecast.ForecastFetchService;
import com.aes.dashboard.backend.service.forecast.ForecastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

@RestController
@RequestMapping("/forecast")
public class ForecastController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForecastController.class);

    @Autowired
    private ForecastService forecastService;

    @Autowired
    private ForecastFetchService forecastFetchService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ForecastSnapshot> latestSnapshot() {
        Optional<ForecastSnapshot> snapshot = forecastService.latestSnapshot();
        if (snapshot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok( snapshot.get() );
     }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ForecastSnapshot> fetchAndPersist() {
        LOGGER.info("Refreshing forecast snapshot...");
        ForecastSnapshot forecastSnapshot = refreshSnapshot();
        forecastSnapshot = this.forecastService.persistForecastSnapshot(forecastSnapshot);
        LOGGER.info("Refreshing forecast snapshot completed");
        return ResponseEntity.status(HttpStatus.CREATED).body(forecastSnapshot);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/refresh")
    public ForecastSnapshot refreshSnapshot() {
        List<Forecast> freshForecasts = forecastFetchService.callForecastsService();
        ForecastSnapshot result = new ForecastSnapshot();
        result.setTime(LocalDateTime.now(ZoneId.of(UTC_ZONE_ID)));
        result.setForecasts(freshForecasts);
        return result;
    }
}
