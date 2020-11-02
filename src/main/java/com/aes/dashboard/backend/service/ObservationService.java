package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.DataOrigin;
import com.aes.dashboard.backend.model.MeasurementUnit;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.StationDataOrigin;
import com.aes.dashboard.backend.repository.ObservationRepository;
import com.aes.dashboard.backend.repository.StationDataOriginRepository;
import com.aes.dashboard.backend.service.aesLatestData.AESDataService;
import com.aes.dashboard.backend.service.aesLatestData.DataItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ObservationService {

    @Autowired
    private AESDataService aesDataService;

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
    public void updateOrCreateObservation(Observation observation) {
        List<Observation> existingObservations = observationRepository.findByStationAndDimensionAndTime(
                observation.getStation(), observation.getDimension(), observation.getTime()
        );
        if (!existingObservations.isEmpty()) {
            existingObservations.stream().forEach(existing -> {
                existing.setUnit(observation.getUnit());
                existing.setValue(observation.getValue());
                observationRepository.save(existing);
                System.out.println("updated observation: " + existing.toString());
            });
        } else {
            observationRepository.save(observation);
            System.out.println("created observation: " + observation.toString());
        }
    }

}
