package com.aes.dashboard.backend.service.pwsWeatherData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PWSWeatherLoc {

    @JsonProperty("long")
    private Double longitude;

    @JsonProperty("lat")
    private Double latitude;

    public PWSWeatherLoc() {
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "PWSWeatherLoc{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
