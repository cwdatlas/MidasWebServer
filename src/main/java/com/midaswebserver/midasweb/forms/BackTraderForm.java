package com.midaswebserver.midasweb.forms;

import com.midaswebserver.midasweb.models.trader.Algorithm;
import com.midaswebserver.midasweb.models.trader.StockTicker;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

public class BackTraderForm {

//TODO get date annotation
    @NotNull
    @DateTimeFormat
    @NotBlank(message = "Please Enter a time series start date")
    public String startDate;
    @NotNull
    @DateTimeFormat
    @NotBlank(message = "Please enter a time series end date")
    public String endDate;
    @NotNull
    public StockTicker stockTicker;
    @NotNull
    public Algorithm algorithm;
    @NotNull(message = "Please enter stake in decimal form (3% -> .03)")
    @Min(0)
    @Max(1)
    public double stake;
    @NotNull(message = "Please enter commission in decimal form (3% -> .03)")
    @Min(0)
    @Max(1)
    public double commission;
    @NotNull(message = "Please enter sma length, 1 < 100")
    @Max(100)
    @Min(1)
    public int smaLength;
    @NotNull(message = "Please enter ema length, 1 < 100")
    @Max(100)
    @Min(1)
    public int emaLength;

    public BackTraderForm() {
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

    public double getStake() {
        return stake;
    }

    public void setStake(double stake) {
        this.stake = stake;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
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
        return "BackTraderForm{" +
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
