package com.aes.dashboard.backend.dto;

import com.aes.dashboard.backend.model.Observation;

public class StreamLevel {

    private ObservationWithStation observation;
    private Double streamLevel;

    public StreamLevel(Observation observation) {
        this.observation = ObservationWithStation.fromObservation(observation);
        if (this.observation.getStation().getHqModel() != null) {
            this.streamLevel = this.observation.getStation().getHqModel()
                    .calculateH(this.observation.getValue());
        }
    }

    public Observation getObservation() {
        return observation;
    }

    public Double getStreamLevel() {
        return streamLevel;
    }
}
