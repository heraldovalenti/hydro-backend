package com.aes.dashboard.backend.service.snih;

import com.aes.dashboard.backend.service.AppConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.aes.dashboard.backend.config.GlobalConfigs.SALTA_ZONE_ID;

@Service
public class SNIHDataService {

    public static final Integer HEIGHT_CODE = 1;
    public static final Integer RAIN_CODE = 20;

    private String url;
    private RestTemplate restTemplate;

    public SNIHDataService(
            RestTemplate restTemplate,
            @Value("${snih.url.latest}") String url,
            AppConfigService appConfigService) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public List<SNIHObservation> getLatestData(Integer stationId, Integer code) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of(SALTA_ZONE_ID));
        return getLatestData(stationId, code, now, now);
    }

    public List<SNIHObservation> getLatestData(Integer stationId, Integer code, LocalDateTime from, LocalDateTime to) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, String> body = Map.of(
                "fechaDesde", from.format(dtf),
                "fechaHasta", to.format(dtf),
                "estacion", stationId.toString(),
                "codigo", code.toString() );
        ResponseEntity<SNIHDataRoot> response = this.restTemplate.postForEntity(
                this.url, body, SNIHDataRoot.class);
        List<SNIHObservation> result = new LinkedList<>();
        SNIHDataRoot dataRoot = response.getBody();
        for (SNIHDataMedicion medicion : dataRoot.getD().getMediciones()) {
            result.add( new SNIHObservation(
                    medicion.parseFechaHora(),
                    medicion.parseCodigo(),
                    medicion.parseValor() ));
        }
        return result;
    }


}
