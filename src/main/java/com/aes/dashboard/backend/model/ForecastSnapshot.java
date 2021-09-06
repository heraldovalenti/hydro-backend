package com.aes.dashboard.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.aes.dashboard.backend.config.GlobalConfigs.DEFAULT_DATE_TIME_FORMAT;

@Entity
public class ForecastSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_DATE_TIME_FORMAT)
    @Column(nullable = false)
    private LocalDateTime time;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "forecastSnapshot", cascade = CascadeType.ALL)
    private List<Forecast> forecasts;

    public ForecastSnapshot() {
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

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
