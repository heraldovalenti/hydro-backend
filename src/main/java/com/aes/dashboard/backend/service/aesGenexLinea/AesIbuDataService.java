package com.aes.dashboard.backend.service.aesGenexLinea;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AesIbuDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AesIbuDataService.class);
    private static final int CCOR_ROW_INDEX = 8;
    private static final int TUNA_ROW_INDEX = 9;

    private String url;
    private String httpProxyHost;
    private Integer httpProxyPort;

    public AesIbuDataService(
            @Value("${aes.ibu.url}") String url,
            @Value("${backend.http.proxy.host}") String httpProxyHost,
            @Value("${backend.http.proxy.port}") Integer httpProxyPort) {
        this.url = url;
        this.httpProxyHost = httpProxyHost;
        this.httpProxyPort = httpProxyPort;
    }

    public Map<AesIbuDataParts, Double> getObservationData() {
        Map<AesIbuDataParts, Double> result = new HashMap();
        Optional<Elements> observationRows = fetchObservations();
        if (observationRows.isPresent()) {
            String ccorData = parseRow(observationRows.get(), CCOR_ROW_INDEX);
            List<Double> ccorValues = parseValues(ccorData);
            result.put(AesIbuDataParts.PB_NIVEL, ccorValues.get(0));
            result.put(AesIbuDataParts.PB_CAUDAL, ccorValues.get(1));
            result.put(AesIbuDataParts.CCOR_NIVEL, ccorValues.get(2));

            String tunaData = parseRow(observationRows.get(), TUNA_ROW_INDEX);
            List<Double> tunaValues = parseValues(tunaData);
            result.put(AesIbuDataParts.TUNA_CAUDAL, tunaValues.get(0));
            result.put(AesIbuDataParts.TUNA_NIVEL, tunaValues.get(1));
        }
        return result;
    }

    private Optional<Elements> fetchObservations() {
        Document doc = null;
        try {
            Proxy proxy = null;
            if (httpProxyHost != "" && httpProxyPort != null) {
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpProxyHost, httpProxyPort));
                LOGGER.info("Using proxy configuration with {} and {}: {}",
                        httpProxyHost, httpProxyPort, proxy.toString());
            }
            doc = Jsoup.connect(url)
                    .proxy(proxy)
                    .get();
            Elements rows = doc.select("#table-sparkline > tbody > tr");
            LOGGER.debug("ROWS AMOUNT {}", rows.size());
            return Optional.of(rows);
        } catch (IOException e) {
            LOGGER.warn("Error trying to fetch observations from {}", url, e);
        }
        return Optional.empty();
    }

    private String parseRow(Elements rows, int index) {
        Element row = rows.get(index);
        Elements rowColumns = row.select("td");
        Element column3 = rowColumns.get(3);
        Element column4 = rowColumns.get(4);
        String column3Data = column3.child(0).text();
        String column4Data = column4.child(0).text();
        LOGGER.debug("row{}_column3Data={} row{}_column4Data={}", index, column3Data, index, column4Data);
        return String.format("%s %s", column3Data, column4Data);
    }

    private List<Double> parseValues(String data) {
        String[] parts = data.split(" ");
        var result = Arrays.stream(parts)
                .filter(p -> p.contains(","))
                .map(p -> p.replace(",", "."))
                .map(p -> Double.parseDouble(p))
                .collect(Collectors.toList());
        LOGGER.debug("value {} parsed into {}", data, result);
        return result;
    }

}
