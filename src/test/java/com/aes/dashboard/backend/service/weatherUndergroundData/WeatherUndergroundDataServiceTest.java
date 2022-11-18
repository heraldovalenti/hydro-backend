package com.aes.dashboard.backend.service.weatherUndergroundData;

import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.model.StationDataOrigin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
class WeatherUndergroundDataServiceTest {

    @Autowired
    private WeatherUndergroundDataService service;

    @Test
    public void getObservationDataTest_IROSARIO12() {
        Station s = new Station();
        s.setId(1);
        StationDataOrigin sdo = new StationDataOrigin();
        sdo.setExternalStationId("IROSARIO12");
        sdo.setStation(s);
        Optional<WeatherUndergroundResult> optionalResult = this.service.getObservationData(sdo);
        Assertions.assertTrue(optionalResult.isPresent());
        WeatherUndergroundResult result = optionalResult.get();
        Assertions.assertEquals(1, result.getSummaries().size());
        WeatherUndergroundStationObservation observation =
                result.getSummaries().stream().findAny().get().getImperial();
        Assertions.assertNotNull(observation);
        Assertions.assertEquals(result.getObservationTime().get(),
                result.getSummaries().stream().findAny().get().getObsTimeUtc());
        Assertions.assertEquals(result.getObservationValue().get(),
                result.getSummaries().stream().findAny().get().getImperial().getPrecipTotal());
    }

    @Test
    public void getObservationDataTest_ISALTA7() {
        Station s = new Station();
        s.setId(1);
        StationDataOrigin sdo = new StationDataOrigin();
        sdo.setExternalStationId("ISALTA7");
        sdo.setStation(s);
        Optional<WeatherUndergroundResult> optionalResult = this.service.getObservationData(sdo);
        Assertions.assertTrue(optionalResult.isEmpty());
    }

    @Test
    public void roundDateTests() {
        // dateRoundMinutes: 5;
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                this.service.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0))
        );
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 0, 5, 0),
                this.service.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 0, 1))
        );
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 0, 5, 0),
                this.service.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 1, 0))
        );
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 0, 5, 0),
                this.service.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 0, 59))
        );
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 0, 5, 0),
                this.service.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 4, 59))
        );
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 0, 45, 0),
                this.service.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 43, 33))
        );
        Assertions.assertEquals(
                LocalDateTime.of(2020, 1, 1, 1, 0, 0),
                this.service.roundDateTime(
                        LocalDateTime.of(2020, 1, 1, 0, 59, 59))
        );
    }
}