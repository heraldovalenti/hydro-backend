package com.aes.dashboard.backend.repository;

import com.aes.dashboard.backend.model.DataOrigin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataOriginRepository extends JpaRepository<DataOrigin, Long> {

    List<DataOrigin> findByDescription(String description);

}
