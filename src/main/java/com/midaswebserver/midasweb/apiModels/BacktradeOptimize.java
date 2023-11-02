package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class BacktradeOptimize extends BacktradeData {
    @JsonProperty("start_sma")
    private double startSma;
    @JsonProperty("end_sma")
    private double endSma;
    @JsonProperty("start_ema")
    private double startEma;
    @JsonProperty("end_ema")
    private double endEma;

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
                ", startDate='" + getStartDate() + '\'' +
                ", endDate='" + getEndDate() + '\'' +
                ", stockTicker=" + getStockTicker() +
                ", algorithm=" + getAlgorithm() +
                ", commission=" + getCommission() +
                '}';
    }
}
