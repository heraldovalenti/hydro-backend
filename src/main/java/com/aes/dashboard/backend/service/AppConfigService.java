package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.controller.entities.AuthTokens;
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

    private static final String LATEST_DATA_FED_AUTH = "LATEST_DATA_FED_AUTH";
    private static final String LATEST_DATA_RT_FA = "LATEST_DATA_RT_FA";
    private static final String WEATHERLINK_AUTH_TOKEN = "WEATHERLINK_AUTH_TOKEN";
    private static final String WEATHERUNDERGROUND_AUTH_TOKEN = "WEATHERUNDERGROUND_AUTH_TOKEN";
    private static final String USER_AGENT_HEADER = "USER_AGENT_HEADER";
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfigService.class);

    @Autowired
    private AppConfigRepository appConfigRepository;

    public AppConfig getConfig(String name) {
        Optional<AppConfig> result = appConfigRepository.findByName(name);
        return result.orElseThrow(() -> new AppConfigMissing(name));
    }

    public AuthTokens getAuthTokens() {
        String fedAuth = getConfig(LATEST_DATA_FED_AUTH).getValue();
        String rtFa = getConfig(LATEST_DATA_RT_FA).getValue();
        return new AuthTokens(fedAuth, rtFa);
    }

    public String getWeatherUndergroundAuthToken() {
        return getConfig(WEATHERUNDERGROUND_AUTH_TOKEN).getValue();
    }

    public String getWeatherlinkAuthToken() {
        return getConfig(WEATHERLINK_AUTH_TOKEN).getValue();
    }

    public String getUserAgentHeader() {
        return getConfig(USER_AGENT_HEADER).getValue();
    }


    @Transactional
    public void updateFedAuth(String fedAuth) {
        updateAuthToken(LATEST_DATA_FED_AUTH, fedAuth);
    }

    @Transactional
    public void updateRtFa(String rtFa) {
        updateAuthToken(LATEST_DATA_RT_FA, rtFa);
    }

    @Transactional
    private void updateAuthToken(String authTokenName, String authTokenValue) {
        AppConfig authTokenConfig = getConfig(authTokenName);
        if (authTokenConfig.getValue().equals(authTokenValue)) {
            LOGGER.info("Skipping {} update as it is still the same token value ({})",
                    authTokenName, authTokenValue);
        } else {
            String oldValue = authTokenConfig.getValue();
            authTokenConfig.setValue(authTokenValue);
            appConfigRepository.save(authTokenConfig);
            LOGGER.info("{} was updated: was {} and now is {}",
                    authTokenName, oldValue, authTokenValue);
        }
    }

}
