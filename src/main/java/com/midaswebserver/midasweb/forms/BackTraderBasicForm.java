package com.midaswebserver.midasweb.forms;

import com.midaswebserver.midasweb.models.trader.Algorithm;
import com.midaswebserver.midasweb.models.trader.StockTicker;
import jakarta.validation.constraints.*;

public class BackTraderBasicForm {

//TODO get date annotation
    @NotNull
    @NotBlank(message = "Please Enter a time series start date")
    public String startDate;
    @NotNull
    @NotBlank(message = "Please enter a time series end date")
    public String endDate;
    @NotNull
    @NotBlank(message = "Please select a stock ticker")
    public StockTicker stockTicker;
    @NotNull
    @NotBlank(message = "Please select an algorithm")
    public Algorithm algorithm;
    @NotNull
    @NotBlank(message = "Please enter stake in decimal form (3% -> .03)")
    public float stake;
    @NotNull
    @NotBlank(message = "Please enter commission in decimal form (3% -> .03)")
    public float commission;
    @NotNull
    @Max(100)
    @Min(1)
    @NotBlank(message = "Please enter sma length, 1 < 100")
    public int smaLength;
    @NotNull
    @Max(100)
    @Min(1)
    @NotBlank(message = "Please enter ema length, 1 < 100")
    public int emaLength;

    public BackTraderBasicForm() {
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public StockTicker getStockTicker() {
        return stockTicker;
    }

    public void setStockTicker(StockTicker stockTicker) {
        this.stockTicker = stockTicker;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public float getStake() {
        return stake;
    }

    public void setStake(float stake) {
        this.stake = stake;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public int getSmaLength() {
        return smaLength;
    }

    public void setSmaLength(int smaLength) {
        this.smaLength = smaLength;
    }

    public int getEmaLength() {
        return emaLength;
    }

    public void setEmaLength(int emaLength) {
        this.emaLength = emaLength;
    }

    @Override
    public String toString() {
        return "BackTraderBasicForm{" +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", stockTicker=" + stockTicker +
                ", algorithm=" + algorithm +
                ", stake=" + stake +
                ", commission=" + commission +
                ", smaLength=" + smaLength +
                ", emaLength=" + emaLength +
                '}';
    }
}
