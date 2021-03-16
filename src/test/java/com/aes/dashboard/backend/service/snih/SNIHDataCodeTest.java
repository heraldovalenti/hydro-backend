package com.aes.dashboard.backend.service.snih;

import com.aes.dashboard.backend.model.MeasurementDimension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SNIHDataCodeTest {

    @Test
    public void measurementDimensionForCodeTest() {
        Assertions.assertEquals(
                MeasurementDimension.RAIN,
                SNIHDataCode.measurementDimensionForCode(SNIHDataCode.RAIN_CODE).get());
        Assertions.assertEquals(
                MeasurementDimension.LEVEL,
                SNIHDataCode.measurementDimensionForCode(SNIHDataCode.HEIGHT_CODE).get());
    }

    @Test
    public void codeForMeasurementDimensionTest() {
        Assertions.assertEquals(
                SNIHDataCode.RAIN_CODE,
                SNIHDataCode.codeForMeasurementDimension(MeasurementDimension.RAIN).get());
        Assertions.assertEquals(
                SNIHDataCode.HEIGHT_CODE,
                SNIHDataCode.codeForMeasurementDimension(MeasurementDimension.LEVEL).get());
    }

}