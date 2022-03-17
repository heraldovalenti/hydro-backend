package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.exception.AppConfigMissing;
import com.aes.dashboard.backend.model.AppConfig;
import com.aes.dashboard.backend.repository.AppConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AppConfigService {

    private static final String LATEST_DATA_AUTH_TOKEN = "LATEST_DATA_AUTH_TOKEN";
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfigService.class);

    @Autowired
    private AppConfigRepository appConfigRepository;

    public AppConfig getConfig(String name) {
        Optional<AppConfig> result = appConfigRepository.findByName(name);
        return result.orElseThrow(() -> new AppConfigMissing(name));
    }

    public String getAuthToken() {
        return getConfig(LATEST_DATA_AUTH_TOKEN).getValue();
    }

    @Transactional
    public void updateAuthToken(String authToken) {
        AppConfig authTokenConfig = getConfig(LATEST_DATA_AUTH_TOKEN);
        if (authTokenConfig.getValue().equals(authToken)) {
            LOGGER.info("Skipping authToken update as it is still the same token value ({})", truncateAuthToken(authToken));
        } else {
            String oldValue = authTokenConfig.getValue();
            authTokenConfig.setValue(authToken);
            appConfigRepository.save(authTokenConfig);
            LOGGER.info("authToken was updated: was {} and now is {}",
                    truncateAuthToken(oldValue), truncateAuthToken(authToken));
        }
    }

    private String truncateAuthToken(String authToken) {
        int begin = 550, end = 650;
        try {
            return authToken.substring(begin, end);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.warn("Tried to substring authToken between {} and {} but authToken length is {}", begin, end, authToken.length());
        }
        return authToken;
    }
}
