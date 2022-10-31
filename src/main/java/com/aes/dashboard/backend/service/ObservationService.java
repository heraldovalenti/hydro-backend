package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.controller.entities.RequestTimePeriod;
import com.aes.dashboard.backend.model.*;
import com.aes.dashboard.backend.repository.ObservationRepository;
import com.aes.dashboard.backend.repository.StationDataOriginRepository;
import com.aes.dashboard.backend.service.aesLatestData.AESDataService;
import com.aes.dashboard.backend.service.aesLatestData.DataItem;
import com.aes.dashboard.backend.service.intaData.INTAAnteriorDataItem;
import com.aes.dashboard.backend.service.intaData.INTAAnteriorDataService;
import com.aes.dashboard.backend.service.intaData.INTASiga2DataItem;
import com.aes.dashboard.backend.service.intaData.INTASiga2DataService;
import com.aes.dashboard.backend.service.snih.SNIHDataCode;
import com.aes.dashboard.backend.service.snih.SNIHDataService;
import com.aes.dashboard.backend.service.snih.SNIHObservation;
import com.aes.dashboard.backend.service.weatherUndergroundData.WeatherUndergroundDataService;
import com.aes.dashboard.backend.service.weatherUndergroundData.WeatherUndergroundResult;
import com.aes.dashboard.backend.service.weatherlinkData.WeatherlinkDataService;
import com.aes.dashboard.backend.service.weatherlinkData.WeatherlinkResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.aes.dashboard.backend.config.GlobalConfigs.SALTA_ZONE_ID;

