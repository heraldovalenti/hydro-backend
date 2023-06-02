package com.aes.dashboard.backend.service.intaData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class INTAAnteriorDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(INTAAnteriorDataService.class);

    private final String baseUrl;
    private final String sufix;
    private final RestTemplate restTemplate;

    public INTAAnteriorDataService(
            @Qualifier("sslDisablingRestTemplate") RestTemplate restTemplate,
            @Value("${INTA-data.anterior.baseUrl}") String baseUrl,
            @Value("${INTA-data.anterior.sufix}") String sufix
    ) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.sufix = sufix;
    }

    public List<INTAAnteriorDataItem> getObservationData(String stationId) {
        String response = "";
        String url = baseUrl + "/" + stationId + sufix;
        try {
            response = this.restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            LOGGER.warn("Could not retrieve observation data for station ID {}", stationId, e);
        }
        return INTAAnteriorParser.parseResponse(response);
    }


}
