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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.aes.dashboard.backend.config.GlobalConfigs.REQUEST_PARAM_DATE_SHORT_INPUT_FORMAT;

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
        LocalDate now = LocalDate.now(),
                from = now.minusDays(1),
                to = now.plusDays(1);
        return getObservationData(stationId, from, to);
    }

    public Optional<PWSWeatherResult> getObservationData(
            String stationId, LocalDate from, LocalDate to) {
        String userAgentHeader = appConfigService.getUserAgentHeader();
        String authParam = this.appConfigService.getPWSWeatherAuthParam();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(REQUEST_PARAM_DATE_SHORT_INPUT_FORMAT);
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(String.format("%s/%s?%s", this.url, stationId, authParam))
//                .queryParam("filter", "calcprecip%2Cprecise%2Cmetar%3Bpws%3Bmadis%3Bausbom%2Callownowksy")
                .queryParam("from", from.format(dtf))
                .queryParam("to", to.format(dtf));
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
