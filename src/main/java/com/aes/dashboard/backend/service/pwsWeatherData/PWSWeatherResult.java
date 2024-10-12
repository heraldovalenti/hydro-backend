package com.aes.dashboard.backend.service.pwsWeatherData;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

public class PWSWeatherResult {

    private Boolean success;
    private PWSWeatherResponse response;

    public PWSWeatherResult() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public PWSWeatherResponse getResponse() {
        return response;
    }

    public void setResponse(PWSWeatherResponse response) {
        this.response = response;
    }
}
