package com.aes.dashboard.backend.service.forecast;

import com.aes.dashboard.backend.model.Forecast;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ForecastFetchServiceTest {

    @Autowired
    private ForecastFetchService forecastFetchService;

    @Test
    public void integrationTest() {
        List<Forecast> result = forecastFetchService.callForecastsService();
        assertNotNull(result);
        assertEquals(2, result.size()); // YR, windy
        assertTrue(result.get(0).getDetails().size() > 0);
        assertTrue(result.get(1).getDetails().size() > 0);
    }

}