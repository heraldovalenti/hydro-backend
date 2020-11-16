package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.DataOrigin;

public class RainAccumulation {

    private DataOrigin dataOrigin;
    private double accumulation;

    public RainAccumulation(
            DataOrigin dataOrigin,
            double accumulation) {
        this.dataOrigin = dataOrigin;
        this.accumulation = accumulation;
    }

    public DataOrigin getDataOrigin() {
        return dataOrigin;
    }

    public double getAccumulation() {
        return accumulation;
    }
}
