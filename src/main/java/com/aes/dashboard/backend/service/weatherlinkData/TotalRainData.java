package com.aes.dashboard.backend.service.weatherlinkData;

public class TotalRainData {

    private long iLogicalSensorId;
    private TotalRainDataItem totalForToday;
    private TotalRainDataItem totalForMonth;
    private TotalRainDataItem totalForYear;

    public TotalRainData() {
    }

    public long getiLogicalSensorId() {
        return iLogicalSensorId;
    }

    public void setiLogicalSensorId(long iLogicalSensorId) {
        this.iLogicalSensorId = iLogicalSensorId;
    }

    public TotalRainDataItem getTotalForToday() {
        return totalForToday;
    }

    public void setTotalForToday(TotalRainDataItem totalForToday) {
        this.totalForToday = totalForToday;
    }

    public TotalRainDataItem getTotalForMonth() {
        return totalForMonth;
    }

    public void setTotalForMonth(TotalRainDataItem totalForMonth) {
        this.totalForMonth = totalForMonth;
    }

    public TotalRainDataItem getTotalForYear() {
        return totalForYear;
    }

    public void setTotalForYear(TotalRainDataItem totalForYear) {
        this.totalForYear = totalForYear;
    }
}
