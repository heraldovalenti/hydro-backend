package com.aes.dashboard.backend.model.date;

import java.time.LocalDateTime;

public class DateRounder {

    private int dateMinutesRound;

    public DateRounder(int dateMinutesRound) {
        this.dateMinutesRound = dateMinutesRound;
    }

    public LocalDateTime roundDateTime(LocalDateTime date) {
        int minuteDiff = date.getMinute() % this.dateMinutesRound;
        if (date.getSecond() == 0 && minuteDiff == 0) {
            return date;
        }
        LocalDateTime plusMinutes = date.plusMinutes(this.dateMinutesRound);
        LocalDateTime roundToSeconds = plusMinutes.withSecond(0);
        LocalDateTime roundToMinutes = roundToSeconds.plusMinutes(-minuteDiff);
        return roundToMinutes;
    }
}
