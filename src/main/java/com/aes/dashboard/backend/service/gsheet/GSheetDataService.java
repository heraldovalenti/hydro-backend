package com.aes.dashboard.backend.service.gsheet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    public GSheetDataService(
            @Value("${aes.gsheet.url}") String url,
            RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public List<GSheetStation> getLatestData() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url);
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        this.restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);

        ResponseEntity<GSheetStation[]> response = this.restTemplate.getForEntity(
                builder.build(false).toUriString(),
                GSheetStation[].class);
        List<GSheetStation> result = new LinkedList<>();
        Arrays.stream(response.getBody()).forEach(station -> result.add(station));
        return result;
    }
}
