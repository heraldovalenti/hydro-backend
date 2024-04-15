package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.dto.RequestTimePeriod;
import com.aes.dashboard.backend.model.MeasurementDimension;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.repository.StationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class DataExportServiceTest {

    @Autowired
    private DataExportService service;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private MeasurementDimensionService dimensionService;

    @Test
    public void testStationsNameForExport() {
        RequestTimePeriod rtp = new RequestTimePeriod(
                LocalDateTime.of(2021,1,1,0,0),
                LocalDateTime.of(2021,1,1,0,0));
        List<Station> stations = stationRepository.findAll();
        MeasurementDimension rainDimension = dimensionService.getRainDimension();
        for (Station s : stations) {
            if (!Normalizer.isNormalized(s.getDescription(), Normalizer.Form.NFD)) {
                String header = service.getContentDispositionHeader(s, rainDimension, rtp);
                boolean result = Normalizer.isNormalized(header, Normalizer.Form.NFD);
                Assertions.assertTrue(result);
            }

        }
    }

    @Test
    public void testNormalizer() {
        String input = "ÁáÉéíÍóÓúÚñÑüÜ";
        boolean result1 = Normalizer.isNormalized(input, Normalizer.Form.NFC);
        boolean result2 = Normalizer.isNormalized(input, Normalizer.Form.NFD);
        boolean result3 = Normalizer.isNormalized(input, Normalizer.Form.NFKC);
        boolean result4 = Normalizer.isNormalized(input, Normalizer.Form.NFKD);
        Assertions.assertTrue(result1);
        Assertions.assertFalse(result2);
        Assertions.assertTrue(result3);
        Assertions.assertFalse(result4);
    }

}