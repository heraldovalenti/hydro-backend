package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.DataOrigin;
import com.aes.dashboard.backend.model.MeasurementUnit;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.StationDataOrigin;
import com.aes.dashboard.backend.repository.ObservationRepository;
import com.aes.dashboard.backend.repository.StationDataOriginRepository;
import com.aes.dashboard.backend.service.aesLatestData.AESDataService;
import com.aes.dashboard.backend.service.aesLatestData.DataItem;
import com.aes.dashboard.backend.service.weatherUndergroundData.WeatherUndergroundDataService;
import com.aes.dashboard.backend.service.weatherUndergroundData.WeatherUndergroundResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ObservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObservationService.class);

    @Autowired
    private AESDataService aesDataService;

    @Autowired
    private WeatherUndergroundDataService weatherUndergroundDataService;

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
        DataOrigin aesDataOrigin = dataOriginService.getAesDataOrigin();
        List<DataItem> latestDataItems = aesDataService.getLatestData();
        List<StationDataOrigin> aesStationDataOriginList = stationDataOriginRepository.findByDataOrigin(aesDataOrigin);
        for (StationDataOrigin aesStationDataOrigin : aesStationDataOriginList) {
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
                        this.updateOrCreateObservation(observation);
            });
        }
    }

    @Transactional
    public void updateWeatherUndergroundObservations() {
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
    }

    @Transactional
    public void updateOrCreateObservation(Observation observation) {
        List<Observation> existingObservations = observationRepository.findByStationAndDimensionAndTime(
                observation.getStation(), observation.getDimension(), observation.getTime()
        );
        if (!existingObservations.isEmpty()) {
            existingObservations.stream().forEach(existing -> {
                existing.setUnit(observation.getUnit());
                existing.setValue(observation.getValue());
                LOGGER.info("Updating observation: {}", existing.toString());
                observationRepository.save(existing);
            });
        } else {
            LOGGER.info("Creating observation: {}", observation.toString());
            observationRepository.save(observation);
        }
    }

}
