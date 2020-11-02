package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.model.DataOrigin;
import org.springframework.stereotype.Service;


@Service
public class DataOriginService {

    private static final String AES_DATA_ORIGIN = "AES";

    public DataOrigin getAesDataOrigin() {
        DataOrigin aesDataOrigin = new DataOrigin();
        aesDataOrigin.setId(1);
        aesDataOrigin.setDescription(AES_DATA_ORIGIN);
        return aesDataOrigin;
    }
}
