package com.aes.dashboard.backend.service.gsheet;

import java.util.List;

public class GSheetStation {

    private String id;
    private List<GSheetObservation> observations;

    public GSheetStation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GSheetObservation> getObservations() {
        return observations;
    }

    public void setObservations(List<GSheetObservation> observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "GSheetStation{" +
                "id='" + id + '\'' +
                ", observations=" + observations +
                '}';
    }
}
