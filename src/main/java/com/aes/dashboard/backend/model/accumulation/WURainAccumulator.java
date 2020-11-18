package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.Observation;

import java.util.List;

public class WURainAccumulator extends RainAccumulator {

    @Override
    public double accumulate(List<Observation> observations) {
        double acum = 0;
        List<Observation> sorted = this.sortObservations(observations);
        for (int i = 0; i < sorted.size() - 1; i++) {
            Observation o1 = sorted.get(i);
            Observation o2 = sorted.get(i + 1);
            if (o1.getValue() < o2.getValue()) acum += o2.getValue() - o1.getValue();
            if (o1.getValue() > o2.getValue() && o2.getValue() > 0) acum += o2.getValue();
        }
        return acum;
    }
}
