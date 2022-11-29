package com.aes.dashboard.backend.service.forecast;

import com.aes.dashboard.backend.model.Forecast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Service
public class ForecastFetchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForecastFetchService.class);

    private String url;
    private RestTemplate restTemplate;

    public ForecastFetchService(
            RestTemplate restTemplate,
            @Value("${aes.forecasts.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public List<Forecast> callForecastsService() {
        LOGGER.info("Calling forecast ETL...");
        ResponseEntity<Forecast[]> response = this.restTemplate.getForEntity(
                this.url, Forecast[].class);
        List<Forecast> result = new LinkedList<>();
        for (Forecast f : response.getBody()) {
            result.add(f);
        }
        LOGGER.info("Forecast ETL call completed");
        return result;
    }

}
