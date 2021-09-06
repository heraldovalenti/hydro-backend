package com.aes.dashboard.backend.repository;

import com.aes.dashboard.backend.model.ForecastSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForecastSnapshotRepository extends JpaRepository<ForecastSnapshot, Long> {

    Optional<ForecastSnapshot> findFirstByOrderByTimeDesc();

}
