package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.exception.EntityNotFound;
import com.aes.dashboard.backend.model.MeasurementDimension;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.model.StationDataOrigin;
import com.aes.dashboard.backend.repository.StationDataOriginRepository;
import com.aes.dashboard.backend.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    private StationDataOriginRepository stationDataOriginRepository;

    @Autowired
    private MeasurementDimensionService measurementDimensionService;

    @Autowired
    private StationRepository stationRepository;

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

    public Page<Station> findAll(Pageable pageable) {
        return stationRepository.findAll(pageable);
    }

    public Station find(Long stationId) {
        Optional<Station> station = stationRepository.findById(stationId);
        return station.orElseThrow(() -> new EntityNotFound(stationId, Station.class));
    }

    public List<Station> findActives() {
        return stationRepository.findAllByActive(Boolean.TRUE);
    }

    @Transactional
    public Station activate(Long stationId) {
        return setActiveStatus(stationId, true);
    }

    @Transactional
    public Station deactivate(Long stationId) {
        return setActiveStatus(stationId, false);
    }

    @Transactional
    public Station setActiveStatus(Long stationId, boolean active) {
        Station station = find(stationId);
        station.setActive(active);
        stationRepository.save(station);
        return station;
    }

}
