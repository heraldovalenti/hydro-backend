package com.aes.dashboard.backend.model;

import javax.persistence.*;

@Entity(name = "hq_model")
public class HQModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @ManyToOne( optional = false, fetch = FetchType.EAGER)
    private Station station;

    @Column(nullable = false)
    private double hLimit;

    @Column(nullable = false)
    private double hOffset;

    @Column(nullable = false)
    private double lowPassFactor;

    @Column(nullable = false)
    private double highPassFactor;

    @Column(nullable = false)
    private double lowPassOffset;

    @Column(nullable = false)
    private double highPassOffset;

    @Column(nullable = false)
    private double lowPassExponent;

    @Column(nullable = false)
    private double highPassExponent;

    @Column(nullable = true)
    private String streamName;

    public HQModel() {
        this.hOffset = 0;
        this.lowPassOffset = 0;
        this.highPassOffset = 0;

        this.lowPassFactor = 0;
        this.highPassFactor = 0;

        this.lowPassExponent = 1;
        this.highPassExponent = 1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public double gethLimit() {
        return hLimit;
    }

    public void sethLimit(double hLimit) {
        this.hLimit = hLimit;
    }

    public double gethOffset() {
        return hOffset;
    }

    public void sethOffset(double hOffset) {
        this.hOffset = hOffset;
    }

    public double getLowPassFactor() {
        return lowPassFactor;
    }

    public void setLowPassFactor(double lowPassFactor) {
        this.lowPassFactor = lowPassFactor;
    }

    public double getHighPassFactor() {
        return highPassFactor;
    }

    public void setHighPassFactor(double highPassFactor) {
        this.highPassFactor = highPassFactor;
    }

    public double getLowPassOffset() {
        return lowPassOffset;
    }

    public void setLowPassOffset(double lowPassOffset) {
        this.lowPassOffset = lowPassOffset;
    }

    public double getHighPassOffset() {
        return highPassOffset;
    }

    public void setHighPassOffset(double highPassOffset) {
        this.highPassOffset = highPassOffset;
    }

    public double getLowPassExponent() {
        return lowPassExponent;
    }

    public void setLowPassExponent(double lowPassExponent) {
        this.lowPassExponent = lowPassExponent;
    }

    public double getHighPassExponent() {
        return highPassExponent;
    }

    public void setHighPassExponent(double highPassExponent) {
        this.highPassExponent = highPassExponent;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public double calculateH(double hSensor) {
        return hSensor + hOffset;
    }

    public double calculateQ(double hSensor) {
        double h = calculateH(hSensor);
        double q = 0,
                qFactor = lowPassFactor,
                base = h + lowPassOffset,
                exp = lowPassExponent;
        if (h >= hLimit) {
            qFactor = highPassFactor;
            base = h + highPassOffset;
            exp = highPassExponent;
        }
        if (base > 0) q = qFactor * Math.pow(base, exp);
        return q;
    }
}