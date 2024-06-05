package com.aes.dashboard.backend.service.pwsWeatherData;

import com.aes.dashboard.backend.service.AppConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class PWSWeatherDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PWSWeatherDataService.class);
    private String url;
    private RestTemplate restTemplate;
    private AppConfigService appConfigService;


    public PWSWeatherDataService(
            @Value("${pwsWeather-data.url}") String url,
            @Qualifier("sslDisablingRestTemplate") RestTemplate restTemplate,
            AppConfigService appConfigService) {
        this.url = url;
        this.appConfigService = appConfigService;
        this.restTemplate = restTemplate;
    }

    public Optional<PWSWeatherResult> getObservationData(String stationId) {
        String userAgentHeader = appConfigService.getUserAgentHeader();
        String authParam = this.appConfigService.getPWSWeatherAuthParam();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(String.format("%s/%s?%s", this.url, stationId, authParam));
        String stationUrl = builder.build(false).toUriString();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("User-Agent", userAgentHeader);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<PWSWeatherResult> response = this.restTemplate.exchange(
                    stationUrl,
                    HttpMethod.GET,
                    entity,
                    PWSWeatherResult.class);
            PWSWeatherResult result = response.getBody();
            if (!response.hasBody() || result == null) {
                return Optional.empty();
            }
            return Optional.of(result);
        } catch (Exception e) {
            LOGGER.warn("Could not retrieve observation data for station ID {}", stationId, e);
            return Optional.empty();
        }
    }
}
