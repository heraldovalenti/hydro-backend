package com.aes.dashboard.backend.service.aesLatestData;

import com.aes.dashboard.backend.service.AppConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class AESDataService {


    private String url;
    private RestTemplate restTemplate;
    private AppConfigService appConfigService;

    public AESDataService(
            RestTemplate restTemplate,
            @Value("${aes.latest-data.url}") String url,
            AppConfigService appConfigService) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.appConfigService = appConfigService;
    }

    public List<DataItem> getLatestData() {
        String authToken = appConfigService.getAuthToken();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url)
                .queryParam("authToken", encodeAuthToken(authToken));
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

    private String encodeAuthToken(String authToken) {
        try {
            return URLEncoder.encode(authToken, "UTF-8" );
        } catch (Exception e) {
            return "";
        }
    }

}
