package com.aes.dashboard.backend.repository;

import com.aes.dashboard.backend.model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservationRepository extends JpaRepository<Observation, Long> { }
