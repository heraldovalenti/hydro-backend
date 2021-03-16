package com.aes.dashboard.backend.service.snih;

import com.aes.dashboard.backend.model.MeasurementDimension;

import java.util.Optional;

public enum SNIHDataCode {

    HEIGHT_CODE(1),
    RAIN_CODE(20);

    public final Integer code;

    SNIHDataCode(Integer code) {
        this.code = code;
    }

    public static Optional<MeasurementDimension> measurementDimensionForCode(
            SNIHDataCode snihDataCode) {
        switch (snihDataCode) {
            case RAIN_CODE: return Optional.of(MeasurementDimension.RAIN);
            case HEIGHT_CODE: return Optional.of(MeasurementDimension.LEVEL);
        }
        return Optional.empty();
    }

    public static Optional<SNIHDataCode> codeForMeasurementDimension(
            MeasurementDimension measurementDimension) {
        if (MeasurementDimension.RAIN.equals(measurementDimension)) {
            return Optional.of(RAIN_CODE);
        } else if (MeasurementDimension.LEVEL.equals(measurementDimension)) {
            return Optional.of(HEIGHT_CODE);
        }
        return Optional.empty();
    }

}
