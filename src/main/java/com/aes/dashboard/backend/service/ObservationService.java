package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.dto.RequestTimePeriod;
import com.aes.dashboard.backend.model.*;
import com.aes.dashboard.backend.repository.ObservationRepository;
import com.aes.dashboard.backend.repository.StationDataOriginRepository;
import com.aes.dashboard.backend.service.aesGenexLinea.AesIbuDataParts;
import com.aes.dashboard.backend.service.aesGenexLinea.AesIbuDataService;
import com.aes.dashboard.backend.service.aesLatestData.AESDataService;
import com.aes.dashboard.backend.service.aesLatestData.DataItem;
import com.aes.dashboard.backend.service.gsheet.GSheetDataService;
import com.aes.dashboard.backend.service.gsheet.GSheetStation;
import com.aes.dashboard.backend.service.intaData.INTAAnteriorDataItem;
import com.aes.dashboard.backend.service.intaData.INTAAnteriorDataService;
import com.aes.dashboard.backend.service.intaData.INTASiga2DataItem;
import com.aes.dashboard.backend.service.intaData.INTASiga2DataService;
import com.aes.dashboard.backend.service.pwsWeatherData.PWSWeatherDataService;
import com.aes.dashboard.backend.service.pwsWeatherData.PWSWeatherPeriodItem;
import com.aes.dashboard.backend.service.pwsWeatherData.PWSWeatherResult;
import com.aes.dashboard.backend.service.rp5.RP5DataService;
import com.aes.dashboard.backend.service.rp5.RP5Row;
import com.aes.dashboard.backend.service.snih.SNIHDataCode;
import com.aes.dashboard.backend.service.snih.SNIHDataService;
import com.aes.dashboard.backend.service.snih.SNIHObservation;
import com.aes.dashboard.backend.service.weatherCloudData.WeatherCloudDataService;
import com.aes.dashboard.backend.service.weatherCloudData.WeatherCloudResult;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.aes.dashboard.backend.config.GlobalConfigs.SALTA_ZONE_ID;
import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;
import static com.aes.dashboard.backend.model.date.DateConversor.toUTCLocalDateTime;

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

    @Autowired
    private AesIbuDataService aesIbuDataService;

    @Autowired
    private WeatherCloudDataService weatherCloudDataService;

    @Autowired
    private PWSWeatherDataService pwsWeatherDataService;

    @Autowired
    private RP5DataService rp5DataService;

    @Autowired
    private GSheetDataService aesGSheetDataService;

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
                MeasurementUnit defaultUnit = aesStationDataOrigin.getDefaultUnit();
                if (defaultUnit != null) {
                    observation.setUnit(aesStationDataOrigin.getDefaultUnit());
                } else if (unit.isPresent()) {
                    observation.setUnit(unit.get());
                }
                observations.add(observation);
            });
            updateObservationsForStationOrigin(aesStationDataOrigin, observations);
        }
        LOGGER.info("Update for AES observations completed");
    }

    @Transactional
    public void updateRP5Observations() {
        LOGGER.info("Starting update for RP5 observations...");
        DataOrigin rp5DataOrigin = dataOriginService.getRP5DataOrigin();
        List<StationDataOrigin> rp5StationDataOriginList = stationDataOriginRepository.findByDataOrigin(rp5DataOrigin);
        for (StationDataOrigin rp5StationDataOrigin : rp5StationDataOriginList) {
            List<RP5Row> rp5Rows = rp5DataService.getObservationData(rp5StationDataOrigin.getExternalStationId());
            List<Observation> observations = new LinkedList<>();
            rp5Rows.stream()
                    .forEach(rp5Row -> {
                        Observation observation = new Observation();
                        observation.setDimension(rp5StationDataOrigin.getDimension());
                        observation.setStation(rp5StationDataOrigin.getStation());
                        observation.setDataOrigin(rp5DataOrigin);
                        observation.setTime(rp5Row.getDateTime());
                        observation.setValue(rp5Row.getRain());
                        MeasurementUnit defaultUnit = rp5StationDataOrigin.getDefaultUnit();
                        observation.setUnit(defaultUnit);
                        observations.add(observation);
                    });
            updateObservationsForStationOrigin(rp5StationDataOrigin, observations, false);
        }
        LOGGER.info("Update for RP5 observations completed");
    }

    public void updateAesGSheetObservations() {
        updateAesGSheetObservations(Optional.empty());
    }

    @Transactional
    public void updateAesGSheetObservations(Optional<RequestTimePeriod> period) {
        LOGGER.info("Starting update for AesGSheet observations...");
        if (period.isPresent()) {
            LOGGER.info("from={} to={}", period.get().getFrom(), period.get().getTo());
        }
        DataOrigin aesGSheetDataOrigin = dataOriginService.getAesGSheetDataOrigin();
        List<StationDataOrigin> aesGSheetStationDataOriginList = stationDataOriginRepository.findByDataOrigin(aesGSheetDataOrigin);
        List<GSheetStation> gSheetStations = aesGSheetDataService.getLatestData(period);
        for (StationDataOrigin aesGSheetStationDataOrigin : aesGSheetStationDataOriginList) {
            List<Observation> observations = new LinkedList<>();
            Optional<GSheetStation> gSheetStationData = gSheetStations.stream().filter(aesGSheetStation ->
                    aesGSheetStationDataOrigin.getExternalStationId().equals(aesGSheetStation.getId())
            ).findFirst();
            if (gSheetStationData.isPresent()) {
                gSheetStationData.get().getObservations().forEach(aesGSheetObservation -> {
                    Observation observation = new Observation();
                    observation.setDimension(aesGSheetStationDataOrigin.getDimension());
                    observation.setStation(aesGSheetStationDataOrigin.getStation());
                    observation.setDataOrigin(aesGSheetDataOrigin);
                    observation.setTime(toUTCLocalDateTime(aesGSheetObservation.getDate()));
                    observation.setValue(aesGSheetObservation.getHeight());
                    MeasurementUnit defaultUnit = aesGSheetStationDataOrigin.getDefaultUnit();
                    observation.setUnit(defaultUnit);
                    observations.add(observation);
                });
                updateObservationsForStationOrigin(aesGSheetStationDataOrigin, observations, false);
            }
        }
        LOGGER.info("Update for AesGSheet observations completed");
    }

    @Transactional
    public void updateWeatherUndergroundObservations() {
        LOGGER.info("Starting update for WeatherUnderground observations...");
        DataOrigin WUDataOrigin = dataOriginService.getWeatherUndergroundDataOrigin();
        List<StationDataOrigin> WUStationDataOriginList = stationDataOriginRepository.findByDataOrigin(WUDataOrigin);
        for (StationDataOrigin WUStationDataOrigin : WUStationDataOriginList) {
            Optional<WeatherUndergroundResult> wuResult = weatherUndergroundDataService.getObservationData(WUStationDataOrigin);
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
            observation.setTime(weatherlinkDataService.roundDateTime(wlResult.get().getObservationTime()));
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
    public void updateAesIbuObservations() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of(UTC_ZONE_ID));
        LOGGER.info("Starting update for AES IBU observations...");
        DataOrigin aesIbuDataOrigin = dataOriginService.getAesIbuDataOrigin();
        List<StationDataOrigin> aesIbuStationDataOriginList = stationDataOriginRepository.findByDataOrigin(aesIbuDataOrigin);
        Map<AesIbuDataParts, Double> observationData = aesIbuDataService.getObservationData();
        for (StationDataOrigin aesIbuStationDataOrigin : aesIbuStationDataOriginList) {
            List<Observation> observations = new LinkedList<>();
            AesIbuDataParts key = AesIbuDataParts.valueOf(aesIbuStationDataOrigin.getExternalStationId());
            Double observationValue = observationData.get(key);
            if (observationValue == null) {
                LOGGER.warn("{} AES IBU observation value for station {} (id {})",
                        aesIbuStationDataOrigin.getDimension().getDescription(),
                        aesIbuStationDataOrigin.getStation().getDescription(),
                        aesIbuStationDataOrigin.getStation().getId());
                continue;
            }
            Observation observation = new Observation();
            observation.setDimension(aesIbuStationDataOrigin.getDimension());
            observation.setStation(aesIbuStationDataOrigin.getStation());
            observation.setDataOrigin(aesIbuDataOrigin);
            observation.setTime(now);
            observation.setValue(observationValue);
            observation.setUnit(aesIbuStationDataOrigin.getDefaultUnit());
            observations.add(observation);
            updateObservationsForStationOrigin(aesIbuStationDataOrigin, observations);
        }
        LOGGER.info("Update for AES IBU observations completed");
    }

    @Transactional
    public void updateWeatherCloudObservations() {
        LOGGER.info("Starting update for Weather Cloud observations");
        LocalDateTime now = LocalDateTime.now(ZoneId.of(UTC_ZONE_ID));
        DataOrigin weatherCloudDataOrigin = dataOriginService.getWeatherCloudDataOrigin();
        List<StationDataOrigin> weatherCloudStationDataOriginList = stationDataOriginRepository.findByDataOrigin(weatherCloudDataOrigin);

        for (StationDataOrigin stationDataOrigin : weatherCloudStationDataOriginList) {
            Optional<WeatherCloudResult> weatherCloudResult = weatherCloudDataService.getObservationData(
                    stationDataOrigin.getExternalStationId());
            if (weatherCloudResult.isEmpty()) continue;
            Observation observation = new Observation();
            observation.setDimension(stationDataOrigin.getDimension());
            observation.setStation(stationDataOrigin.getStation());
            observation.setDataOrigin(weatherCloudDataOrigin);
            observation.setTime(now);
            observation.setValue(weatherCloudResult.get().getValues().getRain());
            observation.setUnit(stationDataOrigin.getDefaultUnit());
            this.updateOrCreateObservation(observation);
        }

        LOGGER.info("Update for Weather Cloud observations completed");
    }

    public void updatePWSWeatherObservations() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of(SALTA_ZONE_ID));
        RequestTimePeriod period = new RequestTimePeriod(now, now);
        updatePWSWeatherObservations(period, true);
    }
    public void updatePWSWeatherObservations(RequestTimePeriod period, boolean skipPreviousObservations) {
        LocalDate date = period.getFrom().toLocalDate();
        do {
            updatePWSWeatherObservations(date, skipPreviousObservations);
            date = date.plusDays(1);
        } while (date.isBefore(period.getTo().toLocalDate()));
    }

    @Transactional
    public void updatePWSWeatherObservations(LocalDate date, boolean skipPreviousObservations) {
        LOGGER.info("Starting update for PWS Weather observations (date: {})", date);
        DataOrigin pwsWeatherDataOrigin = dataOriginService.getPWSWeatherDataOrigin();
        List<StationDataOrigin> pwsWeatherStationDataOriginList = stationDataOriginRepository.findByDataOrigin(pwsWeatherDataOrigin);

        for (StationDataOrigin stationDataOrigin : pwsWeatherStationDataOriginList) {
            if (!stationDataOrigin.getStation().getActive()) continue;
            Optional<PWSWeatherResult> pwsWeatherResult = pwsWeatherDataService.getObservationData(
                    stationDataOrigin.getExternalStationId(), date);
            if (pwsWeatherResult.isEmpty()) continue;
            List<Observation> observations = new LinkedList<>();
            for (PWSWeatherPeriodItem item : pwsWeatherResult.get().getResponse().getPeriods()) {
                Observation observation = new Observation();
                observation.setDimension(stationDataOrigin.getDimension());
                observation.setStation(stationDataOrigin.getStation());
                observation.setDataOrigin(pwsWeatherDataOrigin);
                observation.setTime(toUTCLocalDateTime(item.getOb().getDateTimeISO()));
                observation.setValue(item.getOb().getPrecipSinceLastObMM());
                observation.setUnit(stationDataOrigin.getDefaultUnit());
                observations.add(observation);
            }
            this.updateObservationsForStationOrigin(stationDataOrigin, observations, skipPreviousObservations);
        }

        LOGGER.info("Update for PWS Weather observations completed");
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
    private void updateOrCreateObservation(Observation observation) {
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
        measurementUnitService.normalizeMeasurementUnits(hObservations);
        return hObservations.map(o -> {
            double hValue = o.getValue();
            double qValue = station.getHqModel().calculateQ(hValue);
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
        Collections.sort(result, Comparator.comparing(Observation::getId));
        Long end = System.currentTimeMillis();
        LOGGER.debug("took {} millis", end - start);
        measurementUnitService.normalizeMeasurementUnits(result);
        return result;
    }

    public List<Observation> latestObservationsV2(MeasurementDimension dimension, LocalDateTime from, LocalDateTime to) {
        List<Observation> latestObservation = observationRepository
                    .findLatestObservationsByDimension(dimension.getId(), from, to);
        // the following line is necessary only to compare with latestObservation V1 to get the same order
        //        Collections.sort(latestObservation, Comparator.comparing(Observation::getId));
        measurementUnitService.normalizeMeasurementUnits(latestObservation);
        return latestObservation;
    }

    @Transactional
    public void saveAll(List<Observation> observationList) {
        observationRepository.saveAll(observationList);
    }
}
