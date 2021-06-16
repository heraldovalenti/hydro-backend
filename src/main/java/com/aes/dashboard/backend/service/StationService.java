package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.MeasurementDimension;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.model.StationDataOrigin;
import com.aes.dashboard.backend.repository.StationDataOriginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    private StationDataOriginRepository stationDataOriginRepository;

    @Autowired
    private MeasurementDimensionService measurementDimensionService;

    public List<Station> stationsWithDimension(MeasurementDimension dimension) {
        List<StationDataOrigin> dataOriginsForRain = stationDataOriginRepository
                .findByDimension(dimension);
        List<Station> result = dataOriginsForRain.stream().map(x -> x.getStation()).collect(Collectors.toList());
        return result;
    }

    public List<Station> stationsWithRainOrigin() {
        MeasurementDimension rainDimension = measurementDimensionService.getRainDimension();
        List<StationDataOrigin> dataOriginsForRain = stationDataOriginRepository
                .findByDimension(rainDimension);
        List<Station> result = dataOriginsForRain.stream().map(x -> x.getStation()).collect(Collectors.toList());
        return result;
    }

}
