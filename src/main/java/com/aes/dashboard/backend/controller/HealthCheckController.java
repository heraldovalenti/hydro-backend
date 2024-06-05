package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.dto.HealthCheckResult;
import com.aes.dashboard.backend.dto.WeatherLinkHealthCheckResult;
import com.aes.dashboard.backend.service.aesLatestData.AESDataService;
import com.aes.dashboard.backend.service.pwsWeatherData.PWSWeatherDataService;
import com.aes.dashboard.backend.service.pwsWeatherData.PWSWeatherResult;
import com.aes.dashboard.backend.service.weatherCloudData.WeatherCloudDataService;
import com.aes.dashboard.backend.service.weatherCloudData.WeatherCloudResult;
import com.aes.dashboard.backend.service.weatherUndergroundData.WeatherUndergroundDataService;
import com.aes.dashboard.backend.service.weatherUndergroundData.WeatherUndergroundResult;
import com.aes.dashboard.backend.service.weatherlinkData.WeatherlinkDataService;
import com.aes.dashboard.backend.service.weatherlinkData.WeatherlinkResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private WeatherUndergroundDataService weatherUndergroundDataService;

    @Autowired
    private WeatherCloudDataService weatherCloudDataService;

    @Autowired
    private PWSWeatherDataService pwsWeatherDataService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<HealthCheckResult> generalHealthCheck() {
        LOGGER.info("general health check");
        HealthCheckResult result = new HealthCheckResult("general");
        return ResponseEntity.ok(result);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/latestData")
    public ResponseEntity latestData() {
        LOGGER.info("health check for latestData");
        HealthCheckResult result = new HealthCheckResult("latestData");
        try {
            int latestDataSize = aesDataService.getLatestData(true).size();
            result.setInfo(
                    Map.of(
                            "latestDataCheck", "ok",
                            "latestDataSize", latestDataSize
                    )
            );
            return ResponseEntity.ok(result);
        } catch (RestClientException e) {
            LOGGER.warn("Exception during health check for /latestData", e);
            result.setInfo(Map.of(
                    "errorMessage", e.getMessage()
            ));
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/weatherlink")
    public ResponseEntity<HealthCheckResult> weatherlink(
            @RequestParam("stationId") String stationId) {
        LOGGER.info("health check for weatherlink (using stationId {})", stationId);
        HealthCheckResult result = new HealthCheckResult("weatherlink");
        try {
            Optional<WeatherlinkResult> wlResult = weatherlinkDataService.getObservationData(stationId);
            if (wlResult.isEmpty()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
            }
            WeatherLinkHealthCheckResult checkResult =
                    WeatherLinkHealthCheckResult.fromWeatherlinkResult(wlResult.get());
            result.setInfo(Map.of(
                    "checkResult", checkResult
            ));
        return ResponseEntity.ok().body(result);
        } catch (RestClientException e) {
            LOGGER.warn("Exception during health check for /weatherlink", e);
            result.setInfo(Map.of(
                    "errorMessage", e.getMessage()
            ));
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/weatherUnderground")
    public ResponseEntity<HealthCheckResult> weatherUnderground(
            @RequestParam("stationId") String stationId) {
        LOGGER.info("health check for weatherUnderground (using stationId {})", stationId);
        HealthCheckResult result = new HealthCheckResult("weatherUnderground");
        try {
            Optional<WeatherUndergroundResult> wuResult =
                    weatherUndergroundDataService.getObservationData(stationId);
            if (wuResult.isEmpty()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
            }
            result.setInfo(Map.of(
                    "checkResult", wuResult
            ));
            return ResponseEntity.ok().body(result);
        } catch (RestClientException e) {
            LOGGER.warn("Exception during health check for /weatherUnderground", e);
            result.setInfo(Map.of(
                    "errorMessage", e.getMessage()
            ));
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/weatherCloud")
    public ResponseEntity<HealthCheckResult> weatherCloud(
            @RequestParam("stationId") String stationId) {
        LOGGER.info("health check for weatherCloud (using stationId {})", stationId);
        HealthCheckResult result = new HealthCheckResult("weatherCloud");
        try {
            Optional<WeatherCloudResult> wuResult =
                    weatherCloudDataService.getObservationData(stationId);
            if (wuResult.isEmpty()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
            }
            result.setInfo(Map.of(
                    "checkResult", wuResult
            ));
            return ResponseEntity.ok().body(result);
        } catch (RestClientException e) {
            LOGGER.warn("Exception during health check for /weatherCloud", e);
            result.setInfo(Map.of(
                    "errorMessage", e.getMessage()
            ));
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/pwsWeather")
    public ResponseEntity<HealthCheckResult> pwsWeather(
            @RequestParam("stationId") String stationId) {
        LOGGER.info("health check for pwsWeather (using stationId {})", stationId);
        HealthCheckResult result = new HealthCheckResult("pwsWeather");
        try {
            Optional<PWSWeatherResult> wuResult =
                    pwsWeatherDataService.getObservationData(stationId);
            if (wuResult.isEmpty()) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
            }
            result.setInfo(Map.of(
                    "checkResult", wuResult
            ));
            return ResponseEntity.ok().body(result);
        } catch (RestClientException e) {
            LOGGER.warn("Exception during health check for /pwsWeather", e);
            result.setInfo(Map.of(
                    "errorMessage", e.getMessage()
            ));
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
        }
    }
}
