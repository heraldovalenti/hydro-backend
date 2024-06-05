package com.aes.dashboard.backend.service.pwsWeatherData;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

import static com.aes.dashboard.backend.config.GlobalConfigs.PWS_WEATHER_DATE_TIME_FORMAT;

public class PWSWeatherResponse {

    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PWS_WEATHER_DATE_TIME_FORMAT )
    private ZonedDateTime obDateTime;

    private PWSWeatherOb ob;
    private PWSWeatherLoc loc;
    private PWSWeatherPlace place;

    public PWSWeatherResponse() {
    }

    public ZonedDateTime getObDateTime() {
        return obDateTime;
    }

    public void setObDateTime(ZonedDateTime obDateTime) {
        this.obDateTime = obDateTime;
    }

    public PWSWeatherOb getOb() {
        return ob;
    }

    public void setOb(PWSWeatherOb ob) {
        this.ob = ob;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PWSWeatherLoc getLoc() {
        return loc;
    }

    public void setLoc(PWSWeatherLoc loc) {
        this.loc = loc;
    }

    public PWSWeatherPlace getPlace() {
        return place;
    }

    public void setPlace(PWSWeatherPlace place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "PWSWeatherResponse{" +
                "id='" + id + '\'' +
                ", obDateTime=" + obDateTime +
                ", ob=" + ob +
                ", loc=" + loc +
                ", place=" + place +
                '}';
    }
}
