package com.aes.dashboard.backend.service.weatherCloudData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.MessageFormat;
import java.util.Optional;

@SpringBootTest
public class WeatherCloudDataServiceTest {

    @Autowired
    private WeatherCloudDataService weatherCloudDataService;

    @ParameterizedTest
    @CsvSource(value = {
            "SASA,1246.0,Salta", // Salta
            "1968239092,1000.0,SAN LORENZO", // ESTACION SALTA
            "5457430806,1236.0,Salta", // Infomet
            "9341323576,0.0,Salta", // Encon Beach
            "8418199173,1352.0,La Silleta", // WS Los Robles

            "9831848457,760.0,Rosario de la Frontera", // EMA Roberto
            "7770552514,1740.0,guachipas", // daza
            "5992524282,1100.0,San Pedro de colalao", // Tako yana

            "SASJ,920.2,Jujuy", // Jujuy
            "1752089865,1259.0,San Salvador de Jujuy", // Los Perales
            "3863394405,1302.0,SAN SALVADOR DE JUJUY", // Easy Weather Jujuy
            "5433158430,1571.0,Yala ", // Dazayala
    })
    void testStationObservations(String stationId, Double altitude, String city) {
        Optional<WeatherCloudResult> opt = weatherCloudDataService.getObservationData(stationId);
        Assertions.assertTrue(opt.isPresent());
        WeatherCloudResult result = opt.get();
        DeviceInfo device = result.getDevice();
        ValuesInfo values = result.getValues();
        Assertions.assertEquals(altitude, device.getAltitude());
        Assertions.assertEquals(city, device.getCity());
        Assertions.assertNotNull(device.getUpdate());
        Assertions.assertTrue(
                device.getUpdate() >= 0,
                MessageFormat.format("device.getUpdate() should be greater than 0, but found {0}", device.getUpdate()));
        Assertions.assertNotNull(values.getBar());
        Assertions.assertTrue(
                values.getBar() >= 0,
                MessageFormat.format("values.getBar() should be greater than 0, but found {0}", values.getBar()));
        Assertions.assertNotNull(values.getHum());
        Assertions.assertTrue(
                values.getHum() >= 0,
                MessageFormat.format("values.getHum() should be greater than 0, but found {0}", values.getHum()));
        Assertions.assertNotNull(values.getRain());
    }
}
