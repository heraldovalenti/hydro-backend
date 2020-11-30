package com.aes.dashboard.backend.exception;

public class AppConfigMissing extends RuntimeException {

    private String name;

    public AppConfigMissing(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AppConfigMissing{" +
                "name='" + name + '\'' +
                '}';
    }
}
