package com.aes.dashboard.backend.service.weatherCloudData;

public class ValuesInfo {

    private Double bar;
    private Integer hum;
    private Double rain;
    private Double temp;

    public ValuesInfo() {
    }

    public Double getBar() {
        return bar;
    }

    public void setBar(Double bar) {
        this.bar = bar;
    }

    public Integer getHum() {
        return hum;
    }

    public void setHum(Integer hum) {
        this.hum = hum;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "ValuesInfo{" +
                "bar=" + bar +
                ", hum=" + hum +
                ", rain=" + rain +
                ", temp=" + temp +
                '}';
    }
}
