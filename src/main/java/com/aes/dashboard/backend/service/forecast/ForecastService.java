package com.aes.dashboard.backend.service.forecast;

import com.aes.dashboard.backend.model.ForecastSnapshot;
import com.aes.dashboard.backend.repository.ForecastSnapshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ForecastService {

    @Autowired
    private ForecastSnapshotRepository forecastSnapshotRepository;

    public ForecastService() {
    }

    public Optional<ForecastSnapshot> latestSnapshot() {
        return forecastSnapshotRepository.findFirstByOrderByTimeDesc();
    }

    @Transactional
    public ForecastSnapshot persistForecastSnapshot(ForecastSnapshot forecastSnapshot) {
        forecastSnapshot.getForecasts().forEach(f -> {
            f.setForecastsSnapshot(forecastSnapshot);
            f.getDetails().forEach(d -> d.setForecast(f));
        });
        return this.forecastSnapshotRepository.save(forecastSnapshot);
    }
}
