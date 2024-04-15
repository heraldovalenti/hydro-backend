package com.aes.dashboard.backend.dto;

import com.aes.dashboard.backend.service.weatherlinkData.OIssData;
import com.aes.dashboard.backend.service.weatherlinkData.TotalRainData;
import com.aes.dashboard.backend.service.weatherlinkData.WeatherlinkResult;

public class WeatherLinkHealthCheckResult {

    public WeatherLinkHealthCheckResult() {
    }

    private Double observationValue;
    private OIssData oIssData;
    private TotalRainData[] totalRainData;

    public Double getObservationValue() {
        return observationValue;
    }

    public void setObservationValue(Double observationValue) {
        this.observationValue = observationValue;
    }

    public OIssData getoIssData() {
        return oIssData;
    }

    public void setoIssData(OIssData oIssData) {
        this.oIssData = oIssData;
    }

    public TotalRainData[] getTotalRainData() {
        return totalRainData;
    }

    public void setTotalRainData(TotalRainData[] totalRainData) {
        this.totalRainData = totalRainData;
    }

    public static WeatherLinkHealthCheckResult fromWeatherlinkResult(WeatherlinkResult weatherlinkResult) {
        WeatherLinkHealthCheckResult result = new WeatherLinkHealthCheckResult();
        result.observationValue = weatherlinkResult.getObservationValue();
        result.oIssData = weatherlinkResult.getoIssData();
        result.totalRainData = weatherlinkResult.getTotalRainData();
        return result;
    }
}
