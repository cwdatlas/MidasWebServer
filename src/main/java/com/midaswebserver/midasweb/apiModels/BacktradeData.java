package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.midaswebserver.midasweb.models.trader.Algorithm;
import com.midaswebserver.midasweb.models.trader.StockTicker;
import com.midaswebserver.midasweb.apiModels.BacktradeDate;

public class BacktradeData {

    @JsonProperty("start_date")
    private BacktradeDate startDate;
    @JsonProperty("end_date")
    private BacktradeDate endDate;
    @JsonProperty("stock_ticker")
    private StockTicker stockTicker;
    @JsonProperty
    private Algorithm algorithm;
    @JsonProperty
    private double commission;
    @JsonProperty
    private double stake;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public double getStake() {
        return stake;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setStake(double stake) {
        this.stake = stake;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public BacktradeDate getStartDate() {
        return startDate;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setStartDate(BacktradeDate startDate) {
        this.startDate = startDate;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public BacktradeDate getEndDate() {
        return endDate;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setEndDate(BacktradeDate endDate) {
        this.endDate = endDate;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public StockTicker getStockTicker() {
        return stockTicker;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setStockTicker(StockTicker stockTicker) {
        this.stockTicker = stockTicker;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public double getCommission() {
        return commission;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setCommission(double commission) {
        this.commission = commission;
    }

    @Override
    public String toString() {
        return "BacktradeData{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", stockTicker=" + stockTicker +
                ", algorithm=" + algorithm +
                ", commission=" + commission +
                ", stake=" + stake +
                '}';
    }
}
