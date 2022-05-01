package com.aes.dashboard.backend.service.aesLatestData;

import com.aes.dashboard.backend.service.AppConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    private RestTemplateBuilder restTemplateBuilder;
    private AppConfigService appConfigService;

    public AESDataService(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${aes.one-drive.url}") String url,
            AppConfigService appConfigService) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.url = url;
        this.appConfigService = appConfigService;
    }

    public List<DataItem> getLatestData() {
        String authToken = appConfigService.getAuthToken();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url)
                .queryParam("authToken", encodeParam(authToken));
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        RestTemplate restTemplate = this.restTemplateBuilder.build();
        restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
        ResponseEntity<LatestDataItem[]> response = restTemplate.getForEntity(
                builder.build(false).toUriString(),
                LatestDataItem[].class);
        List<DataItem> result = new LinkedList<>();
        Arrays.stream(response.getBody()).forEach(latestDataItem -> result.addAll(latestDataItem.getData()));
        return result;
    }

    public void refreshAuthToken() {
        String currentAuthToken = appConfigService.getAuthToken();
        refreshAuthToken(currentAuthToken);
    }

    public void refreshAuthToken(String currentAuthToken) {
        String oneDriveBaseUrl = "https://aescloud-my.sharepoint.com/personal/edgardo_mendez_aes_com/_api/contextinfo";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(oneDriveBaseUrl);
//                .queryParam("skipSignal", encodeParam("true"))
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        RestTemplate restTemplate = this.restTemplateBuilder.build();
        restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);

        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json;odata=verbose");
        headers.add("accept-encoding", "gzip, deflate, br");
        headers.add("accept-language", "en,en-US;q=0.9,es;q=0.8");
        headers.add("cache-control", "no-cache");
        headers.add("content-length", "0");
        headers.add("content-type", "application/json;odata=verbose");
        headers.add("origin", "https://aescloud-my.sharepoint.com");
        headers.add("pragma", "no-cache");
        headers.add("referer", "https://aescloud-my.sharepoint.com/personal/edgardo_mendez_aes_com/_layouts/15/onedrive.aspx?id=%2Fpersonal%2Fedgardo%5Fmendez%5Faes%5Fcom%2FDocuments%2FDatosCCO");
        headers.add("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"99\", \"Google Chrome\";v=\"99\"");
        headers.add("sec-ch-ua-mobile", "?0");
        headers.add("sec-ch-ua-platform", "\"Linux\"");
        headers.add("sec-fetch-dest", "empty");
        headers.add("sec-fetch-mode", "cors");
        headers.add("sec-fetch-site", "same-origin");
        headers.add("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");


        String cookieHeaderValue = String.format("KillSwitchOverrides_enableKillSwitches=; KillSwitchOverrides_disableKillSwitches=; %s=%s", AUTH_TOKEN_COOKIE_NAME, currentAuthToken);
        headers.add("cookie", cookieHeaderValue);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build(false).toUriString(),
                HttpMethod.POST,
                entity,
                String.class);

        List<HttpCookie> cookies = new LinkedList<>();
        Objects.requireNonNull(response.getHeaders().get("set-cookie"))
                .stream().forEach(h -> cookies.addAll(HttpCookie.parse(h)));
        LOGGER.debug("retrieved cookies: {}", cookies);
        List<HttpCookie> authTokenCookies = cookies.stream()
                .filter(c -> AUTH_TOKEN_COOKIE_NAME.equals(c.getName()))
                .collect(Collectors.toList());
        if (authTokenCookies.isEmpty()) {
            LOGGER.warn("{} cookie was not found, authToken refresh could not be completed (provided authToken is {})",
                    AUTH_TOKEN_COOKIE_NAME, currentAuthToken);
        }
        authTokenCookies.forEach(c -> appConfigService.updateAuthToken(c.getValue()));
    }

    private String encodeParam(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
