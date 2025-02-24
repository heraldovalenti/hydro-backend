package com.aes.dashboard.backend.service.gsheet;

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
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class GSheetDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GSheetDataService.class);

    private String url;
    private RestTemplate restTemplate;
    private AppConfigService appConfigService;

    public GSheetDataService(
            @Value("${aes.gsheet.url}") String url,
            @Qualifier("sslDisablingRestTemplate") RestTemplate restTemplate,
            AppConfigService appConfigService) {
        this.url = url;
        this.restTemplate = restTemplate;
        this.appConfigService = appConfigService;
    }

    public List<GSheetStation> getLatestData() {
        List<GSheetStation> result = new LinkedList<>();
        String authToken = appConfigService.getAesGsheetAuthParam();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(this.url);
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        this.restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("auth-token", authToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<GSheetStation[]> response = this.restTemplate.exchange(
                    builder.build(false).toUriString(),
                    HttpMethod.GET,
                    entity,
                    GSheetStation[].class);
            Arrays.stream(response.getBody()).forEach(station -> result.add(station));
        } catch (RuntimeException e) {
            LOGGER.warn("Could not retrieve observation data from Aes GSheet", e);
        }
        return result;
    }
}
