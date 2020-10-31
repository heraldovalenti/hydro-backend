package com.aes.dashboard.backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "station")
    private List<StationDataOrigin> stationDataOriginList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "station")
    private List<Observation> observationList;

    public Station() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<StationDataOrigin> getStationDataOriginList() {
        return stationDataOriginList;
    }

    public void setStationDataOriginList(List<StationDataOrigin> stationDataOriginList) {
        this.stationDataOriginList = stationDataOriginList;
    }

    public List<Observation> getObservationList() {
        return observationList;
    }

    public void setObservationList(List<Observation> observationList) {
        this.observationList = observationList;
    }
}
