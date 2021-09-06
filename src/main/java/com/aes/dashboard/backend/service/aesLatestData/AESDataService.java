package com.aes.dashboard.backend.service.aesLatestData;

import com.aes.dashboard.backend.service.AppConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AESDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AESDataService.class);
    private static final String AUTH_TOKEN_COOKIE_NAME = "FedAuth";

    private String url;
    private RestTemplate restTemplate;
    private AppConfigService appConfigService;

    public AESDataService(
            RestTemplate restTemplate,
            @Value("${aes.one-drive.url}") String url,
            AppConfigService appConfigService) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.appConfigService = appConfigService;
    }

    public List<DataItem> getLatestData() {
        String authToken = appConfigService.getAuthToken();
        refreshAuthToken(authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url)
                .queryParam("authToken", encodeParam(authToken));
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        this.restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
        ResponseEntity<LatestDataItem[]> response = this.restTemplate.getForEntity(
                builder.build(false).toUriString(),
                LatestDataItem[].class);
        List<DataItem> result = new LinkedList<>();
        Arrays.stream(response.getBody()).forEach(latestDataItem -> result.addAll(latestDataItem.getData()));
        return result;
    }

    private String encodeParam(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public void refreshAuthToken(String currentAuthToken) {
        String oneDriveBaseUrl = "https://aescloud-my.sharepoint.com/personal/edgardo_mendez_aes_com/_layouts/15/onedrive.aspx";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(oneDriveBaseUrl)
                .queryParam("id", encodeParam("/personal/edgardo_mendez_aes_com/Documents/DatosCCO"))
                .queryParam("originalPath", "aHR0cHM6Ly9hZXNjbG91ZC1teS5zaGFyZXBvaW50LmNvbS86ZjovZy9wZXJzb25hbC9lZGdhcmRvX21lbmRlel9hZXNfY29tL0VoX254bm9kTkJKQ2s4NnNBa2dHWVVrQk1hekd5cHBLS2Rhc3FZYU5MMnFOcEE_cnRpbWU9cUItV2ZfZngyRWc");
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        this.restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);

        HttpHeaders headers = new HttpHeaders();
        headers.add("authority", "aescloud-my.sharepoint.com");
        headers.add("pragma", "no-cache");
        headers.add("cache-control", "no-cache");
        headers.add("sec-ch-ua", "\"Google Chrome\";v=\"89\", \"Chromium\";v=\"89\", \";Not A Brand\";v=\"99\"");
        headers.add("sec-ch-ua-mobile", "?0");
        headers.add("upgrade-insecure-requests", "1");
        headers.add("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
        headers.add("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.add("service-worker-navigation-preload", "true");
        headers.add("sec-fetch-site", "same-origin");
        headers.add("sec-fetch-mode", "navigate");
        headers.add("sec-fetch-user", "?1");
        headers.add("sec-fetch-dest", "document");
        headers.add("referer", "https://aescloud-my.sharepoint.com/personal/edgardo_mendez_aes_com/_layouts/15/guestaccess.aspx?email=heraldovalenti%40gmail.com&e=5%3afclMkS&at=9&share=Eh_nxnodNBJCk86sAkgGYUkBMazGyppKKdasqYaNL2qNpA");
        headers.add("accept-language", "en,en-US;q=0.9,es;q=0.8");
        String cookieHeadderValue = String.format("odbnu=0; %s=%s", AUTH_TOKEN_COOKIE_NAME, currentAuthToken);
        headers.add("cookie", cookieHeadderValue);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = this.restTemplate.exchange(
                builder.build(false).toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        List<HttpCookie> cookies = new LinkedList<>();
        Objects.requireNonNull(response.getHeaders().get("set-cookie"))
                .stream().forEach(h -> cookies.addAll(HttpCookie.parse(h)));
        List<HttpCookie> authTokenCookies = cookies.stream()
                .filter(c -> AUTH_TOKEN_COOKIE_NAME.equals(c.getName()))
                .collect(Collectors.toList());
        if (authTokenCookies.isEmpty()) {
            LOGGER.warn("{} cookie was not found, authToken refresh could not be completed (provided authToken is {})",
                    AUTH_TOKEN_COOKIE_NAME, currentAuthToken);
        }
        authTokenCookies.forEach(c -> appConfigService.updateAuthToken(c.getValue()));
    }

}
