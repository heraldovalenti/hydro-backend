package com.aes.dashboard.backend.controller.entities;

import com.fasterxml.jackson.annotation.JsonAlias;

import javax.validation.constraints.NotNull;

public class AuthTokens {

    @NotNull
    private String fedAuth;

    @NotNull
    private String rtFa;

    public AuthTokens() {
    }

    public AuthTokens(String fedAuth, String rtFa) {
        this.fedAuth = fedAuth;
        this.rtFa = rtFa;
    }

    public String getFedAuth() {
        return fedAuth;
    }

    public void setFedAuth(String fedAuth) {
        this.fedAuth = fedAuth;
    }

    public String getRtFa() {
        return rtFa;
    }

    public void setRtFa(String rtFa) {
        this.rtFa = rtFa;
    }

    @Override
    public String toString() {
        return "AuthTokens{" +
                "fedAuth='" + fedAuth + '\'' +
                ", rtFa='" + rtFa + '\'' +
                '}';
    }
}
