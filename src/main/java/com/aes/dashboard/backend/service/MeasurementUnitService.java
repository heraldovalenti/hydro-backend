package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.MeasurementUnit;
import com.aes.dashboard.backend.repository.MeasurementUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeasurementUnitService {

    @Autowired
    MeasurementUnitRepository measurementUnitRepository;

    public Optional<MeasurementUnit> getByAlias(String alias) {
        if (alias == null) return Optional.empty();
        String sanitizedAlias = alias.trim();
        sanitizedAlias.replaceAll("\n", "");
        return measurementUnitRepository.findByAlias(sanitizedAlias).stream().findAny();
    }

}
