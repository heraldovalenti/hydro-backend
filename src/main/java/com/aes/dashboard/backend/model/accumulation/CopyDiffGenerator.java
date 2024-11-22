package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.MeasurementUnitConversion;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.service.MeasurementUnitService;

import java.util.List;
import java.util.Optional;

public class CopyDiffGenerator implements DiffsGenerator {

    private MeasurementUnitService measurementUnitService;

    public CopyDiffGenerator(MeasurementUnitService measurementUnitService) {
        this.measurementUnitService = measurementUnitService;
    }

    @Override
    public void generateDiffs(List<Observation> observationList) {
        for (Observation o : observationList) {
            double diff = o.getValue();

            Optional<MeasurementUnitConversion> optConversion = measurementUnitService.getConversionForObservation(o);
            if (optConversion.isPresent()) {
                diff = optConversion.get().convert(diff);
            }

            o.setDiff(diff);
        }
    }
}
