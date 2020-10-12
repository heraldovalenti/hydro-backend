package com.aes.dashboard.backend.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Observation {

    @Id
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Station station;

    @Column(nullable = false)
    private int value;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private String dimension;

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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
