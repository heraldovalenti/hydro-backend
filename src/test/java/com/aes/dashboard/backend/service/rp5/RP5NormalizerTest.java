package com.aes.dashboard.backend.service.rp5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RP5NormalizerTest {

    private List<RP5Row> testData() {
        List<RP5Row> list = new LinkedList<>();
        list.add(new RP5Row("2024", "15", "enero", "09", "0.8", "24"));
        list.add(new RP5Row("2024", "13", "enero", "09", "1.0", "24"));
        list.add(new RP5Row("2024", "13", "enero", "03", "1.0", "6")); // 2
        list.add(new RP5Row("2024", "12", "enero", "09", "4.0", "24"));
        list.add(new RP5Row("2024", "12", "enero", "03", "0.1", "6"));
        list.add(new RP5Row("2024", "11", "enero", "21", "1.0", "6"));
        list.add(new RP5Row("2024", "11", "enero", "15", "0.2", "6"));
        list.add(new RP5Row("2024", "11", "enero", "09", "24.0", "24"));
        list.add(new RP5Row("2024", "11", "enero", "03", "24.0", "6"));
        list.add(new RP5Row("2024", "10", "enero", "21", "0.9", "6"));
        list.add(new RP5Row("2024", "10", "enero", "09", "18.0", "24")); // 10
        list.add(new RP5Row("2024", "10", "enero", "03", "18.0", "6")); // 11
        list.add(new RP5Row("2024", "9", "enero", "21", "0.2", "6"));
        list.add(new RP5Row("2024", "3", "enero", "09", "4.0", "24"));
        list.add(new RP5Row("2024", "2", "enero", "15", "4.0", "6"));
        list.add(new RP5Row("2024", "2", "enero", "09", "19.0", "24"));
        list.add(new RP5Row("2024", "2", "enero", "03", "5.0", "6"));
        list.add(new RP5Row("2024", "1", "enero", "21", "0.5", "6"));
        list.add(new RP5Row("2023", "31", "diciembre", "09", "4.0", "24"));
        list.add(new RP5Row("2023", "31", "diciembre", "03", "0.2", "6"));
        list.add(new RP5Row("2023", "30", "diciembre", "21", "4.0", "6"));
        list.add(new RP5Row("2023", "30", "diciembre", "09", "72.0", "24"));
        list.add(new RP5Row("2023", "30", "diciembre", "03", "5.0", "6"));
        list.add(new RP5Row("2023", "29", "diciembre", "21", "14.0", "6"));
        list.add(new RP5Row("2023", "29", "diciembre", "15", "53.0", "6"));
        list.add(new RP5Row("2023", "29", "diciembre", "09", "0.1", "24"));
        list.add(new RP5Row("2023", "28", "diciembre", "09", "1.0", "24"));
        list.add(new RP5Row("2023", "27", "diciembre", "15", "1.0", "6"));
        list.add(new RP5Row("2023", "27", "diciembre", "09", "2.0", "24"));
        list.add(new RP5Row("2023", "26", "diciembre", "09", "47.0", "24"));
        list.add(new RP5Row("2023", "25", "diciembre", "21", "45.0", "6"));
        list.add(new RP5Row("2023", "25", "diciembre", "15", "1.0", "6"));
        list.add(new RP5Row("2023", "25", "diciembre", "09", "0.4", "24"));
        list.add(new RP5Row("2023", "25", "diciembre", "03", "0.2", "6"));
        list.add(new RP5Row("2023", "22", "diciembre", "09", "0.1", "24"));
        list.add(new RP5Row("2023", "22", "diciembre", "03", "0.1", "6"));
        list.add(new RP5Row("2023", "21", "diciembre", "09", "0.6", "24"));
        list.add(new RP5Row("2023", "21", "diciembre", "03", "0.4", "6"));
        list.add(new RP5Row("2023", "20", "diciembre", "09", "0.7", "24"));
        list.add(new RP5Row("2023", "20", "diciembre", "03", "0.5", "6"));
        list.add(new RP5Row("2023", "19", "diciembre", "15", "0.2", "6"));
        list.add(new RP5Row("2023", "19", "diciembre", "09", "50.0", "24"));
        list.add(new RP5Row("2023", "19", "diciembre", "03", "8.0", "6"));
        list.add(new RP5Row("2023", "18", "diciembre", "21", "29.0", "6"));
        list.add(new RP5Row("2023", "18", "diciembre", "15", "0.2", "6"));
        list.add(new RP5Row("2023", "18", "diciembre", "09", "1.0", "24"));
        list.add(new RP5Row("2023", "18", "diciembre", "03", "1.0", "6"));
        return list;
    }
    @Test
    void normalizeTest() {
        Assertions.assertEquals(47, testData().size());
        List<RP5Row> result = RP5Normalizer.normalize(testData());
        Assertions.assertEquals(45, result.size());
    }

    @Test
    void normalizeWith24HourRowsOnlyTest() {
        List<RP5Row> input = testData().subList(0, 2);
        Assertions.assertEquals(2, input.size());
        List<RP5Row> result = RP5Normalizer.normalize(input);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void normalizeShouldExcludeLast24HourPeriodTest() {
        List<RP5Row> input = testData().subList(2, 10);
        Assertions.assertEquals(8, input.size());
        List<RP5Row> result = RP5Normalizer.normalize(input);
        Assertions.assertEquals(5, result.size());
        Assertions.assertEquals(1.0, result.get(0).getRain());
        Assertions.assertEquals(2.7, result.get(1).getRain());
        Assertions.assertEquals(0.1, result.get(2).getRain());
        Assertions.assertEquals(1.0, result.get(3).getRain());
        Assertions.assertEquals(0.2, result.get(4).getRain());
    }

    @Test
    void normalizeShouldExcludeLast24HourPeriodTest2() {
        List<RP5Row> input = testData().subList(2, 11);
        Assertions.assertEquals(9, input.size());
        List<RP5Row> result = RP5Normalizer.normalize(input);
        Assertions.assertEquals(8, result.size());
        Assertions.assertEquals(0.0, result.get(5).getRain());
        Assertions.assertEquals(24.0, result.get(6).getRain());
        Assertions.assertEquals(0.9, result.get(7).getRain());
    }
}