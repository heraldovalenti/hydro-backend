package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.MeasurementUnit;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.repository.MeasurementUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeasurementUnitService {

    private static final double INCH_MILLIMETER = 25.4;

    @Autowired
    private MeasurementUnitRepository measurementUnitRepository;

    @Autowired
    private MeasurementDimensionService measurementDimensionService;

    public Optional<MeasurementUnit> getByAlias(String alias) {
        if (alias == null) return Optional.empty();
        String sanitizedAlias = alias.trim();
        sanitizedAlias.replaceAll("\n", "");
        return measurementUnitRepository.findByAlias(sanitizedAlias).stream().findAny();
    }

    public MeasurementUnit getInchUnit() {
        return measurementUnitRepository.findById(4L).get();
    }

    public MeasurementUnit getMillimeterUnit() {
        return measurementUnitRepository.findById(3L).get();
    }

    public void normalizeMeasurementUnits(Page<Observation> observations) {
        observations.stream()
                .filter(o -> o.getDimension().equals(measurementDimensionService.getRainDimension()))
                .filter(o -> o.getUnit().equals(getInchUnit()))
                .forEach(o -> {
                    o.setUnit(getMillimeterUnit());
                    o.setValue(o.getValue() * INCH_MILLIMETER);
                });
    }


}
