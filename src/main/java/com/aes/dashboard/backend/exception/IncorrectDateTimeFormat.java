package com.aes.dashboard.backend.exception;

public class IncorrectDateTimeFormat extends RuntimeException {

    private String input;
    private int index;
    private String format;

    public IncorrectDateTimeFormat(String input, int index, String format) {
        this.input = input;
        this.index = index;
        this.format = format;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "IncorrectDateTimeFormat{" +
                "input='" + input + '\'' +
                ", index=" + index +
                ", format='" + format + '\'' +
                '}';
    }
}
