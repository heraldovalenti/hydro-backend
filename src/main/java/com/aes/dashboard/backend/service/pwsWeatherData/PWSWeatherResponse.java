package com.aes.dashboard.backend.service.pwsWeatherData;

public class PWSWeatherResponse {

    private String id;
    private PWSWeatherOb ob;
    private PWSWeatherLoc loc;
    private PWSWeatherPlace place;
    private PWSWeatherPeriodItem[] periods;

    public PWSWeatherResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PWSWeatherOb getOb() {
        return ob;
    }

    public void setOb(PWSWeatherOb ob) {
        this.ob = ob;
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

    public PWSWeatherPeriodItem[] getPeriods() {
        return periods;
    }

    public void setPeriods(PWSWeatherPeriodItem[] periods) {
        this.periods = periods;
    }
}
