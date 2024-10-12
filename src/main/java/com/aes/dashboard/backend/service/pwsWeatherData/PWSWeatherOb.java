package com.aes.dashboard.backend.service.pwsWeatherData;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

import static com.aes.dashboard.backend.config.GlobalConfigs.PWS_WEATHER_DATE_TIME_FORMAT;

public class PWSWeatherOb {

    private Double precipSinceLastObMM;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PWS_WEATHER_DATE_TIME_FORMAT)
    private ZonedDateTime dateTimeISO;

    public PWSWeatherOb() {
    }

    public Double getPrecipSinceLastObMM() {
        if (this.precipSinceLastObMM == null) {
            return new Double(0);
        }
        return precipSinceLastObMM;
    }

    public void setPrecipSinceLastObMM(Double precipSinceLastObMM) {
        this.precipSinceLastObMM = precipSinceLastObMM;
    }

    public ZonedDateTime getDateTimeISO() {
        return dateTimeISO;
    }

    public void setDateTimeISO(ZonedDateTime dateTimeISO) {
        this.dateTimeISO = dateTimeISO;
    }
}
