package com.aes.dashboard.backend.repository;

import com.aes.dashboard.backend.model.MeasurementDimension;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ObservationRepository extends JpaRepository<Observation, Long> {

    Page<Observation> findByStation(Station station, Pageable pageable);

    List<Observation> findByStationAndDimensionAndTime(
            Station station, MeasurementDimension dimension, LocalDateTime time);

    @Query("SELECT o FROM Observation o WHERE " +
            "o.station = :station " +
            "AND o.dimension = :rain " +
            "AND o.time >= :from AND o.time <= :to " +
            "ORDER BY time DESC")
    List<Observation> findByStationAndDimensionAndBetweenTime(
            Station station,
            MeasurementDimension rain,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

}
