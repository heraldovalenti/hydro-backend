package com.aes.dashboard.backend.service.rp5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.aes.dashboard.backend.config.GlobalConfigs.SALTA_ZONE_ID;
import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

public class RP5Row {

    private static final Logger LOGGER = LoggerFactory.getLogger(RP5Row.class);

    private String year;
    private String date;
    private String month;
    private String hour;
    private String rain;
    private String period;

    public RP5Row(String year, String date, String month, String hour, String rain, String period) {
        this.year = year;
        this.date = date;
        this.month = month;
        this.hour = hour;
        this.rain = rain;
        this.period = period;
    }

    public boolean isValidDateTime() {
        return getDateTime() != null;
    }
    public LocalDateTime getDateTime() {
        try {
            Month month = null;
            if ("enero".equals(this.month)) month = Month.JANUARY;
            else if ("febrero".equals(this.month)) month = Month.FEBRUARY;
            else if ("marzo".equals(this.month)) month = Month.MARCH;
            else if ("abril".equals(this.month)) month = Month.APRIL;
            else if ("mayo".equals(this.month)) month = Month.MAY;
            else if ("junio".equals(this.month)) month = Month.JUNE;
            else if ("julio".equals(this.month)) month = Month.JULY;
            else if ("agosto".equals(this.month)) month = Month.AUGUST;
            else if ("septiembre".equals(this.month)) month = Month.SEPTEMBER;
            else if ("octubre".equals(this.month)) month = Month.OCTOBER;
            else if ("noviembre".equals(this.month)) month = Month.NOVEMBER;
            else if ("diciembre".equals(this.month)) month = Month.DECEMBER;
            Integer year = Integer.parseInt(this.year),
                    date = Integer.parseInt(this.date),
                    hour = Integer.parseInt(this.hour);

            LocalDateTime result = LocalDateTime.of(year, month, date, hour, 0);
            ZonedDateTime saltaTime = ZonedDateTime.of(result, ZoneId.of(SALTA_ZONE_ID));
            ZonedDateTime utcTime = saltaTime.withZoneSameInstant(ZoneId.of(UTC_ZONE_ID));
            return utcTime.toLocalDateTime();
        } catch (Exception e) {
            LOGGER.debug("Failed to parse date for year={} month={} date={} hour={}",
                    this.year, this.month, this.date, this.hour);
            return null;
        }
    }

    public boolean hasData() {
        return isValidDateTime() && isValidPeriod();
    }

    public boolean isValidPeriod() {
        return getPeriod() == 6 || getPeriod() == 24;
    }

    public boolean isValidRain() {
        if (this.rain == null || this.rain.isEmpty()) return false;
        if (isZeroRain()) return true;
        try {
            Double.parseDouble(this.rain);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getPeriod() {
        int result = 0;
        try {
            return Integer.parseInt(this.period);
        } catch (NumberFormatException e) {
            LOGGER.debug("Tried to parse period from invalid value: {}", this.period);
        }
        if (isValidDateTime() && getDateTime().getHour() == 12) { // hour 12 in UTC == hour 9 in Salta/Arg
            return 24;
        }
        return result;
    }

    public boolean is24HourPeriod() {
        return isValidPeriod() && this.getPeriod() == 24;
    }

    private boolean isZeroRain() {
        List<String> zeroRains = List.of("Rastros", "Sin precipitación");
        return zeroRains.contains(this.rain);
    }

    public Double getRain() {
        if (isZeroRain()) return 0.0;
        try {
            return Double.parseDouble(this.rain);
        } catch (NumberFormatException e) {
            LOGGER.warn("Tried to parse rain from invalid value: {}", this.rain);
            return 0.0;
        }
    }

    public RP5Row replaceWith(Double rain, Integer period) {
        return new RP5Row(
                this.year, this.date, this.month, this.hour,
                rain.toString(), period.toString());
    }

    @Override
    public String toString() {
        return "RP5Row{" +
                "year='" + year + '\'' +
                ", date='" + date + '\'' +
                ", month='" + month + '\'' +
                ", hour='" + hour + '\'' +
                ", rain='" + rain + '\'' +
                ", period='" + period + '\'' +
                "} getPeriod()=" + this.getPeriod();
    }
}
