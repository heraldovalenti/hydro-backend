package com.aes.dashboard.backend.service.weatherCloudData;

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
public class WeatherCloudDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherCloudDataService.class);
    private String url;
    private RestTemplate restTemplate;
    private AppConfigService appConfigService;


    public WeatherCloudDataService(
            @Value("${weathercloud-data.url}") String url,
            @Qualifier("sslDisablingRestTemplate") RestTemplate restTemplate,
            AppConfigService appConfigService) {
        this.url = url;
        this.restTemplate = restTemplate;
        this.appConfigService = appConfigService;
    }

    public Optional<WeatherCloudResult> getObservationData(String stationId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(String.format("%s/%s", this.url, stationId));
        String stationUrl = builder.build(false).toUriString();
        String userAgentHeader = appConfigService.getUserAgentHeader();
        String cookieValue = this.appConfigService.getWeatherCloudCookie();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-requested-with","XMLHttpRequest");
            headers.add("User-Agent", userAgentHeader);
            headers.add("cookie", cookieValue);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<WeatherCloudResult> response = this.restTemplate.exchange(
                    stationUrl,
                    HttpMethod.GET,
                    entity,
                    WeatherCloudResult.class);
            WeatherCloudResult result = response.getBody();
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
