package com.aes.dashboard.backend.repository;

import com.aes.dashboard.backend.dto.IStationRainAccumulation;
import com.aes.dashboard.backend.model.MeasurementDimension;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ObservationRepository extends JpaRepository<Observation, Long> {

    Page<Observation> findByStation(Station station, Pageable pageable);

    List<Observation> findByStationAndDimensionAndTime(
            Station station, MeasurementDimension dimension, LocalDateTime time);

    @Query("SELECT o FROM Observation o " +
            "WHERE o.station = :station " +
            "AND o.time >= :from AND o.time <= :to")
    Page<Observation> findByStationAndBetweenTime(
            Station station,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable);

    @Query("SELECT o FROM Observation o " +
            "WHERE o.station = :station " +
            "AND o.time >= :from AND o.time <= :to")
    List<Observation> findByStationAndBetweenTime(
            Station station,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Sort pageable);

    @Query("SELECT o.station.id as stationId, sum(o.diff) AS accumulation " +
            "FROM Observation o " +
            "WHERE o.time >= :from AND o.time <= :to " +
            "GROUP BY o.station.id")
    List<IStationRainAccumulation> idAndSumDiffBetweenTime(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("SELECT o.station.id as stationId, sum(o.diff) AS accumulation " +
            "FROM Observation o " +
            "WHERE o.station = :station " +
            "AND o.time >= :from AND o.time <= :to " +
            "GROUP BY o.station.id")
    List<IStationRainAccumulation> idAndSumDiffByStationAndBetweenTime(
            Station station,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("SELECT o.station.id as stationId " +
            "FROM Observation o " +
            "WHERE o.dimension = :dimension " +
            "AND o.time >= :from AND o.time <= :to " +
            "GROUP BY o.station.id")
    List<Long> stationIdByDimensionAndBetweenTime(
            MeasurementDimension dimension,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("SELECT o FROM Observation o  " +
            "WHERE o.station = :station " +
            "AND o.dimension = :dimension " +
            "AND o.value > -999 " +
            "AND o.time >= :from AND o.time <= :to")
    Page<Observation> findByStationAndDimensionAndBetweenTime(
            Station station,
            MeasurementDimension dimension,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable);

    @Query("SELECT o FROM Observation o " +
            "WHERE o.station = :station " +
            "AND o.dimension = :dimension " +
            "AND o.time >= :from AND o.time <= :to " +
            "AND o.value > -999 " +
            "ORDER BY time DESC")
    List<Observation> findByStationAndDimensionAndBetweenTime(
            Station station,
            MeasurementDimension dimension,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

    @Query("SELECT o FROM Observation o " +
            "WHERE o.station = :station " +
            "AND o.dimension = :dimension " +
            "AND o.time >= :from AND o.time <= :to " +
            "AND o.value > -999 " +
            "ORDER BY time DESC")
    List<Observation> findByStationAndDimensionAndBetweenTimeOrderByTimeDesc(
            Station station,
            MeasurementDimension dimension,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

    @Query("SELECT o FROM Observation o " +
            "WHERE o.dimension = :dimension " +
            "AND o.time >= :from AND o.time <= :to")
    List<Observation> findByDimensionAndBetweenTime(
            MeasurementDimension dimension,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

    Optional<Observation> findFirstByStationAndDimensionOrderByTimeDesc(
            Station station,
            MeasurementDimension rain);

    Page<Observation> findByStationAndDimension(Station station, MeasurementDimension rain, Pageable pageable);

    // idea from: https://stackoverflow.com/questions/4510185/select-max-value-of-each-group
    @Query( nativeQuery = true, value = "SELECT * FROM observation o1 " +
            "INNER JOIN ( SELECT o2.station_id AS o2station_id, MAX(o2.time) AS o2time " +
            "FROM observation o2 " +
            "WHERE dimension_id = ?1 " +
            "AND o2.time between ?2 and ?3 " +
            "AND o2.value > -999 " +
            "GROUP BY o2station_id ) o2 " +
            "ON o1.station_id = o2station_id AND o1.time = o2time " +
            "WHERE o1.dimension_id = ?1 " +
            "ORDER BY o1.time DESC")
    List<Observation> findLatestObservationsByDimension(
            Long dimensionId,
            LocalDateTime from,
            LocalDateTime to);

}
