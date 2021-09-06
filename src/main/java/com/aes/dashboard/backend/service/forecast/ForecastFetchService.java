package com.aes.dashboard.backend.service.forecast;

import com.aes.dashboard.backend.model.Forecast;
import com.aes.dashboard.backend.model.ForecastSnapshot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Service
public class ForecastFetchService {

    private String url;
    private RestTemplate restTemplate;

    public ForecastFetchService(
            RestTemplate restTemplate,
            @Value("${aes.forecasts.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public List<Forecast> callForecastsService() {
        ResponseEntity<Forecast[]> response = this.restTemplate.getForEntity(
                this.url, Forecast[].class);
        List<Forecast> result = new LinkedList<>();
        for (Forecast f : response.getBody()) {
            result.add(f);
        }
        return result;
    }

}
