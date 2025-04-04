package com.aes.dashboard.backend.service.pwsWeatherData;

public class PWSWeatherError {

    private String code;
    private String description;

    public PWSWeatherError() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
