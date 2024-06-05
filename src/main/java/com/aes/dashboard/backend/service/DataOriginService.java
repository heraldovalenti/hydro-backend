package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.DataOrigin;
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
}
