package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.controller.entities.WeatherLinkHealthCheckResult;
import com.aes.dashboard.backend.service.aesLatestData.AESDataService;
import com.aes.dashboard.backend.service.weatherlinkData.WeatherlinkDataService;
import com.aes.dashboard.backend.service.weatherlinkData.WeatherlinkResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckController.class);

    @Autowired
    private AESDataService aesDataService;

    @Autowired
    private WeatherlinkDataService weatherlinkDataService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity generalHealthCheck() {
        LOGGER.info("general health check");
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/latestData")
    public ResponseEntity latestData() {
        LOGGER.info("health check for latestData");
        try {
            int latestDataSize = aesDataService.getLatestData(true).size();
            return ResponseEntity.ok().body(
                    Map.of(
                            "latestDataCheck", "ok",
                            "latestDataSize", latestDataSize
                    ));
        } catch (RestClientException e) {
            LOGGER.warn("Exception during health check for /latestData", e);
            return ResponseEntity.status(403).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/weatherlink")
    public ResponseEntity<WeatherLinkHealthCheckResult> weatherlink(
            @RequestParam("stationId") String stationId) {
        LOGGER.info("health check for weatherlink (using stationId {})", stationId);
        try {
            Optional<WeatherlinkResult> wlResult = weatherlinkDataService.getObservationData(stationId);
            if (wlResult.isEmpty()) {
                return ResponseEntity.status(403).build();
            }
            WeatherLinkHealthCheckResult result =
                    WeatherLinkHealthCheckResult.fromWeatherlinkResult(wlResult.get());
        return ResponseEntity.ok().body(result);
        } catch (RestClientException e) {
            LOGGER.warn("Exception during health check for /weatherlink", e);
            return ResponseEntity.status(403).build();
        }
    }
}
