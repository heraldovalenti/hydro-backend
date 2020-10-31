package com.aes.dashboard.backend.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Station station;

    @Column(nullable = false)
    private double value;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private MeasurementUnit unit;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private MeasurementDimension dimension;

    public Observation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public MeasurementUnit getUnit() {
        return unit;
    }

    public void setUnit(MeasurementUnit unit) {
        this.unit = unit;
    }

    public MeasurementDimension getDimension() {
        return dimension;
    }

    public void setDimension(MeasurementDimension dimension) {
        this.dimension = dimension;
    }
}
