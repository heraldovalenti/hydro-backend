package com.aes.dashboard.backend.service.rp5;

import com.aes.dashboard.backend.service.AppConfigService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RP5DataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RP5DataService.class);

    private String url;
    private String httpProxyHost;
    private Integer httpProxyPort;
    private AppConfigService appConfigService;

    public RP5DataService(
            @Value("${rp5.url}") String url,
            @Value("${backend.http.proxy.host}") String httpProxyHost,
            @Value("${backend.http.proxy.port}") Integer httpProxyPort,
            AppConfigService appConfigService) {
        this.url = url;
        this.httpProxyHost = httpProxyHost;
        this.httpProxyPort = httpProxyPort;
        this.appConfigService = appConfigService;
    }

    public List<RP5Row> getObservationData(String stationId) {
        return getObservationData(stationId, LocalDateTime.now(), RP5Period.WEEK, 48);
    }

    public List<RP5Row> getObservationData(
            String stationId, LocalDateTime date,
            RP5Period period, int hourLimitResults) {
        String dateString = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        List<RP5Row> result = new LinkedList<>();
        Optional<Elements> observationRows = fetchRows(stationId, dateString, period.toString());
        if (observationRows.isEmpty()) return result;

        Elements rows = observationRows.get();
        String[] values = new String[]{"", "", "", "", "", "", "", ""};
        for (int i = 0; i < rows.size(); i++) {
            parseRow(rows, i, values);
            RP5Row rp5Row = new RP5Row(values[0], values[1], values[2], values[3], values[4], values[5]);
            result.add(rp5Row);
        }
        result = result.stream().filter(r -> r.hasData()).collect(Collectors.toList());
        List<RP5Row> normalized = RP5Normalizer.normalize(result);
        for (int i = 0; i < normalized.size(); i++) {
            LOGGER.info("{} > {} {}", result.get(i), normalized.get(i).getRain(), normalized.get(i).getPeriod());
        }
        if (hourLimitResults > 0) {
            LocalDateTime limit = date.minusHours(hourLimitResults);
            return normalized.stream()
                    .filter(r -> r.getDateTime().isAfter(limit))
                    .collect(Collectors.toList());
        }
        return normalized;
    }

    private Optional<Elements> fetchRows(String stationId, String date, String period) {
        String rp5Cookie = appConfigService.getRP5Cookie();
        try {
            Proxy proxy = null;
            if (httpProxyHost != "" && httpProxyPort != null) {
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpProxyHost, httpProxyPort));
                LOGGER.info("Using proxy configuration with {} and {}: {}",
                        httpProxyHost, httpProxyPort, proxy.toString());
            }
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(this.url)
                    .queryParam("stationId", stationId)
                    .queryParam("date", date)
                    .queryParam("period", period)
                    .queryParam("rp5Cookie", rp5Cookie);
            String requestUrl = builder.build().toUriString();

            Document doc = Jsoup
                    .connect(requestUrl)
                    .proxy(proxy)
                    .maxBodySize(5 * 1024 * 1024) // 5MB
                    .get();

            Elements rows = doc.select("#archiveTable > tbody > tr");
            LOGGER.debug("ROWS AMOUNT {}", rows.size());
            return Optional.of(rows);
        } catch (IOException e) {
            LOGGER.warn("Error trying to fetch observations from {}", url, e);
        }
        return Optional.empty();
    }

    private void parseRow(Elements rows, int index, String[] values) {
        Element row = rows.get(index);
        Elements rowColumns = row.select("td");
        boolean includesTime = rowColumns.size() == 30;
        int hour = 0, RRR=23 , tR=24;
        if (includesTime) {
            Element timeColumn = rowColumns.get(0);
            String timeText = timeColumn.text();
            String[] timeTextParts = timeText.split(",")[0].split(" ");
            boolean includesYear = timeTextParts.length == 4;
            if (includesYear) {
                values[0] = timeTextParts[0]; // year
                values[1] = timeTextParts[1]; // date
                values[2] = timeTextParts[3]; // month
            } else {
                values[1] = timeTextParts[0]; // date
                values[2] = timeTextParts[2]; // month
            }
            hour++;
            RRR++;
            tR++;
        }

        values[3] = "";
        values[4] = "";
        values[5] = "";
        if (rowColumns.size() >= hour + 1) {
            Element columnHour = rowColumns.get(hour);
            if (columnHour.childrenSize() > 0) {
                values[3] = columnHour.child(0).text();
            }
        }
        if (rowColumns.size() >= RRR + 1) {
            Element columnRRR = rowColumns.get(RRR);
            if (columnRRR.childrenSize() > 0) {
                values[4] = columnRRR.child(0).text();
            }
        }
        if (rowColumns.size() >= tR + 1) {
            Element columnTR = rowColumns.get(tR);
            if (columnTR.childrenSize() > 0) {
                values[5] = columnTR.child(0).text();
            }
        }
        LOGGER.debug("row_{} tds.size()={} values={}", index, rowColumns.size(), values);
    }

}
