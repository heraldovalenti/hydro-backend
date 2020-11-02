package com.aes.dashboard.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ObservationServiceTest {

    @Autowired
    private ObservationService service;

    @Test
    public void testUpdateAesObservationsTest() {
        service.updateAesObservations();
    }

}