package com.aes.dashboard.backend.service.weatherUndergroundData;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class WeatherUndergroundResult {

    private List<WeatherUndergroundStationInfo> summaries;

    public WeatherUndergroundResult() {
    }

    public List<WeatherUndergroundStationInfo> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<WeatherUndergroundStationInfo> summaries) {
        this.summaries = summaries;
    }

    public Optional<LocalDateTime> getObservationTime() {
        Optional<WeatherUndergroundStationInfo> stationInfo = this.summaries.stream().findAny();
        if (!stationInfo.isPresent()) return Optional.empty();
        return Optional.of(stationInfo.get().getObsTimeUtc());
    }

    public Optional<Double> getObservationValue() {
        Optional<WeatherUndergroundStationInfo> stationInfo = this.summaries.stream().findAny();
        if (!stationInfo.isPresent()) return Optional.empty();
        return Optional.of(stationInfo.get().getImperial().getPrecipTotal());
    }
}
