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

    private static final String WEATHERLINK_AUTH_TOKEN = "WEATHERLINK_AUTH_TOKEN";
    private static final String WEATHERUNDERGROUND_AUTH_TOKEN = "WEATHERUNDERGROUND_AUTH_TOKEN";
    private static final String USER_AGENT_HEADER = "USER_AGENT_HEADER";
    private static final String WEATHERCLOUD_COOKIE = "WEATHERCLOUD_COOKIE";
    private static final String PWS_WEATHER_AUTH_PARAM = "PWS_WEATHER_AUTH_PARAM";
    private static final String RP5_COOKIE = "RP5_COOKIE";
    private static final String AES_GSHEET_AUTH_TOKEN = "AES_GSHEET_AUTH_TOKEN";
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfigService.class);

    @Autowired
    private AppConfigRepository appConfigRepository;

    public AppConfig getConfig(String name) {
        Optional<AppConfig> result = appConfigRepository.findByName(name);
        return result.orElseThrow(() -> new AppConfigMissing(name));
    }

    public String getWeatherUndergroundAuthToken() {
        return getConfig(WEATHERUNDERGROUND_AUTH_TOKEN).getValue();
    }

    public String getWeatherlinkAuthToken() {
        return getConfig(WEATHERLINK_AUTH_TOKEN).getValue();
    }

    public String getWeatherCloudCookie() {
        return getConfig(WEATHERCLOUD_COOKIE).getValue();
    }

    public String getPWSWeatherAuthParam() {
        return getConfig(PWS_WEATHER_AUTH_PARAM).getValue();
    }

    public String getRP5Cookie() {
        return getConfig(RP5_COOKIE).getValue();
    }

    public String getUserAgentHeader() {
        return getConfig(USER_AGENT_HEADER).getValue();
    }

    public String getAesGsheetAuthParam() {
        return getConfig(AES_GSHEET_AUTH_TOKEN).getValue();
    }

    @Transactional
    public void updateConfig(String configName, String configValue) {
        AppConfig appConfig = getConfig(configName);
        if (appConfig.getValue().equals(configValue)) {
            LOGGER.info("Skipping {} update as it is still the same token value ({})",
                    configName, configValue);
        } else {
            String oldValue = appConfig.getValue();
            appConfig.setValue(configValue);
            appConfigRepository.save(appConfig);
            LOGGER.info("{} was updated: was {} and now is {}",
                    configName, oldValue, configValue);
        }
    }

}
