package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class BacktradeTest extends BacktradeData{
    public double sma;
    public double ema;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public double getSma() {
        return sma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setSma(double sma) {
        this.sma = sma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public double getEma() {
        return ema;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setEma(double ema) {
        this.ema = ema;
    }

    @Override
    public String toString() {
        return "BacktradeTest{" +
                "sma=" + sma +
                ", ema=" + ema +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", stockTicker=" + stockTicker +
                ", algorithm=" + algorithm +
                ", commission=" + commission +
                ", backtradeType='" + backtradeType + '\'' +
                ", stake=" + stake +
                '}';
    }
}
