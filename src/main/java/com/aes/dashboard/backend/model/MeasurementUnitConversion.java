package com.aes.dashboard.backend.model;

public class MeasurementUnitConversion {

    private long originId;
    private long targetId;
    private MeasurementUnit origin;
    private MeasurementUnit target;
    private double conversionFactor;


    public MeasurementUnitConversion(long originId, long targetId, double conversionFactor) {
        this.originId = originId;
        this.targetId = targetId;
        this.conversionFactor = conversionFactor;
    }

    public long getOriginId() {
        return originId;
    }

    public long getTargetId() {
        return targetId;
    }

    public MeasurementUnit getOrigin() {
        return origin;
    }

    public void setOrigin(MeasurementUnit origin) {
        this.origin = origin;
    }

    public MeasurementUnit getTarget() {
        return target;
    }

    public void setTarget(MeasurementUnit target) {
        this.target = target;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}
