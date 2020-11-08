package com.aes.dashboard.backend.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class MeasurementDimension {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private MeasurementUnit preferredUnit;

    public MeasurementDimension() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MeasurementUnit getPreferredUnit() {
        return preferredUnit;
    }

    public void setPreferredUnit(MeasurementUnit preferredUnit) {
        this.preferredUnit = preferredUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementDimension that = (MeasurementDimension) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
