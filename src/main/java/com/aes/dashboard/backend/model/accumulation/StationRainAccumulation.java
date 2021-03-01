package com.aes.dashboard.backend.model.accumulation;

import java.util.List;

public class StationRainAccumulation {

    private long stationId;
    private List<RainAccumulation> rainAccumulationList;

    public StationRainAccumulation(long stationId, List<RainAccumulation> rainAccumulationList) {
        this.stationId = stationId;
        this.rainAccumulationList = rainAccumulationList;
    }

    public long getStationId() {
        return stationId;
    }

    public List<RainAccumulation> getRainAccumulationList() {
        return rainAccumulationList;
    }
}
