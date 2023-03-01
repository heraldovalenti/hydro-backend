package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.DataOrigin;
import com.aes.dashboard.backend.repository.DataOriginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DataOriginService {

    @Autowired
    private DataOriginRepository dataOriginRepository;

    public DataOrigin getAesDataOrigin() {
        return dataOriginRepository.findById(1L).get();
    }

    public DataOrigin getWeatherUndergroundDataOrigin() {
        return dataOriginRepository.findById(2L).get();
    }

    public DataOrigin getINTASiga2DataOrigin() {
        return dataOriginRepository.findById(3L).get();
    }

    public DataOrigin getINTAAnteriorDataOrigin() {
        return dataOriginRepository.findById(5L).get();
    }

    public DataOrigin getSNIHDataOrigin() {
        return dataOriginRepository.findById(6L).get();
    }

    public DataOrigin getWeatherlinkDataOrigin() {
        return dataOriginRepository.findById(8L).get();
    }

    public DataOrigin getHQModelDataOrigin() {
        return dataOriginRepository.findById(7L).get();
    }

    public DataOrigin getAesIbuDataOrigin() {
        return dataOriginRepository.findById(9L).get();
    }
}
