
package com.aes.dashboard.backend.service.rp5;
public enum RP5Period {
    DAY("1"), WEEK("7"), MONTH("30");

    private final String period;

    RP5Period(String period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return this.period;
    }
}

