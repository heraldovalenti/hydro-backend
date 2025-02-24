package com.aes.dashboard.backend.service.gsheet;

import com.aes.dashboard.backend.config.GlobalConfigs;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.aes.dashboard.backend.config.GlobalConfigs.AES_GSHEET_DATE_TIME_FORMAT;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GSheetDataServiceTest {

    @Autowired
    private GSheetDataService service;

    @ParameterizedTest
    @CsvSource({"miraflores,2025-01-29 14:44:06",
            "alemania,2025-01-24 20:59:24",
            "quijano,2025-01-03 0:01:09",
            "maroma,2025-01-03 0:01:09",
            "medina,2025-01-03 0:01:09",
    })
    public void lastDataTest(String stationId, String dateString) {
        List<GSheetStation> result = service.getLatestData();
        assertEquals(5, result.size());
        GSheetStation station = result.stream().filter(r -> stationId.equals(r.getId())).findFirst().get();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(AES_GSHEET_DATE_TIME_FORMAT)
                .withZone(ZoneId.of(GlobalConfigs.SALTA_ZONE_ID));
        ZonedDateTime date = ZonedDateTime.parse(dateString, dtf);
        assertEquals(date.toInstant(),
                station.getObservations().get(station.getObservations().size() - 1).getDate().toInstant());
    }
}