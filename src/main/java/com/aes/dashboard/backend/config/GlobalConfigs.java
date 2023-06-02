package com.aes.dashboard.backend.config;

import java.time.Duration;

public class GlobalConfigs {

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String REQUEST_PARAM_DATE_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String EXPORT_FILE_NAME_DATE_OUTPUT_FORMAT = "yyyyMMdd";
    public static final String EXPORT_FILE_CONTENT_DATE_OUTPUT_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String SALTA_ZONE_ID = "America/Argentina/Salta";
    public static final String UTC_ZONE_ID = "UTC";

    public static final Long DATA_ORIGIN_AES = 1L;
    public static final Long DATA_ORIGIN_WU = 2L;
    public static final Long DATA_ORIGIN_INTA_SIGA = 3L;
    public static final Long DATA_ORIGIN_SMG = 4L;
    public static final Long DATA_ORIGIN_INTA_ANTERIOR = 5L;
    public static final Long DATA_ORIGIN_WEATHERLINK = 8L;

    public static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);

}
