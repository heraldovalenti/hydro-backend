package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.repository.ObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObservationService {

    @Autowired
    private ObservationRepository observationRepository;

    public void test() { }

}
