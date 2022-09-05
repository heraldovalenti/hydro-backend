package com.aes.dashboard.backend.service.weatherlinkData;

import com.aes.dashboard.backend.service.AppConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WeatherlinkDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherlinkDataService.class);
    private static final String AUTH_TOKEN_COOKIE_NAME = "JSESSIONID";

    private String url;
    private RestTemplate restTemplate;
    private AppConfigService appConfigService;

    public WeatherlinkDataService(
            RestTemplate restTemplate,
            AppConfigService appConfigService,
            @Value("${weatherlink-data.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.appConfigService = appConfigService;
    }

    public Optional<WeatherlinkResult> getObservationData(String stationId) {
        return this.getObservationData(stationId, LocalDateTime.now());
    }
    public Optional<WeatherlinkResult> getObservationData(String stationId, LocalDateTime timestamp) {
        Long ts = TimestampProvider.getTimestamp(timestamp);
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(String.format("%s/%s", this.url, stationId))
                .queryParam("ts", ts);
        String currentAuthToken = this.appConfigService.getWeatherlinkAuthToken();
        String userAgentHeader = appConfigService.getUserAgentHeader();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control","no-cache");
            headers.add("Connection", "keep-alive");
            headers.add("Pragma", "no-cache");
            headers.add("User-Agent", userAgentHeader);
            String cookieHeaderValue = String.format("%s=%s", AUTH_TOKEN_COOKIE_NAME, currentAuthToken);
            headers.add("cookie", cookieHeaderValue);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<WeatherlinkResult> response = restTemplate.exchange(
                    builder.build(false).toUriString(),
                    HttpMethod.GET,
                    entity,
                    WeatherlinkResult.class);

            return Optional.of(response.getBody());
        } catch (Exception e) {
            LOGGER.warn("Could not retrieve observation data for station ID {}", stationId, e);
            return Optional.empty();
        }
    }

}
