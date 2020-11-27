package com.aes.dashboard.backend.service.weatherUndergroundData;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import static com.aes.dashboard.backend.config.GlobalConfigs.DEFAULT_DATE_TIME_FORMAT;

public class WeatherUndergroundStationInfo {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime obsTimeUtc;

    private double lat;
    private double lon;

    private WeatherUndergroundStationObservation imperial;

    public WeatherUndergroundStationInfo() {
    }

    public LocalDateTime getObsTimeUtc() {
        return obsTimeUtc;
    }

    public void setObsTimeUtc(LocalDateTime obsTimeUtc) {
        this.obsTimeUtc = obsTimeUtc;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public WeatherUndergroundStationObservation getImperial() {
        return imperial;
    }

    public void setImperial(WeatherUndergroundStationObservation imperial) {
        this.imperial = imperial;
    }
}
