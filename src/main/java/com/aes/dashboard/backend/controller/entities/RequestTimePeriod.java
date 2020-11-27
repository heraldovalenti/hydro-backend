package com.aes.dashboard.backend.controller.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.aes.dashboard.backend.config.GlobalConfigs.DEFAULT_DATE_TIME_FORMAT;

public class RequestTimePeriod {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime from;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime to;

    public RequestTimePeriod(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "AccumulationPeriod{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
