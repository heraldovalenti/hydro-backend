package com.aes.dashboard.backend.service.gsheet;

import com.aes.dashboard.backend.config.GlobalConfigs;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class GSheetObservation {


    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = GlobalConfigs.AES_GSHEET_DATE_TIME_FORMAT,
            timezone = GlobalConfigs.SALTA_ZONE_ID)
    private ZonedDateTime date;
    private double height;

    public GSheetObservation() {
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public LocalDateTime getUTCDate() {
        ZoneId utcZone = ZoneId.of(GlobalConfigs.UTC_ZONE_ID);
        return this.date.withZoneSameInstant(utcZone).toLocalDateTime();
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "GSheetDataItem{" +
                "date=" + date +
                ", height=" + height +
                '}';
    }
}
