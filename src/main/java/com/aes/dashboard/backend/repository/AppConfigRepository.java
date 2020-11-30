package com.aes.dashboard.backend.repository;

import com.aes.dashboard.backend.model.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {

    Optional<AppConfig> findByName(String name);

}
