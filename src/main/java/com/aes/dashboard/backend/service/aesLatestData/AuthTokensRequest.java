package com.aes.dashboard.backend.service.aesLatestData;

import com.aes.dashboard.backend.controller.entities.AuthTokens;

public class AuthTokensRequest {

    private AuthTokens authTokens;

    public AuthTokensRequest(AuthTokens authTokens) {
        this.authTokens = authTokens;
    }

    public AuthTokens getAuthTokens() {
        return authTokens;
    }

    public void setAuthTokens(AuthTokens authTokens) {
        this.authTokens = authTokens;
    }

}
