package com.aes.dashboard.backend.service.pwsWeatherData.weatherCloudData;

import com.aes.dashboard.backend.service.pwsWeatherData.PWSWeatherDataService;
import com.aes.dashboard.backend.service.pwsWeatherData.PWSWeatherPeriodItem;
import com.aes.dashboard.backend.service.pwsWeatherData.PWSWeatherResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import static com.aes.dashboard.backend.config.GlobalConfigs.PWS_WEATHER_DATE_TIME_FORMAT;
import static com.aes.dashboard.backend.config.GlobalConfigs.REQUEST_PARAM_DATE_SHORT_INPUT_FORMAT;

@SpringBootTest
public class PWSWeatherDataServiceTest {

    @Autowired
    private PWSWeatherDataService pwsWeatherDataService;

    @ParameterizedTest
    @CsvSource(value = {
            "mid_f9219,MID_F9219,salta,-65.418670654297,-24.74467086792", // salta
            "mid_sasa,MID_SASA,general alvarado,-65.480003356934,-24.85000038147", // general alvarado
            "sasa,SASA,salta airport,-65.466666666667,-24.85", // salta airport
            "mid_sasj,MID_SASJ,la unión,-65.080001831055,-24.379999160767", // la unión
            "sasj,SASJ,jujuy airport,-65.1,-24.366666666667", // jujuy airport
    })
    void testStationObservations(String stationId, String responseId, String place, Double longitude, Double latitude) {
        Optional<PWSWeatherResult> opt = pwsWeatherDataService.getObservationData(stationId);
        Assertions.assertTrue(opt.isPresent());
        PWSWeatherResult result = opt.get();
        Assertions.assertTrue(result.getSuccess());
        Assertions.assertEquals(responseId, result.getResponse().getId());
        Assertions.assertEquals(place, result.getResponse().getPlace().getName());
        Assertions.assertEquals(latitude, result.getResponse().getLoc().getLatitude());
        Assertions.assertEquals(longitude, result.getResponse().getLoc().getLongitude());
    }

    @Test
    void testF9219() {
        String stationId = "mid_f9219";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PWS_WEATHER_DATE_TIME_FORMAT);
        LocalDate from = LocalDate.of(2024, 10, 10),
                to = LocalDate.of(2024, 10, 11);
        Optional<PWSWeatherResult> opt = pwsWeatherDataService.getObservationData(stationId, from, to);
        Assertions.assertTrue(opt.isPresent());
        PWSWeatherResult result = opt.get();
        Assertions.assertTrue(result.getSuccess());
        Assertions.assertEquals(186, result.getResponse().getPeriods().length);
        Assertions.assertEquals(new Double(10.921999931335),
                result.getResponse().getPeriods()[0].getOb().getPrecipSinceLastObMM());
        Assertions.assertEquals(new Double(0.25399971008301),
                result.getResponse().getPeriods()[5].getOb().getPrecipSinceLastObMM());

        ZonedDateTime date3_28pm = ZonedDateTime.parse("2024-10-10T15:28:00-03:00", dtf);
        Optional<PWSWeatherPeriodItem> obsAt3_28pm = Arrays.stream(result.getResponse().getPeriods())
                .filter(period -> period.getOb().getDateTimeISO().isEqual(date3_28pm)).findFirst();
        Assertions.assertTrue((obsAt3_28pm.isPresent()));
        Assertions.assertEquals(
                new Double(1.523998260498),
                obsAt3_28pm.get().getOb().getPrecipSinceLastObMM()
        );

    }
}
