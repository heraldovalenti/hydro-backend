package com.aes.dashboard.backend.service.snih;

import java.time.LocalDateTime;

public class SNIHObservation {

    private LocalDateTime dateTime;
    private Integer observationCode;
    private Double value;

    public SNIHObservation(LocalDateTime dateTime, Integer observationCode, Double value) {
        this.dateTime = dateTime;
        this.observationCode = observationCode;
        this.value = value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Integer getObservationCode() {
        return observationCode;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "SNIHObservation{" +
                "dateTime=" + dateTime +
                ", observationCode=" + observationCode +
                ", value=" + value +
                '}';
    }
}
