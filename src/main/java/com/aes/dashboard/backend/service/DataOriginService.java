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

    public DataOrigin getINTADataOrigin() {
        return dataOriginRepository.findById(3L).get();
    }
}
