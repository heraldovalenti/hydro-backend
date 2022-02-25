package com.aes.dashboard.backend.service.intaData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.aes.dashboard.backend.config.GlobalConfigs.SALTA_ZONE_ID;
import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

public class INTAAnteriorParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(INTAAnteriorParser.class);
    public static final String RAIN = "Rain";
    public static final String LLUVIA = "Lluvia";
    public static final String SPACE = " ";

    public static List<INTAAnteriorDataItem> parseResponse(String response) {
        List<INTAAnteriorDataItem> result = new LinkedList<>();
        String[] lines = response.split("\n");
        if (lines.length <= 3) {
            return result;
        }
        int rainIndex = rainColumnIndex(lines[1]);
        LOGGER.debug("Rain column is {} for line: {}", rainIndex, lines[1]);
        for (int i = 3; i < lines.length; i++) {
            String line = lines[i];
            Optional<INTAAnteriorDataItem> itemOpt = parseLine(line, rainIndex);
            if (itemOpt.isPresent()) result.add(itemOpt.get());
        }
        Collections.sort(result, Comparator.comparing(INTAAnteriorDataItem::getDate));
        if (result.size() > 150) {
            result = result.subList(result.size() - 150, result.size() - 1);
        }
        return result;
    }

    private static int rainColumnIndex(String line1) {
        boolean isEnglish = line1.contains(RAIN);
        List<String> columns = Arrays.stream(line1.split(SPACE)).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        for (int i = 0; i < columns.size(); i++) {
            if (isEnglish && RAIN.equals(columns.get(i))) {
                return i;
            } else if (!isEnglish && LLUVIA.equals(columns.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private static Optional<INTAAnteriorDataItem> parseLine(String line, int rainIndex) {
        LOGGER.debug("Parsing line: {}", line);
        List<String> parts = Arrays.stream(line.split(SPACE)).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        Optional<LocalDateTime> date = parseDate(parts.get(0), parts.get(1));
        if (date.isEmpty()) {
            LOGGER.warn("Could not parse <date> from line {}", line);
            return Optional.empty();
        }
        double rain = 0;
        double rainRate = 0;
        try {
            rain = Double.parseDouble(parts.get(rainIndex));
        } catch (NumberFormatException e) {
            LOGGER.warn("Could not parse <rain> from line", line, e);
        }
        try {
            rainRate = Double.parseDouble(parts.get(rainIndex + 1));
        } catch (NumberFormatException e) {
            LOGGER.warn("Could not parse <rainRate> from line {}", line, e);
        }
        INTAAnteriorDataItem result = new INTAAnteriorDataItem(date.get(), rain, rainRate);
        LOGGER.debug("Parse line result: {}", result);
        return Optional.of(result);
    }

    public static Optional<LocalDateTime> parseDate(String date, String time) {
        try {
            LocalDate resultDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/MM/yy"));
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
