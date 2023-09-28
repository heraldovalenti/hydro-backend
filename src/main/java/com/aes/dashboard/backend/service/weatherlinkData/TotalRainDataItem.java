package com.aes.dashboard.backend.service.weatherlinkData;

public class TotalRainDataItem {

    private Double rainCollectorType;
    private String unit;
    private Double convertedVal;
    private Double originalVal;

    public TotalRainDataItem() {
    }

    public Double getRainCollectorType() {
        return rainCollectorType;
    }

    public void setRainCollectorType(Double rainCollectorType) {
        this.rainCollectorType = rainCollectorType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getConvertedVal() {
        return convertedVal;
    }

    public void setConvertedVal(Double convertedVal) {
        this.convertedVal = convertedVal;
    }

    public Double getOriginalVal() {
        return originalVal;
    }

    public void setOriginalVal(Double originalVal) {
        this.originalVal = originalVal;
    }
}
