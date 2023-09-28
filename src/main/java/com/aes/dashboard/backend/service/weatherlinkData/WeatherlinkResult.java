package com.aes.dashboard.backend.service.weatherlinkData;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

public class WeatherlinkResult {

    private OIssData oIssData;
    private TotalRainData[] totalRainData;

    public WeatherlinkResult() {
    }

    public OIssData getoIssData() {
        return oIssData;
    }

    public void setoIssData(OIssData oIssData) {
        this.oIssData = oIssData;
    }

    public TotalRainData[] getTotalRainData() {
        return totalRainData;
    }

    public void setTotalRainData(TotalRainData[] totalRainData) {
        this.totalRainData = totalRainData;
    }

    public boolean isDataAvailable() {
        return this.getoIssData() != null || this.getTotalRainData().length > 0;
    }

    public LocalDateTime getObservationTime() {
        if (this.getoIssData() != null) {
            Long ts = this.getoIssData().getTimeStamp();
            return TimestampProvider.getLocalDate(ts);
        }
        return LocalDateTime.now(ZoneId.of(UTC_ZONE_ID));
    }

    public Double getObservationValue() {
        if (this.getoIssData() != null) {
            return this.getoIssData().getRainDay();
        }
        Double result = 0.0;
        for (TotalRainData item : this.getTotalRainData()) {
            result = Math.max(
                    result,
                    item.getTotalForToday().getOriginalVal()
//                    item.getTotalForYear().getOriginalVal()
            );
        }
        return result;
    }
}
