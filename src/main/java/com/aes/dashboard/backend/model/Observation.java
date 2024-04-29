package com.aes.dashboard.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.aes.dashboard.backend.config.GlobalConfigs.DEFAULT_DATE_TIME_FORMAT;

@Entity
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_DATE_TIME_FORMAT)
    @Column(nullable = false)
    private LocalDateTime time;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Station station;

    @Column(nullable = false)
    private double value;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private MeasurementUnit unit;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private MeasurementDimension dimension;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private DataOrigin dataOrigin;

    @Column(nullable = true)
    private Double diff;

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

    public DataOrigin getDataOrigin() {
        return dataOrigin;
    }

    public void setDataOrigin(DataOrigin dataOrigin) {
        this.dataOrigin = dataOrigin;
    }

    public Double getDiff() {
        return diff;
    }

    public void setDiff(Double diff) {
        this.diff = diff;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "id=" + id +
                ", time=" + time +
                ", station=" + station.getDescription() +
                ", value=" + value +
                ", unit=" + unit.getDescription() +
                ", dimension=" + dimension.getDescription() +
                ", dataOrigin=" + dataOrigin.getDescription() +
                ", diff=" + diff +
                '}';
    }

    public Observation latest(Observation o) {
        if (o != null && o.getTime().isAfter(this.time)) {
            return o;
        }
        return this;
    }
}
