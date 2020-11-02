package com.aes.dashboard.backend.service.weatherUndergroundData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private String apiKey;
    private RestTemplate restTemplate;
    private int dateMinutesRound;

    public WeatherUndergroundDataService(
            RestTemplate restTemplate,
            @Value("${weather-underground-data.url}") String url,
            @Value("${weather-underground-data.apiKey}") String apiKey,
            @Value("${weather-underground-data.dateMinutesRound}") Integer dateMinutesRound) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.apiKey = apiKey;
        this.dateMinutesRound = dateMinutesRound;
    }

    public Optional<WeatherUndergroundResult> getObservationData(String stationId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url)
                .queryParam("apiKey", this.apiKey)
                .queryParam("format", "json")
                .queryParam("units", "e")
                .queryParam("stationId", stationId);
        try {
            ResponseEntity<WeatherUndergroundResult> response = this.restTemplate.getForEntity(
                    builder.toUriString(),
                    WeatherUndergroundResult.class);
            return Optional.of(response.getBody());
        } catch (Exception e) {
            LOGGER.warn("Could not retrieve observation data for station ID {}", stationId);
            return Optional.empty();
        }
    }

    public LocalDateTime roundDateTime(LocalDateTime date) {
        int minuteDiff = date.getMinute() % this.dateMinutesRound;
        if (date.getSecond() == 0 && minuteDiff == 0) {
            return date;
        }
        LocalDateTime plusMinutes = date.plusMinutes(this.dateMinutesRound);
        LocalDateTime roundToSeconds = plusMinutes.withSecond(0);
        LocalDateTime roundToMinutes = roundToSeconds.plusMinutes(-minuteDiff);
        return roundToMinutes;
    }

}
