package com.aes.dashboard.backend.service.weatherCloudData;

public class DeviceInfo {

    private long account;
    private Double altitude;
    private String city;
    private Long update;

    public DeviceInfo() {
    }

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public String getCity() {
        return city.trim();
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getUpdate() {
        return update;
    }

    public void setUpdate(Long update) {
        this.update = update;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "account=" + account +
                ", altitude=" + altitude +
                ", city='" + city + '\'' +
                ", update=" + update +
                '}';
    }
}
