package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.MeasurementUnit;
import com.aes.dashboard.backend.model.MeasurementUnitConversion;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.repository.MeasurementUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementUnitService {

    private static final List<MeasurementUnitConversion> conversions = List.of(
            new MeasurementUnitConversion(4, 3, 25.4) // inch > millimeters
    );

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

    public void normalizeMeasurementUnits(Page<Observation> observations) {
        for(Observation o : observations) {
            conversions.stream()
                    .filter(c -> o.getUnit().equals(c.getOrigin()))
                    .forEach(c -> {
                        o.setValue( o.getValue() * c.getConversionFactor());
                        o.setUnit(c.getTarget());
                    });
        }
    }


}
