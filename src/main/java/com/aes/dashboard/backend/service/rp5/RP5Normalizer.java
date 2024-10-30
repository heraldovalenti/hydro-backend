package com.aes.dashboard.backend.service.rp5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

public class RP5Normalizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RP5Normalizer.class);

    public static List<RP5Row> normalize(List<RP5Row> rows) {
        List<RP5Row> result = new LinkedList<>();
        int last24hoursRow = rows.size();
        for (int i = rows.size() - 1; i >= 0 ; i--) {
            if (rows.get(i).is24HourPeriod()) {
                last24hoursRow = i;
                break;
            };
        }
        for (int i = 0; i < rows.size() && i < last24hoursRow; i ++) {
            RP5Row row = rows.get(i);
            LocalDateTime intervalStart = row.getDateTime().minus(row.getPeriod(), ChronoUnit.HOURS);
            List<RP5Row> rowsInPeriod = new LinkedList<>();
            for (int j = i + 1; j < rows.size(); j++) {
                RP5Row aux = rows.get(j);
                if (aux.getDateTime().isAfter(intervalStart)) rowsInPeriod.add(aux);
                else break;
            }
            if (rowsInPeriod.isEmpty()) {
                RP5Row replacement = row.replaceWith(row.getRain(), 6);
                LOGGER.debug("Replaced 24 period to 6 (original={})", row);
                result.add(replacement);
                continue;
            }
            Integer minPeriod = rowsInPeriod.stream().mapToInt(r -> r.getPeriod()).min().getAsInt();
            Double total = rowsInPeriod.stream().mapToDouble(r -> r.getRain()).sum();
            Double rainDiff = row.getRain() - total;
            RP5Row replacement = row.replaceWith(Math.max(0.0, rainDiff), minPeriod);
            LOGGER.debug("Replaced {} with {} (rowsInPeriod={})", row, replacement, rowsInPeriod);
            result.add(replacement);
        }
        return result;
    }
}
