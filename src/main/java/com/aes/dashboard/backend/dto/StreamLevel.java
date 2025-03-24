package com.aes.dashboard.backend.dto;

import com.aes.dashboard.backend.model.Observation;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class StreamLevel {

    private ObservationWithStation observation;
    private Double streamLevel;
    private String streamName;

    public StreamLevel(Observation observation) {
        this.observation = ObservationWithStation.fromObservation(observation);
        if (this.observation.getStation().getHqModel() != null) {
            this.streamLevel = this.observation.getStation().getHqModel()
                    .calculateH(this.observation.getValue());
            this.streamName = this.observation.getStation().getHqModel()
                    .getStreamName();
        }
    }

    public Observation getObservation() {
        return observation;
    }

    public Double getStreamLevel() {
        return streamLevel;
    }

    public String getStreamName() {
        return streamName;
    }

    @JsonIgnore
    public boolean isValid() {
        return this.getStreamName() != null;
    }
}
