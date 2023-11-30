package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * BacktradeTest is the child class of BacktradeData
 * it is used to send backtrace test data to the microservice
 *
 * @Author Aidan Scott
 */
public class BacktradeTest extends BacktradeData {
    //sma is the metric that will be used to trade in the microservice
    private int sma;
    //ema is the metric that will be used to trade in the microservice
    private int ema;

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
                ", startDate='" + getStartDate() + '\'' +
                ", endDate='" + getEndDate() + '\'' +
                ", stockTicker=" + getStockTicker() +
                ", algorithm=" + getAlgorithm() +
                ", commission=" + getCommission() +
                ", stake=" + getStake() +
                '}';
    }
}
