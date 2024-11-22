package com.aes.dashboard.backend.model.accumulation;

import com.aes.dashboard.backend.model.Observation;

import java.util.List;

public interface DiffsGenerator {

    void generateDiffs(List<Observation> observationList);

}
