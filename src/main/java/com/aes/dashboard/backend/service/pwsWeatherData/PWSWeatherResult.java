package com.aes.dashboard.backend.service.pwsWeatherData;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class PWSWeatherResult {

    private Boolean success;
    @JsonDeserialize(using = PWSWeatherResponseDeserializer.class)
    private PWSWeatherResponse response;
    private PWSWeatherError error;

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

    public PWSWeatherError getError() {
        return error;
    }

    public void setError(PWSWeatherError error) {
        this.error = error;
    }
}
