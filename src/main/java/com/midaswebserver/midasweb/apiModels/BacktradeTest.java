package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class BacktradeTest extends BacktradeData{
    public int sma;
    public int ema;

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