@Service
public class ObservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObservationService.class);

    @Autowired
    private AESDataService aesDataService;

    @Autowired
    private WeatherUndergroundDataService weatherUndergroundDataService;

    @Autowired
    private WeatherlinkDataService weatherlinkDataService;

    @Autowired
    private INTASiga2DataService intaSiga2DataService;

    @Autowired
    private INTAAnteriorDataService intaAnteriorDataService;

    @Autowired
    private SNIHDataService snihDataService;

    @Autowired
    private StationDataOriginRepository stationDataOriginRepository;

    @Autowired
    private DataOriginService dataOriginService;

    @Autowired
    private MeasurementUnitService measurementUnitService;

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private StationService stationService;

    @Autowired
    private MeasurementDimensionService measurementDimensionService;

    @Transactional
    public void updateAesObservations() {
        LOGGER.info("Starting update for AES observations...");
        DataOrigin aesDataOrigin = dataOriginService.getAesDataOrigin();
        List<DataItem> latestDataItems = aesDataService.getLatestData();
        List<StationDataOrigin> aesStationDataOriginList = stationDataOriginRepository.findByDataOrigin(aesDataOrigin);
        for (StationDataOrigin aesStationDataOrigin : aesStationDataOriginList) {
            List<Observation> observations = new LinkedList<>();
            latestDataItems.stream()
                    .filter(item ->
                            aesStationDataOrigin.getExternalStationId().equals(item.getId())
                                    && aesStationDataOrigin.getDimension().getDescription().toLowerCase().trim()
                                    .equals(item.getDimension().toLowerCase().trim())
                    ).forEach(dataItem -> {
                Observation observation = new Observation();
                observation.setDimension(aesStationDataOrigin.getDimension());
                observation.setStation(aesStationDataOrigin.getStation());
                observation.setDataOrigin(aesDataOrigin);
                observation.setTime(dataItem.getDate());
                observation.setValue(dataItem.getValue());
                Optional<MeasurementUnit> unit = measurementUnitService.getByAlias(dataItem.getUnit());
                if (unit.isPresent()) observation.setUnit(unit.get());
                else observation.setUnit(aesStationDataOrigin.getDefaultUnit());
                observations.add(observation);
            });
            updateObservationsForStationOrigin(aesStationDataOrigin, observations);
        }
        LOGGER.info("Update for AES observations completed");
    }

    @Transactional
    public void updateWeatherUndergroundObservations() {
        LOGGER.info("Starting update for WeatherUnderground observations...");
        DataOrigin WUDataOrigin = dataOriginService.getWeatherUndergroundDataOrigin();
        List<StationDataOrigin> WUStationDataOriginList = stationDataOriginRepository.findByDataOrigin(WUDataOrigin);
        for (StationDataOrigin WUStationDataOrigin : WUStationDataOriginList) {
            Optional<WeatherUndergroundResult> wuResult = weatherUndergroundDataService.getObservationData(
                    WUStationDataOrigin.getExternalStationId());
            if (wuResult.isEmpty()) continue;
            Observation observation = new Observation();
            observation.setDimension(WUStationDataOrigin.getDimension());
            observation.setStation(WUStationDataOrigin.getStation());
            observation.setDataOrigin(WUDataOrigin);

            if (wuResult.get().getObservationTime().isEmpty()
                    || wuResult.get().getObservationValue().isEmpty()) continue;
            observation.setTime(weatherUndergroundDataService.roundDateTime(
                    wuResult.get().getObservationTime().get()));
            observation.setValue(wuResult.get().getObservationValue().get());
            observation.setUnit(WUStationDataOrigin.getDefaultUnit());
            this.updateOrCreateObservation(observation);
        }
        LOGGER.info("Update for WeatherUnderground observations completed");
    }

    @Transactional
    public void updateWeatherlinkObservations() {
        LOGGER.info("Starting update for Weatherlink observations");
        DataOrigin weatherlinkDataOrigin = dataOriginService.getWeatherlinkDataOrigin();
        List<StationDataOrigin> weatherlinkStationDataOriginList = stationDataOriginRepository.findByDataOrigin(weatherlinkDataOrigin);

        for (StationDataOrigin weatherlinkStationDataOrigin : weatherlinkStationDataOriginList) {
            Optional<WeatherlinkResult> wlResult = weatherlinkDataService.getObservationData(
                    weatherlinkStationDataOrigin.getExternalStationId());
            if (wlResult.isEmpty()) continue;
            Observation observation = new Observation();
            observation.setDimension(weatherlinkStationDataOrigin.getDimension());
            observation.setStation(weatherlinkStationDataOrigin.getStation());
            observation.setDataOrigin(weatherlinkDataOrigin);

            if (wlResult.isEmpty()) continue;
            observation.setTime(weatherUndergroundDataService.roundDateTime(wlResult.get().getObservationTime()));
            observation.setValue(wlResult.get().getObservationValue());
            observation.setUnit(weatherlinkStationDataOrigin.getDefaultUnit());
            this.updateOrCreateObservation(observation);
        }

        LOGGER.info("Update for Weatherlink observations completed");
    }

    @Transactional
    public void updateINTASiga2Observations() {
        LOGGER.info("Starting update for INTASiga2 observations...");
        DataOrigin intaDataOrigin = dataOriginService.getINTASiga2DataOrigin();
        List<StationDataOrigin> intaStationDataOriginList = stationDataOriginRepository.findByDataOrigin(intaDataOrigin);
        for (StationDataOrigin intaStationDataOrigin : intaStationDataOriginList) {
            List<Observation> observations = new LinkedList<>();
            List<INTASiga2DataItem> intaDataItems = intaSiga2DataService.getObservationData(
                    intaStationDataOrigin.getExternalStationId());
            for (INTASiga2DataItem dataItem : intaDataItems) {
                Observation observation = new Observation();
                observation.setDimension(intaStationDataOrigin.getDimension());
                observation.setStation(intaStationDataOrigin.getStation());
                observation.setDataOrigin(intaDataOrigin);
                observation.setTime(dataItem.getFecha());
                observation.setValue(dataItem.getPrecipitacion());
                observation.setUnit(intaStationDataOrigin.getDefaultUnit());
                observations.add(observation);
            }
            updateObservationsForStationOrigin(intaStationDataOrigin, observations);
        }
        LOGGER.info("Update for INTASiga2 observations completed");
    }

    @Transactional
    public void updateINTAAnteriorObservations() {
        LOGGER.info("Starting update for INTAAnterior observations...");
        DataOrigin intaDataOrigin = dataOriginService.getINTAAnteriorDataOrigin();
        List<StationDataOrigin> intaStationDataOriginList = stationDataOriginRepository.findByDataOrigin(intaDataOrigin);
        for (StationDataOrigin intaStationDataOrigin : intaStationDataOriginList) {
            List<Observation> observations = new LinkedList<>();
            List<INTAAnteriorDataItem> intaDataItems = intaAnteriorDataService.getObservationData(
                    intaStationDataOrigin.getExternalStationId());
            for (INTAAnteriorDataItem dataItem : intaDataItems) {
                Observation observation = new Observation();
                observation.setDimension(intaStationDataOrigin.getDimension());
                observation.setStation(intaStationDataOrigin.getStation());
                observation.setDataOrigin(intaDataOrigin);
                observation.setTime(dataItem.getDate());
                observation.setValue(dataItem.getLluvia());
                observation.setUnit(intaStationDataOrigin.getDefaultUnit());
                observations.add(observation);
            }
            updateObservationsForStationOrigin(intaStationDataOrigin, observations);
        }
        LOGGER.info("Update for INTAAnterior observations completed");

    }

    @Transactional
    public void updateSNIHObservations() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of(SALTA_ZONE_ID));
        RequestTimePeriod period = new RequestTimePeriod(now, now);
        updateSNIHObservations(period, true);
    }

    @Transactional
    public void updateSNIHObservations(RequestTimePeriod period, boolean skipPreviousObservations) {
        LOGGER.info("Starting update for SNIH observations (from {} to {})...", period.getFrom(), period.getTo());
        DataOrigin snihDataOrigin = dataOriginService.getSNIHDataOrigin();
        List<StationDataOrigin> snihStationDataOriginList = stationDataOriginRepository.findByDataOrigin(snihDataOrigin);
        for (StationDataOrigin snihStationDataOrigin : snihStationDataOriginList) {
            List<Observation> observations = new LinkedList<>();
            Optional<SNIHDataCode> aux = SNIHDataCode.codeForMeasurementDimension(snihStationDataOrigin.getDimension());
            if (aux.isEmpty()) {
                LOGGER.warn("SNIHDataCode not found for MeasurementDimension {}", snihStationDataOrigin.getDimension());
                continue;
            }
            List<SNIHObservation> snihDataItems = snihDataService.getLatestData(
                    snihStationDataOrigin.getExternalStationId(), aux.get(), period.getFrom(), period.getTo());
            for (SNIHObservation dataItem : snihDataItems) {
                Observation observation = new Observation();
                observation.setDimension(snihStationDataOrigin.getDimension());
                observation.setStation(snihStationDataOrigin.getStation());
                observation.setDataOrigin(snihDataOrigin);
                observation.setTime(dataItem.getDateTime());
                observation.setValue(dataItem.getValue());
                observation.setUnit(snihStationDataOrigin.getDefaultUnit());
                observations.add(observation);
            }
            updateObservationsForStationOrigin(snihStationDataOrigin, observations, skipPreviousObservations);
        }
        LOGGER.info("Update for SNIH observations completed");
    }

    @Transactional
    public void updateObservationsForStationOrigin(
            StationDataOrigin stationDataOrigin,
            List<Observation> observations) {
        updateObservationsForStationOrigin(stationDataOrigin, observations, true);
    }

    @Transactional
    public void updateObservationsForStationOrigin(
            StationDataOrigin stationDataOrigin,
            List<Observation> observations,
            boolean skipPreviousObservations) {
        Optional<Observation> lastObservation = observationRepository.findFirstByStationAndDimensionOrderByTimeDesc(
                stationDataOrigin.getStation(), stationDataOrigin.getDimension());
        if (lastObservation.isPresent() && skipPreviousObservations) {
            LOGGER.info("Last observation for station \"{}\" ({}) and dimension \"{}\" found ({}), discarding previous observations...",
                    stationDataOrigin.getStation().getDescription(),
                    stationDataOrigin.getStation().getId(),
                    stationDataOrigin.getDimension().getDescription(),
                    lastObservation.get().getTime());
            observations.stream()
                    .filter(o -> o.getTime().isAfter(lastObservation.get().getTime()))
                    .forEach(this::updateOrCreateObservation);
        } else {
            observations.stream()
                    .forEach(this::updateOrCreateObservation);
        }
    }

    @Transactional
    public void updateOrCreateObservation(Observation observation) {
        List<Observation> existingObservations = observationRepository.findByStationAndDimensionAndTime(
                observation.getStation(), observation.getDimension(), observation.getTime()
        );
        if (!existingObservations.isEmpty()) {
            existingObservations.stream().forEach(existing -> {
                if (existing.getValue() == observation.getValue()) {
                    LOGGER.info("Skipping observation update: {}", existing.toString());
                } else {
                    existing.setUnit(observation.getUnit());
                    existing.setValue(observation.getValue());
                    LOGGER.info("Updating observation: {}", existing.toString());
                    observationRepository.save(existing);
                }
            });
        } else {
            LOGGER.info("Creating observation: {}", observation.toString());
            observationRepository.save(observation);
        }
    }

    public Page<Observation> listByStationAndDimensionAndPeriod(
            Station station,
            MeasurementDimension measurementDimension,
            boolean useHQModel,
            LocalDateTime from, LocalDateTime to,
            Pageable pageable) {
        Page<Observation> results;
        if (useHQModel) {
            results = generateHQModel(station, measurementDimension, from, to, pageable);
        } else {
            results = observationRepository.findByStationAndDimensionAndBetweenTime(
                    station, measurementDimension, from, to, pageable);
        }
        measurementUnitService.normalizeMeasurementUnits(results);
        return results;
    }

    private Page<Observation> generateHQModel(
            Station station,
            MeasurementDimension measurementDimension,
            LocalDateTime from, LocalDateTime to,
            Pageable pageable) {
        MeasurementDimension flow = measurementDimensionService.getFlowDimension();
        if (!flow.equals(measurementDimension)) return Page.empty();
        MeasurementDimension levelMeasurementDimension = measurementDimensionService.getLevelDimension();
        DataOrigin hqModelDataOrigin = dataOriginService.getHQModelDataOrigin();
        MeasurementUnit m3PerSecondMeasurementUnit = measurementUnitService.getM3PerSecondMeasurementUnit();
        Page<Observation> hObservations = observationRepository.findByStationAndDimensionAndBetweenTime(
                station, levelMeasurementDimension, from, to, pageable);
        return hObservations.map(o -> {
            double hValue = o.getValue();
            double qValue = station.getHqModel().q(hValue);
            o.setDimension(flow);
            o.setDataOrigin(hqModelDataOrigin);
            o.setUnit(m3PerSecondMeasurementUnit);
            o.setValue(qValue);
            return o;
        });
    }

    public List<Observation> latestObservations(MeasurementDimension dimension, LocalDateTime from, LocalDateTime to) {
        List<Station> stations = stationService.stationsWithDimension(dimension);
        List<Observation> result = new LinkedList<>();
        Long start = System.currentTimeMillis();
        for (Station station : stations) {
            Long startStation = System.currentTimeMillis();
            List<Observation> latestObservation = observationRepository
                    .findByStationAndDimensionAndBetweenTime(station, dimension, from, to);
            if (!latestObservation.isEmpty()) result.add(latestObservation.get(0));
            Long endStation = System.currentTimeMillis();
            LOGGER.debug("took {} millis for station {}", endStation - startStation, station.getId());
        }
        Long end = System.currentTimeMillis();
        LOGGER.debug("took {} millis", end - start);
        measurementUnitService.normalizeMeasurementUnits(result);
        return result;
    }
}
