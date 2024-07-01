package com.aes.dashboard.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HQModelTest {

    @Test
    public void punillaStationHQTest() {
        HQModel model = new HQModel();
        model.sethLimit(0.35);
        model.sethOffset(-2.69);
        model.setLowPassFactor(57.673);
        model.setLowPassOffset(0);
        model.setLowPassExponent(2.473);

        model.setHighPassFactor(58.566);
        model.setHighPassOffset(-0.1);
        model.setHighPassExponent(1.6666);
        assertEquals(0, model.calculateQ(0));
        assertEquals(0, model.calculateQ(-9999));
        assertEquals(5.8110201, model.calculateQ(2.69 + 0.35), 0.000001);
    }

    @Test
    public void matPowTests() {
        assertEquals(4, Math.pow(2, 2));
        assertEquals(4, Math.pow(-2, 2));
        assertEquals(0.25, Math.pow(2, -2));
        assertEquals(0.25, Math.pow(-2, -2));

        assertEquals(Math.sqrt(2), Math.pow(2, 0.5));
        assertEquals(1/Math.sqrt(2), Math.pow(2, -0.5), 0.000000000000001);

        assertEquals(4, Math.pow(-2, 2));
    }

    @Test
    public void mathPowNaNTest() {
        double d1 = 3.69, d2 = 2.473; //2.473;
        assertTrue(Double.isFinite(d1));
        assertTrue(Double.isFinite(d2));
        assertFalse(Double.isNaN( Math.pow(d1, d2) ));
        assertFalse(Double.isNaN( Math.pow(d1, -d2) ));
        assertTrue(Double.isNaN( Math.pow(-d1, -d2) ));
    }


}