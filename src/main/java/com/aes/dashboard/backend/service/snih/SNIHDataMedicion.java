package com.aes.dashboard.backend.service.snih;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.aes.dashboard.backend.config.GlobalConfigs.UTC_ZONE_ID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SNIHDataMedicion {

    private String FechaHora;
    private SNIHDataMedicionItem[] Mediciones;

    public SNIHDataMedicion() {
    }

    public String getFechaHora() {
        return FechaHora;
    }

    @JsonProperty("FechaHora")
    public void setFechaHora(String fechaHora) {
        FechaHora = fechaHora;
    }

    public SNIHDataMedicionItem[] getMediciones() {
        return Mediciones;
    }

    @JsonProperty("Mediciones")
    public void setMediciones(SNIHDataMedicionItem[] mediciones) {
        Mediciones = mediciones;
    }

    public LocalDateTime parseFechaHora() {
        String dateMilisRaw = FechaHora.substring(
                FechaHora.indexOf("(") + 1,
                FechaHora.indexOf(")")
        );
        Long dateMillis = Long.parseLong(dateMilisRaw);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateMillis), ZoneId.of(UTC_ZONE_ID));
    }

    public Integer parseCodigo() {
        return Mediciones[0].getCodigo();
    }

    public Double parseValor() {
        return Mediciones[0].getValor();
    }
}
