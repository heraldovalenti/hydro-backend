package com.aes.dashboard.backend.service.snih;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SNIHDataRoot {

    private SNIHDataRootWrapper d;

    public SNIHDataRoot() {
    }

    public SNIHDataRootWrapper getD() {
        return d;
    }

    public void setD(SNIHDataRootWrapper d) {
        this.d = d;
    }
}
