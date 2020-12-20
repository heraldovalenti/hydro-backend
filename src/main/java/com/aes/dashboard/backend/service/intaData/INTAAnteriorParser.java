package com.aes.dashboard.backend.service.intaData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aes.dashboard.backend.config.GlobalConfigs.SALTA_ZONE_ID;
import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

public class INTAAnteriorParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(INTAAnteriorParser.class);

    public static List<INTAAnteriorDataItem> parseResponse(String response) {
        List<INTAAnteriorDataItem> result = new LinkedList<>();
        String[] lines = response.split("\n");
        for (int i = 3; i < lines.length; i++) {
            String line = lines[i];
            Optional<INTAAnteriorDataItem> itemOpt = parseLine(line);
            if (itemOpt.isPresent()) result.add(itemOpt.get());
        }
        return result;
    }

    private static Optional<INTAAnteriorDataItem> parseLine(String line) {
        List<String> parts = Arrays.stream(line.split(" ")).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        Optional<LocalDateTime> date = parseDate(parts.get(0), parts.get(1));
        if (date.isEmpty()) {
            LOGGER.warn("Could not parse <date> from line {}", line);
            return Optional.empty();
        }
        double lluvia = 0;
        double intensidadLluvia = 0;
        try {
            lluvia = Double.parseDouble(parts.get(17));
        } catch (NumberFormatException e) {
            LOGGER.warn("Could not parse <lluvia> from line", line, e);
        }
        try {
            intensidadLluvia = Double.parseDouble(parts.get(18));
        } catch (NumberFormatException e) {
            LOGGER.warn("Could not parse <intensidadLluvia> from line {}", line, e);
        }
        INTAAnteriorDataItem result = new INTAAnteriorDataItem(date.get(), lluvia, intensidadLluvia);
        return Optional.of(result);
    }

    public static Optional<LocalDateTime> parseDate(String date, String time) {
        try {
            LocalDate resultDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yy"));
            String timeFormat = "H:mm";
            if (time.contains("a") || time.contains("p")) {
                time = time.replace("p", " PM").replace("a", " AM");
                timeFormat = "h:mm a";
            }
            LocalTime resultTime = LocalTime.parse(time, DateTimeFormatter.ofPattern(timeFormat));

            LocalDateTime result = LocalDateTime.of(resultDate, resultTime);
            ZonedDateTime saltaTime = ZonedDateTime.of(result, ZoneId.of(SALTA_ZONE_ID));
            ZonedDateTime utcTime = saltaTime.withZoneSameInstant(ZoneId.of(UTC_ZONE_ID));
            return Optional.of(utcTime.toLocalDateTime());
        } catch (Exception e ) {
            LOGGER.warn("Could not parse <date> from date {} and time {}", date, time, e);
            return Optional.empty();
        }
    }
}
