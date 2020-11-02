package com.aes.dashboard.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class StationDataOrigin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String externalStationId;

    @JsonManagedReference
    @ManyToOne( optional = false, fetch = FetchType.EAGER)
    private Station station;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private MeasurementDimension dimension;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private DataOrigin dataOrigin;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private MeasurementUnit defaultUnit;

    public StationDataOrigin() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExternalStationId() {
        return externalStationId;
    }

    public void setExternalStationId(String externalStationId) {
        this.externalStationId = externalStationId;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
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

    public MeasurementUnit getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(MeasurementUnit defaultUnit) {
        this.defaultUnit = defaultUnit;
    }
}
