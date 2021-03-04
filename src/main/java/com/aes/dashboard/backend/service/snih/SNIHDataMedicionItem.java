package com.aes.dashboard.backend.service.snih;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SNIHDataMedicionItem {

    private Integer Codigo;
    private Double Valor;

    public SNIHDataMedicionItem() {
    }

    public Integer getCodigo() {
        return Codigo;
    }

    @JsonProperty("Codigo")
    public void setCodigo(Integer codigo) {
        Codigo = codigo;
    }

    public Double getValor() {
        return Valor;
    }

    @JsonProperty("Valor")
    public void setValor(Double valor) {
        Valor = valor;
    }
}
