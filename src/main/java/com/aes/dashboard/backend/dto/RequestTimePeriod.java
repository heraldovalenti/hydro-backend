package com.aes.dashboard.backend.dto;

import com.aes.dashboard.backend.exception.IncorrectDateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.aes.dashboard.backend.config.GlobalConfigs.DEFAULT_DATE_TIME_FORMAT;
import static com.aes.dashboard.backend.config.GlobalConfigs.REQUEST_PARAM_DATE_INPUT_FORMAT;

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

    public static RequestTimePeriod of(String from, String to) {
        DateTimeFormatter inDTF = DateTimeFormatter.ofPattern(REQUEST_PARAM_DATE_INPUT_FORMAT);
        try {
            LocalDateTime fromDate = LocalDateTime.parse(from, inDTF),
                    toDate = LocalDateTime.parse(to, inDTF);
            return new RequestTimePeriod(fromDate, toDate);
        } catch (DateTimeParseException ex) {
            throw new IncorrectDateTimeFormat(
                    ex.getParsedString(),
                    ex.getErrorIndex(),
                    REQUEST_PARAM_DATE_INPUT_FORMAT
            );
        }
    }
}
