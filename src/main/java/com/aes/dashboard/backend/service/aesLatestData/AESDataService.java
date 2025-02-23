package com.aes.dashboard.backend.service.aesLatestData;

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
public class AESDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AESDataService.class);
    private static final String COOKIE_NAME_FED_AUTH = "FedAuth";
    private static final String COOKIE_NAME_RT_FA = "rtFa";


    private String url;
    private RestTemplate restTemplate;

    public AESDataService(
            @Value("${aes.one-drive.url}") String url,
            RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public List<DataItem> getLatestData() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url);
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        this.restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);

        ResponseEntity<LatestDataItem[]> response = this.restTemplate.getForEntity(
                builder.build(false).toUriString(),
                LatestDataItem[].class);
        List<DataItem> result = new LinkedList<>();
        Arrays.stream(response.getBody()).forEach(latestDataItem -> result.addAll(latestDataItem.getData()));
        return result;
    }
}
