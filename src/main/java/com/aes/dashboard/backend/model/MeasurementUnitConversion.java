package com.aes.dashboard.backend.model;

public class MeasurementUnitConversion {

    private long origin;
    private long target;
    private double conversionFactor;


    public MeasurementUnitConversion(long originId, long targetId, double conversionFactor) {
        this.origin = originId;
        this.target = targetId;
        this.conversionFactor = conversionFactor;
    }

    public MeasurementUnit getOrigin() {
        MeasurementUnit measurementUnit = new MeasurementUnit();
        measurementUnit.setId(this.origin);
        return measurementUnit;
    }

    public MeasurementUnit getTarget() {
        MeasurementUnit measurementUnit = new MeasurementUnit();
        measurementUnit.setId(this.target);
        return measurementUnit;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}
