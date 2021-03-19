package com.aes.dashboard.backend.service;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ObservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObservationService.class);

    @Autowired
    private AESDataService aesDataService;

    @Autowired
    private WeatherUndergroundDataService weatherUndergroundDataService;

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
    public void updateINTASiga2Observations() {
        LOGGER.info("Starting update for INTASiga2 observations...");
        DataOrigin intaDataOrigin = dataOriginService.getINTASiga2DataOrigin();
        List<StationDataOrigin> intaStationDataOriginList = stationDataOriginRepository.findByDataOrigin(intaDataOrigin);
        for (StationDataOrigin intaStationDataOrigin : intaStationDataOriginList) {
            List<Observation> observations = new LinkedList<>();
            List<INTASiga2DataItem> intaDataItems = intaSiga2DataService.getObservationData(
                    intaStationDataOrigin.getExternalStationId());
            for(INTASiga2DataItem dataItem : intaDataItems) {
                Observation observation = new Observation();
                observation.setDimension(intaStationDataOrigin.getDimension());
                observation.setStation(intaStationDataOrigin.getStation());
                observation.setDataOrigin(intaDataOrigin);
                observation.setTime(dataItem.getFecha());
                observation.setValue(dataItem.getPrecipitacion());
                observation.setUnit(intaStationDataOrigin.getDefaultUnit());
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
        LOGGER.info("Starting update for SNIH observations...");
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
                    snihStationDataOrigin.getExternalStationId(), aux.get());
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
            updateObservationsForStationOrigin(snihStationDataOrigin, observations);
        }
        LOGGER.info("Update for SNIH observations completed");
    }

    @Transactional
    public void updateObservationsForStationOrigin(StationDataOrigin stationDataOrigin, List<Observation> observations) {
        Optional<Observation> lastObservation = observationRepository.findFirstByStationAndDimensionOrderByTimeDesc(
                stationDataOrigin.getStation(), stationDataOrigin.getDimension());
        if (lastObservation.isPresent()) {
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

}
