package com.aes.dashboard.backend.service.pwsWeatherData.weatherCloudData;

import com.aes.dashboard.backend.service.pwsWeatherData.PWSWeatherDataService;
import com.aes.dashboard.backend.service.pwsWeatherData.PWSWeatherResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

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

        Assertions.assertTrue(
                result.getObservationValue() >= 0,
                MessageFormat.format("result.getObservationValue() should be greater than 0, but found {0}",
                        result.getObservationValue()));
        LocalDateTime now = LocalDateTime.now(ZoneId.of(UTC_ZONE_ID)),
                ldt = result.getObservationTime();
        Duration diff = Duration.between(ldt, now);
        long minutesDiff = diff.toMinutes();
        int tolerance = 10;
        Assertions.assertTrue(
                minutesDiff <= tolerance,
                MessageFormat.format(
                        "observation date diff with now() should be less than {0}, but found {1} (now={2} ldt={3})",
                        tolerance, minutesDiff, now, ldt));
    }
}
