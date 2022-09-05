package com.aes.dashboard.backend.service.weatherlinkData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OIssData {

    @JsonProperty(value = "time_stamp")
    private Long timeStamp;
    @JsonProperty(value = "rain_day")
    private Double rainDay;
    @JsonProperty(value = "rain_month")
    private Double rainMonth;
    @JsonProperty(value = "rain_year")
    private Double rainYear;
    @JsonProperty(value = "et_day")
    private Double etDay;
    @JsonProperty(value = "et_month")
    private Double etMonth;
    @JsonProperty(value = "et_year")
    private Double etYear;

    public OIssData() {
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getRainDay() {
        return rainDay;
    }

    public void setRainDay(Double rainDay) {
        this.rainDay = rainDay;
    }

    public Double getRainMonth() {
        return rainMonth;
    }

    public void setRainMonth(Double rainMonth) {
        this.rainMonth = rainMonth;
    }

    public Double getRainYear() {
        return rainYear;
    }

    public void setRainYear(Double rainYear) {
        this.rainYear = rainYear;
    }

    public Double getEtDay() {
        return etDay;
    }

    public void setEtDay(Double etDay) {
        this.etDay = etDay;
    }

    public Double getEtMonth() {
        return etMonth;
    }

    public void setEtMonth(Double etMonth) {
        this.etMonth = etMonth;
    }

    public Double getEtYear() {
        return etYear;
    }

    public void setEtYear(Double etYear) {
        this.etYear = etYear;
    }
}
