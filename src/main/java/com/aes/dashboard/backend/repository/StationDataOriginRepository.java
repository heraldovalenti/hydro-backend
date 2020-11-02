package com.aes.dashboard.backend.repository;

import com.aes.dashboard.backend.model.DataOrigin;
import com.aes.dashboard.backend.model.StationDataOrigin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationDataOriginRepository extends JpaRepository<StationDataOrigin, Long> {

    List<StationDataOrigin> findByDataOrigin(DataOrigin dataOrigin);

}
