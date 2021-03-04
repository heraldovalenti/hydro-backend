package com.aes.dashboard.backend.service.snih;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SNIHDataRootWrapper {

    private String __type;
    private List<SNIHDataMedicion> Mediciones;
    private String MsgErr;
    private Boolean RespuestaOK;

    public SNIHDataRootWrapper() {
    }

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }

    public List<SNIHDataMedicion> getMediciones() {
        return Mediciones;
    }

    @JsonProperty("Mediciones")
    public void setMediciones(List<SNIHDataMedicion> mediciones) {
        Mediciones = mediciones;
    }

    @JsonProperty("MsgErr")
    public String getMsgErr() {
        return MsgErr;
    }

    public void setMsgErr(String msgErr) {
        MsgErr = msgErr;
    }

    @JsonProperty("RespuestaOK")
    public Boolean getRespuestaOK() {
        return RespuestaOK;
    }

    public void setRespuestaOK(Boolean respuestaOK) {
        RespuestaOK = respuestaOK;
    }
}
