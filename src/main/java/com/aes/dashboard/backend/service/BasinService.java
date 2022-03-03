package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.Basin;
import com.aes.dashboard.backend.repository.BasinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasinService {

    @Autowired
    private BasinRepository basinRepository;

    public List<Basin> basinList() {
        return basinRepository.findAll();
    }

}
