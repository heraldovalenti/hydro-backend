package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.exception.AppConfigMissing;
import com.aes.dashboard.backend.model.AppConfig;
import com.aes.dashboard.backend.repository.AppConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppConfigService {

    private static final String LATEST_DATA_AUTH_TOKEN = "LATEST_DATA_AUTH_TOKEN";

    @Autowired
    private AppConfigRepository appConfigRepository;

    public String getAuthToken() {
        Optional<AppConfig> result = appConfigRepository.findByName(LATEST_DATA_AUTH_TOKEN);
        return result.orElseThrow(() -> new AppConfigMissing(LATEST_DATA_AUTH_TOKEN)).getValue();
    }
}
