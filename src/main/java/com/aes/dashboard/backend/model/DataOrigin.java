package com.aes.dashboard.backend.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class DataOrigin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String description;

    public DataOrigin(long id) {
        this.id = id;
    }

    public DataOrigin() {
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

    @Override
    public String toString() {
        return "DataOrigin{" +
                "id=" + id +
                ", description=" + description +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataOrigin that = (DataOrigin) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
