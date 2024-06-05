package com.aes.dashboard.backend.service.pwsWeatherData;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

import static com.aes.dashboard.backend.config.GlobalConfigs.PWS_WEATHER_DATE_TIME_FORMAT;

public class PWSWeatherOb {

    private Double precipMM;
    private Double humidity;
    private Double dewpointC;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PWS_WEATHER_DATE_TIME_FORMAT)
    private ZonedDateTime recDateTimeISO;

    public PWSWeatherOb() {
    }

    public Double getPrecipMM() {
        return precipMM;
    }

    public void setPrecipMM(Double precipMM) {
        this.precipMM = precipMM;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getDewpointC() {
        return dewpointC;
    }

    public void setDewpointC(Double dewpointC) {
        this.dewpointC = dewpointC;
    }

    public ZonedDateTime getRecDateTimeISO() {
        return recDateTimeISO;
    }

    public void setRecDateTimeISO(ZonedDateTime recDateTimeISO) {
        this.recDateTimeISO = recDateTimeISO;
    }

    @Override
    public String toString() {
        return "PWSWeatherOb{" +
                "precipMM=" + precipMM +
                ", humidity=" + humidity +
                ", dewpointC=" + dewpointC +
                ", recDateTimeISO=" + recDateTimeISO +
                '}';
    }
}
