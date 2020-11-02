package com.aes.dashboard.backend.service.aesLatestData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class AESDataService {

    private String url;
    private RestTemplate restTemplate;

    public AESDataService(RestTemplate restTemplate, @Value("${aes.latest-data.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public List<DataItem> getLatestData() {
        ResponseEntity<LatestDataItem[]> response = this.restTemplate.getForEntity(
                this.url,
                LatestDataItem[].class);
        List<DataItem> result = new LinkedList<>();
        Arrays.stream(response.getBody()).forEach(latestDataItem -> result.addAll(latestDataItem.getData()));
        return result;
    }

}
