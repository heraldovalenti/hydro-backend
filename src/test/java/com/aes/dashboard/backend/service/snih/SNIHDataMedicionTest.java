package com.aes.dashboard.backend.service.snih;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class SNIHDataMedicionTest {

    @Test
    public void simpleTest() {
        SNIHDataMedicion medicion = new SNIHDataMedicion();
        medicion.setFechaHora("/Date(1614567660000)/");
        LocalDateTime result = medicion.parseFechaHora();
        Assertions.assertEquals(1, result.getDayOfMonth());
        Assertions.assertEquals(3, result.getMonthValue());
        Assertions.assertEquals(2021, result.getYear());
        Assertions.assertEquals(0, result.getHour());
        Assertions.assertEquals(1, result.getMinute());
        Assertions.assertEquals(0, result.getSecond());

    }

}