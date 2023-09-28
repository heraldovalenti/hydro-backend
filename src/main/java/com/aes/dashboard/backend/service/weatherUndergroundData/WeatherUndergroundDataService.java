package com.aes.dashboard.backend.service.weatherUndergroundData;

import com.aes.dashboard.backend.model.StationDataOrigin;
import com.aes.dashboard.backend.model.date.DateRounder;
import com.aes.dashboard.backend.service.AppConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WeatherUndergroundDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherUndergroundDataService.class);

    private String url;
    private RestTemplate restTemplate;
    private AppConfigService appConfigService;
    private int dateMinutesRound;
    private DateRounder dateRounder;

    public WeatherUndergroundDataService(
            @Qualifier("sslDisablingRestTemplate") RestTemplate restTemplate,
            AppConfigService appConfigService,
            @Value("${weather-underground-data.url}") String url,
            @Value("${weather-underground-data.dateMinutesRound}") Integer dateMinutesRound) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.appConfigService = appConfigService;
        this.dateMinutesRound = dateMinutesRound;
        this.dateRounder = new DateRounder(this.dateMinutesRound);
    }

    public Optional<WeatherUndergroundResult> getObservationData(StationDataOrigin stationDataOrigin) {

        String apiKey = appConfigService.getWeatherUndergroundAuthToken();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url)
                .queryParam("apiKey", apiKey)
                .queryParam("format", "json")
                .queryParam("units", "e")
                .queryParam("stationId", stationDataOrigin.getExternalStationId());
        try {
            ResponseEntity<WeatherUndergroundResult> response = this.restTemplate.getForEntity(
                    builder.toUriString(),
                    WeatherUndergroundResult.class);
            if (!response.hasBody()) {
                LOGGER.warn("Weatherunderground station {} (external ID {}) has no data",
                        stationDataOrigin.getStation().getId(), stationDataOrigin.getExternalStationId());
                return Optional.empty();
            }
            return Optional.of(response.getBody());
        } catch (Exception e) {
            LOGGER.warn("Could not retrieve observation data for station ID {}", stationDataOrigin, e);
            return Optional.empty();
        }
    }

    public LocalDateTime roundDateTime(LocalDateTime date) {
        return this.dateRounder.roundDateTime(date);
    }

}
