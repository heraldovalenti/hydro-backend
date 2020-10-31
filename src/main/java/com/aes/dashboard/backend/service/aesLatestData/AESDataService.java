package com.aes.dashboard.backend.service.aesLatestData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class AESDataService {

    protected static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private String url;
    private RestTemplate restTemplate;

    public AESDataService(RestTemplate restTemplate, @Value("${aes.latest-data.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public List<LatestDataItem> getLatestData() {
        ResponseEntity<LatestDataItem[]> response = this.restTemplate.getForEntity(
                this.url,
                LatestDataItem[].class);
        List<LatestDataItem> result = new LinkedList<>();
        Collections.addAll(result, response.getBody());
        return result;
    }

}
