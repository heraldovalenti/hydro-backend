package com.aes.dashboard.backend.service;

import com.aes.dashboard.backend.controller.entities.RequestTimePeriod;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.aes.dashboard.backend.config.GlobalConfigs.*;

@Service
public class DataExportService {

    public String getContentDispositionHeader(Station station, RequestTimePeriod requestTimePeriod) {
        DateTimeFormatter outDTF = DateTimeFormatter.ofPattern(EXPORT_FILE_NAME_DATE_OUTPUT_FORMAT);
        return String.format("attachment; filename=%s_%s_%s.csv",
                station.getDescription(),
                requestTimePeriod.getFrom().format(outDTF),
                requestTimePeriod.getTo().format(outDTF));
    }

    public RequestTimePeriod parseRequestTimePeriod(String from, String to) {
        DateTimeFormatter inDTF = DateTimeFormatter.ofPattern(REQUEST_PARAM_DATE_INPUT_FORMAT);
        LocalDateTime fromDate = LocalDateTime.parse(from, inDTF),
                toDate = LocalDateTime.parse(to, inDTF);
        return new RequestTimePeriod(fromDate, toDate);
    }

    public void writeExportData(Writer pw, Page<Observation> data) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(EXPORT_FILE_CONTENT_DATE_OUTPUT_FORMAT);
        pw.write(String.format("Fecha y hora, Valor observado, Unidad"));
        for (Observation o : data) {
            ZonedDateTime utcTime = ZonedDateTime.of(o.getTime(), ZoneId.of(UTC_ZONE_ID));
            ZonedDateTime saltaTime = utcTime.withZoneSameInstant(ZoneId.of(SALTA_ZONE_ID));
            pw.write(String.format("\n%s, %f, %s", saltaTime.format(dtf), o.getValue(), o.getUnit().getDescription()));
        }
    }

}
