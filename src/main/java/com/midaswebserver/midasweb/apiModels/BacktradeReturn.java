package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class BacktradeReturn {

    public int sma;
    public int ema;
    public double endingValue;


    @JsonIgnoreProperties(ignoreUnknown = true)
    public double getEndingValue() {
        return endingValue;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setEndingValue(double endingValue) {
        this.endingValue = endingValue;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public int getSma() {
        return sma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setSma(int sma) {
        this.sma = sma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public int getEma() {
        return ema;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setEma(int ema) {
        this.ema = ema;
    }

    @Override
    public String toString() {
        return "BacktradeReturn{" +
                "sma=" + sma +
                ", ema=" + ema +
                ", endingValue=" + endingValue +
                '}';
    }
}
