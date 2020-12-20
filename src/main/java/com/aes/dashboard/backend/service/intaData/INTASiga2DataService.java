package com.aes.dashboard.backend.service.intaData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.aes.dashboard.backend.config.GlobalConfigs.SALTA_ZONE_ID;
import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

@Service
public class INTASiga2DataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(INTASiga2DataService.class);

    private final String baseUrl;
    private final String varsKey;
    private final String varsPath;
    private final RestTemplate restTemplate;

    public INTASiga2DataService(
            RestTemplate restTemplate,
            @Value("${INTA-data.baseUrl}") String baseUrl,
            @Value("${INTA-data.varsKey}") String varsKey,
            @Value("${INTA-data.varsPath}") String varsPath
    ) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.varsPath = varsPath;
        this.varsKey = varsKey;
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(List.of(MediaType.valueOf("text/html; charset=utf-8")));
        List converters = this.restTemplate.getMessageConverters();
        converters.add(converter);
        this.restTemplate.setMessageConverters(converters);
    }

    public String retrieveStationBaseUrl() {
        String varsResult = this.restTemplate.getForObject(baseUrl + varsPath, String.class);
        String[] lines = varsResult.split("\n");
        String result = Arrays.stream(lines)
                .filter(line -> line.contains(varsKey))
                .collect(Collectors.joining());
        String[] lineParts = result.split("'");
        return lineParts[1];
    }

    public List<INTASiga2DataItem> getObservationData(String stationId) {
        String stationBaseUrl = baseUrl + "/" + retrieveStationBaseUrl() + stationId;
        List<INTASiga2DataItem> result = new LinkedList<>();
        try {
            ResponseEntity<INTASiga2DataItem[]> response = this.restTemplate.getForEntity(
                    stationBaseUrl,
                    INTASiga2DataItem[].class);
            Arrays.stream(response.getBody()).forEach(intaDataItem -> {
                ZonedDateTime saltaTime = ZonedDateTime.of(intaDataItem.getFecha(), ZoneId.of(SALTA_ZONE_ID));
                ZonedDateTime utcTime = saltaTime.withZoneSameInstant(ZoneId.of(UTC_ZONE_ID));
                intaDataItem.setFecha(utcTime.toLocalDateTime());
                result.add(intaDataItem);
            });
        } catch (Exception e) {
            LOGGER.warn("Could not retrieve observation data for station ID {}", stationId, e);
        }
        return result;
    }


}
