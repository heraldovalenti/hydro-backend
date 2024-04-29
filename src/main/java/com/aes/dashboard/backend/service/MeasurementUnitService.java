package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.MeasurementUnit;
import com.aes.dashboard.backend.model.MeasurementUnitConversion;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.repository.MeasurementUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementUnitService {

    @Autowired
    private MeasurementUnitRepository measurementUnitRepository;

    @Autowired
    private MeasurementDimensionService measurementDimensionService;

    private List<MeasurementUnitConversion> conversions;

    @PostConstruct
    public void initConversions() {
        this.conversions = List.of(
                new MeasurementUnitConversion(
                        getInchesMeasurementUnit(),
                        getMilimeterMeasurementUnit(),
                        25.4), // inch > millimeters
                new MeasurementUnitConversion(
                        getCentimeterMeasurementUnit(),
                        getMeterMeasurementUnit(),
                        0.01) // centimetro > metro
        );
    }

    public Optional<MeasurementUnit> getByAlias(String alias) {
        if (alias == null) return Optional.empty();
        String sanitizedAlias = alias.trim();
        sanitizedAlias.replaceAll("\n", "");
        return measurementUnitRepository.findByAlias(sanitizedAlias).stream().findAny();
    }

    public void normalizeMeasurementUnits(List<Observation> observations) {
        for (Observation o : observations) {
            getConversionForObservation(o).ifPresent(c -> {
                o.setValue(c.convert(o.getValue()));
                o.setUnit(c.getTarget());
            });
        }
    }

    public Optional<MeasurementUnitConversion> getConversionForObservation(Observation o) {
        return this.conversions.stream().filter(c -> o.getUnit().equals(c.getOrigin())).findFirst();
    }

    public void normalizeMeasurementUnits(Page<Observation> observations) {
        this.normalizeMeasurementUnits(observations.toList());
    }

    public MeasurementUnit getMeterMeasurementUnit() {
        return measurementUnitRepository.findById(2L).get();
    }

    public MeasurementUnit getMilimeterMeasurementUnit() {
        return measurementUnitRepository.findById(3L).get();
    }

    public MeasurementUnit getInchesMeasurementUnit() {
        return measurementUnitRepository.findById(4L).get();
    }

    public MeasurementUnit getM3PerSecondMeasurementUnit() {
        return measurementUnitRepository.findById(5L).get();
    }

    public MeasurementUnit getCentimeterMeasurementUnit() {
        return measurementUnitRepository.findById(6L).get();
    }
}
