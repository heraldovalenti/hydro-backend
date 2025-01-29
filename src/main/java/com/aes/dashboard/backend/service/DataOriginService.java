package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.DataOrigin;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.repository.DataOriginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.aes.dashboard.backend.config.GlobalConfigs.*;


@Service
public class DataOriginService {

    @Autowired
    private DataOriginRepository dataOriginRepository;

    public DataOrigin getAesDataOrigin() {
        return dataOriginRepository.findById(DATA_ORIGIN_AES).get();
    }

    public DataOrigin getWeatherUndergroundDataOrigin() {
        return dataOriginRepository.findById(DATA_ORIGIN_WU).get();
    }

    public DataOrigin getINTASiga2DataOrigin() {
        return dataOriginRepository.findById(DATA_ORIGIN_INTA_SIGA).get();
    }

    public DataOrigin getINTAAnteriorDataOrigin() {
        return dataOriginRepository.findById(DATA_ORIGIN_INTA_ANTERIOR).get();
    }

    public DataOrigin getSNIHDataOrigin() {
        return dataOriginRepository.findById(DATA_ORIGIN_SNIH).get();
    }

    public DataOrigin getWeatherlinkDataOrigin() {
        return dataOriginRepository.findById(DATA_ORIGIN_WEATHERLINK).get();
    }

    public DataOrigin getHQModelDataOrigin() {
        return dataOriginRepository.findById(DATA_ORIGIN_HQ_MODEL).get();
    }

    public DataOrigin getAesIbuDataOrigin() {
        return dataOriginRepository.findById(DATA_ORIGIN_AES_IBU).get();
    }

    public DataOrigin getWeatherCloudDataOrigin() {
        return dataOriginRepository.findById(DATA_ORIGIN_WEATHERCLOUD).get();
    }

    public DataOrigin getPWSWeatherDataOrigin() {
        return dataOriginRepository.findById(DATA_ORIGIN_PWSWEATHER).get();
    }

    public DataOrigin getRP5DataOrigin() {
        return dataOriginRepository.findById(DATA_ORIGIN_RP5).get();
    }

    public DataOrigin getAesGSheetDataOrigin() {
        return dataOriginRepository.findById(DATA_ORIGIN_AES_GSHEET).get();
    }

    public boolean hasAESDataOrigin(Station station) {
        return station.getStationDataOriginList()
                .stream().filter(sdo -> getAesDataOrigin().equals(sdo.getDataOrigin()))
                .findFirst().isPresent();
    }

    public boolean hasWeatherUndergroundDataOrigin(Station station) {
        return station.getStationDataOriginList()
                .stream().filter(sdo -> getWeatherUndergroundDataOrigin().equals(sdo.getDataOrigin()))
                .findFirst().isPresent();
    }

    public boolean hasWeatherLinkDataOrigin(Station station) {
        return station.getStationDataOriginList()
                .stream().filter(sdo -> getWeatherlinkDataOrigin().equals(sdo.getDataOrigin()))
                .findFirst().isPresent();
    }

    public boolean hasWeatherCloudDataOrigin(Station station) {
        return station.getStationDataOriginList()
                .stream().filter(sdo -> getWeatherCloudDataOrigin().equals(sdo.getDataOrigin()))
                .findFirst().isPresent();
    }

    public boolean hasSNIHDataOrigin(Station station) {
        return station.getStationDataOriginList()
                .stream().filter(sdo -> getSNIHDataOrigin().equals(sdo.getDataOrigin()))
                .findFirst().isPresent();
    }

    public boolean hasRP5DataOrigin(Station station) {
        return station.getStationDataOriginList()
                .stream().filter(sdo -> getRP5DataOrigin().equals(sdo.getDataOrigin()))
                .findFirst().isPresent();
    }

    public boolean hasPWSDataOrigin(Station station) {
        return station.getStationDataOriginList()
                .stream().filter(sdo -> getPWSWeatherDataOrigin().equals(sdo.getDataOrigin()))
                .findFirst().isPresent();
    }

}
