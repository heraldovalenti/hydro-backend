package com.aes.dashboard.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FORECAST_SEQ")
    @SequenceGenerator(name = "FORECAST_SEQ", sequenceName = "SEQUENCE_FORECAST", allocationSize = 20)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "forecast", cascade = CascadeType.ALL)
    private List<ForecastDetail> details;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ForecastSnapshot forecastSnapshot;

    public Forecast() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<ForecastDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ForecastDetail> details) {
        this.details = details;
    }

    @JsonIgnore
    public ForecastSnapshot getForecastsSnapshot() {
        return forecastSnapshot;
    }

    public void setForecastsSnapshot(ForecastSnapshot forecastSnapshot) {
        this.forecastSnapshot = forecastSnapshot;
    }
}
