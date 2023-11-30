package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BacktradeOptimize is the child class of BacktradeData
 * it is used to send optimization data to the microservice
 *
 * @Author Aidan Scott
 */
public class BacktradeOptimize extends BacktradeData {
    @JsonProperty("start_sma")
    //startSma specifies the first part of the sma range to be tested with
    private int startSma;
    @JsonProperty("end_sma")
    //endSma specifies the last part of the sma range to be tested with
    private int endSma;
    @JsonProperty("start_ema")
    //startEma specifies the first part of the ema range to be tested with
    private int startEma;
    @JsonProperty("end_ema")
    //endEma specifies the last part of the ema range to be tested with
    private int endEma;

    public int getStartSma() {
        return startSma;
    }

    public void setStartSma(int startSma) {
        this.startSma = startSma;
    }

    public int getEndSma() {
        return endSma;
    }

    public void setEndSma(int endSma) {
        this.endSma = endSma;
    }

    public int getStartEma() {
        return startEma;
    }

    public void setStartEma(int startEma) {
        this.startEma = startEma;
    }

    public int getEndEma() {
        return endEma;
    }

    public void setEndEma(int endEma) {
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
