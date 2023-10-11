package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.midaswebserver.midasweb.models.trader.Algorithm;
import com.midaswebserver.midasweb.models.trader.StockTicker;

public class BacktradeData {

    public String startDate;
    public String endDate;
    public com.midaswebserver.midasweb.models.trader.StockTicker stockTicker;
    public com.midaswebserver.midasweb.models.trader.Algorithm algorithm;
    public double commission;
    public String backtradeType;
    public double stake;
    public double endValue;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public double getEndValue() {
        return endValue;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setEndValue(double endValue) {
        this.endValue = endValue;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public double getStake() {
        return stake;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setStake(double stake) {
        this.stake = stake;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public String getBacktradeType() {
        return backtradeType;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setBacktradeType(String backtradeType) {
        this.backtradeType = backtradeType;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public String getStartDate() {return startDate;}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public String getEndDate() {
        return endDate;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setEndDate(String endDate) {
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
                ", backtradeType='" + backtradeType + '\'' +
                ", stake=" + stake +
                '}';
    }
}
