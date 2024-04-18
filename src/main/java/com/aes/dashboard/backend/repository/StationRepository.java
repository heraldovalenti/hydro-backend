package com.aes.dashboard.backend.repository;

import com.aes.dashboard.backend.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {

    List<Station> findAllByActive(Boolean active);

}
