package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.exception.WrongSortException;
import com.aes.dashboard.backend.model.MeasurementUnitConversion;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.service.MeasurementUnitService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleDiffGenerator implements DiffsGenerator {

    private MeasurementUnitService measurementUnitService;

    public SimpleDiffGenerator(MeasurementUnitService measurementUnitService) {
        this.measurementUnitService = measurementUnitService;
    }

    @Override
    public void generateDiffs(List<Observation> observationList) {
        if (observationList.isEmpty() || observationList.size() == 1) return;
        for (int i = 0; i < observationList.size() - 1; i++) {
            double diff = 0.0;
            Observation o1 = observationList.get(i);
            Observation o2 = observationList.get(i + 1);

            if (o1.getTime().isBefore(o2.getTime())) {
                throw new WrongSortException(o1, o2);
            }
            diff = o1.getValue() - o2.getValue();

            Optional<MeasurementUnitConversion> optConversion = measurementUnitService.getConversionForObservation(o1);
            if (optConversion.isPresent()) {
                diff = optConversion.get().convert(diff);
            }

            o1.setDiff(diff);
        }
    }
}
