package com.aes.dashboard.backend.repository;

import com.aes.dashboard.backend.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> { }
