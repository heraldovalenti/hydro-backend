package com.aes.dashboard.backend.model;

public class MeasurementUnitConversion {

    private MeasurementUnit origin;
    private MeasurementUnit target;
    private double conversionFactor;


    public MeasurementUnitConversion(MeasurementUnit origin, MeasurementUnit target, double conversionFactor) {
        this.origin = origin;
        this.target = target;
        this.conversionFactor = conversionFactor;
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

    public double convert(double input) {
        return this.conversionFactor * input;
    }
}
