package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.Observation;

import java.util.List;
import java.util.stream.Collectors;

public class WURainAccumulator extends RainAccumulator {

    @Override
    public double accumulate(List<Observation> observations) {
        double acum = 0;
        List<Observation> sorted = observations.stream().sorted((o1, o2) -> {
            if (o1.getTime().isAfter(o2.getTime())) return 1;
            else if (o1.getTime().isBefore(o2.getTime())) return -1;
            return 0;
        }).collect(Collectors.toList());
        for (int i = 0; i < sorted.size() - 1; i++) {
            Observation o1 = sorted.get(i);
            Observation o2 = sorted.get(i + 1);
            if (o1.getValue() < o2.getValue()) acum += o2.getValue() - o1.getValue();
            if (o1.getValue() > o2.getValue() && o2.getValue() > 0) acum += o2.getValue();
        }
        return acum;
    }
}
