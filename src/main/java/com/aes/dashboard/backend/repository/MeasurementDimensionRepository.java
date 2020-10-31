package com.aes.dashboard.backend.repository;

import com.aes.dashboard.backend.model.MeasurementDimension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementDimensionRepository extends JpaRepository<MeasurementDimension, Long> { }
