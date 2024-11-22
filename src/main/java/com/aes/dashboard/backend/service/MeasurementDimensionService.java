package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.MeasurementDimension;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.repository.MeasurementDimensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MeasurementDimensionService {

    @Autowired
    private MeasurementDimensionRepository measurementDimensionRepository;

    public MeasurementDimension getLevelDimension() {
        return measurementDimensionRepository.findById(1L).get();
    }

    public MeasurementDimension getFlowDimension() {
        return measurementDimensionRepository.findById(2L).get();
    }

    public MeasurementDimension getRainDimension() {
        return measurementDimensionRepository.findById(3L).get();
    }

    public boolean hasRainDimension(Station station) {
        return station.getStationDataOriginList()
                .stream().filter(sdo -> getRainDimension().equals(sdo.getDimension()))
                .findFirst().isPresent();
    }
}
