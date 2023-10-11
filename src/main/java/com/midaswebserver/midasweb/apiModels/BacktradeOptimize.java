package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class BacktradeOptimize extends BacktradeData {
    public double startSma;
    public double endSma;
    public double startEma;
    public double endEma;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public double getStartSma() {
        return startSma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setStartSma(double startSma) {
        this.startSma = startSma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public double getEndSma() {
        return endSma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setEndSma(double endSma) {
        this.endSma = endSma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public double getStartEma() {
        return startEma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setStartEma(double startEma) {
        this.startEma = startEma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public double getEndEma() {
        return endEma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setEndEma(double endEma) {
        this.endEma = endEma;
    }

    @Override
    public String toString() {
        return "BacktradeOptimize{" +
                "startSma=" + startSma +
                ", endSma=" + endSma +
                ", startEma=" + startEma +
                ", endEma=" + endEma +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", stockTicker=" + stockTicker +
                ", algorithm=" + algorithm +
                ", commission=" + commission +
                ", backtradeType='" + backtradeType + '\'' +
                '}';
    }
}
