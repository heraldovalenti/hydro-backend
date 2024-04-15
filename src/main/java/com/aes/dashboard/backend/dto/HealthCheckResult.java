package com.aes.dashboard.backend.dto;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class HealthCheckResult {

    private String serviceName;
    private Map<String, Object> info;

    public HealthCheckResult(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
}
