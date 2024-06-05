package com.aes.dashboard.backend.service.pwsWeatherData;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

public class PWSWeatherResult {

    private Boolean success;
    private PWSWeatherResponse response;

    public PWSWeatherResult() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public PWSWeatherResponse getResponse() {
        return response;
    }

    public void setResponse(PWSWeatherResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "PWSWeatherResult{" +
                "success=" + success +
                ", response=" + response +
                '}';
    }

    public LocalDateTime getObservationTime() {
        if (this.getResponse() != null && this.getResponse().getObDateTime() != null) {
            return this.getResponse().getObDateTime().withZoneSameInstant(ZoneId.of(UTC_ZONE_ID)).toLocalDateTime();
        }
        return LocalDateTime.now(ZoneId.of(UTC_ZONE_ID));
    }

    public Double getObservationValue() {
        if (this.getResponse() != null
                && this.getResponse().getOb() != null
                && this.getResponse().getOb().getPrecipMM() != null) {
            return this.getResponse().getOb().getPrecipMM();
        }
        return 0.0;
    }
}
