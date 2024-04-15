package com.aes.dashboard.backend.dto;

import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ObservationWithStation extends Observation {

    public ObservationWithStation() {
        super();
    }

    @JsonProperty
    @JsonIgnore(false)
    @Override
    public Station getStation() {
        return super.getStation();
    }

    public static ObservationWithStation fromObservation(Observation o) {
        ObservationWithStation result = new ObservationWithStation();
        result.setId(o.getId());
        result.setStation(o.getStation());
        result.setDimension(o.getDimension());
        result.setUnit(o.getUnit());
        result.setValue(o.getValue());
        result.setDataOrigin(o.getDataOrigin());
        result.setTime(o.getTime());
        return result;
    }
}
