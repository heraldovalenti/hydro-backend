package com.aes.dashboard.backend.service.weatherCloudData;

public class WeatherCloudResult {

    private DeviceInfo device;
    private ValuesInfo values;

    public WeatherCloudResult() {
    }

    public DeviceInfo getDevice() {
        return device;
    }

    public void setDevice(DeviceInfo device) {
        this.device = device;
    }

    public ValuesInfo getValues() {
        return values;
    }

    public void setValues(ValuesInfo values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "WeatherCloudResult{" +
                "device=" + device +
                ", values=" + values +
                '}';
    }
}
