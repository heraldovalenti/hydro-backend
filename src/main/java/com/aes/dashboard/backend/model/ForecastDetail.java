package com.aes.dashboard.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.aes.dashboard.backend.config.GlobalConfigs.ISO_DATE_TIME_FORMAT;

@Entity
public class ForecastDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FORECAST_DETAIL_SEQ")
    @SequenceGenerator(name = "FORECAST_DETAIL_SEQ", sequenceName = "SEQUENCE_FORECAST_DETAIL", allocationSize = 1312)
    @Column(nullable = false)
    private long id;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Forecast forecast;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ISO_DATE_TIME_FORMAT)
    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false)
    private double value;

    public ForecastDetail() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonIgnore
    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
