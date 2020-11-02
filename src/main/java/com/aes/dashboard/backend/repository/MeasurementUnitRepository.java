package com.aes.dashboard.backend.repository;

import com.aes.dashboard.backend.model.MeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementUnitRepository extends JpaRepository<MeasurementUnit, Long> {

    List<MeasurementUnit> findByAlias(String alias);

}
