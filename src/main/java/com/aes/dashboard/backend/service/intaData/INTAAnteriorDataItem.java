package com.aes.dashboard.backend.service.intaData;

import java.time.LocalDateTime;

public class INTAAnteriorDataItem {

    private LocalDateTime date;
    private double lluvia;
    private double intensidadLluvia;

    public INTAAnteriorDataItem(LocalDateTime date, double lluvia, double intensidadLluvia) {
        this.date = date;
        this.lluvia = lluvia;
        this.intensidadLluvia = intensidadLluvia;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getLluvia() {
        return lluvia;
    }

    public void setLluvia(double lluvia) {
        this.lluvia = lluvia;
    }

    public double getIntensidadLluvia() {
        return intensidadLluvia;
    }

    public void setIntensidadLluvia(double intensidadLluvia) {
        this.intensidadLluvia = intensidadLluvia;
    }

    @Override
    public String toString() {
        return "INTAAnteriorDataItem{" +
                "date=" + date +
                ", lluvia=" + lluvia +
                ", intensidadLluvia=" + intensidadLluvia +
                '}';
    }
}
