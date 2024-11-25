package com.aes.dashboard.backend.exception;

import com.aes.dashboard.backend.model.Observation;

public class WrongSortException extends RuntimeException {

    private Observation o1;
    private Observation o2;

    public WrongSortException(Observation o1, Observation o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    public Observation getO1() {
        return o1;
    }

    public void setO1(Observation o1) {
        this.o1 = o1;
    }

    public Observation getO2() {
        return o2;
    }

    public void setO2(Observation o2) {
        this.o2 = o2;
    }

    @Override
    public String toString() {
        return "WrongSortException{" +
                "o1=" + o1 +
                ", o2=" + o2 +
                '}';
    }
}
