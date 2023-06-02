package com.aes.dashboard.backend.config;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import static com.aes.dashboard.backend.config.GlobalConfigs.REQUEST_TIMEOUT;

@Configuration
public class SSLDisablingRestTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(SSLDisablingRestTemplate.class);

    @Bean(name = "sslDisablingRestTemplate")
    public RestTemplate restTemplate(
            RestTemplateBuilder builder,
            @Value("${backend.http.proxy.host}") String httpProxyHost,
            @Value("${backend.http.proxy.port}") Integer httpProxyPort)
            throws NoSuchAlgorithmException, KeyManagementException {

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        HttpHost proxy = null;
        if (httpProxyHost != "" && httpProxyPort != null) {
            proxy = new HttpHost(httpProxyHost, httpProxyPort);
            LOGGER.info("Configured HTTP Proxy with {} and {}: {}", httpProxyHost, httpProxyPort, proxy.toString());
        }
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setProxy(proxy)
                .build();
        HttpComponentsClientHttpRequestFactory customRequestFactory = new HttpComponentsClientHttpRequestFactory();
        customRequestFactory.setHttpClient(httpClient);

        return builder
                .requestFactory(() -> customRequestFactory)
                .setConnectTimeout(REQUEST_TIMEOUT)
                .build();
    }

}
