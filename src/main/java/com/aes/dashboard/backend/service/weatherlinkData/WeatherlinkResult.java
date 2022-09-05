package com.aes.dashboard.backend.service.weatherlinkData;

import java.time.LocalDateTime;

public class WeatherlinkResult {

    private OIssData oIssData;

    public WeatherlinkResult() {
    }

    public OIssData getoIssData() {
        return oIssData;
    }

    public void setoIssData(OIssData oIssData) {
        this.oIssData = oIssData;
    }

    public LocalDateTime getObservationTime() {
        Long ts = this.getoIssData().getTimeStamp();
        return TimestampProvider.getLocalDate(ts);
    }

    public Double getObservationValue() {
        return this.getoIssData().getRainDay();
    }
}
