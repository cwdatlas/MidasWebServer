package com.midaswebserver.midasweb.forms;

import com.midaswebserver.midasweb.models.trader.Algorithm;
import com.midaswebserver.midasweb.models.trader.StockTicker;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;

public class BackTraderOptimizeForm {
    //TODO get date annotation
    @NotNull
    @NotBlank
    @DateTimeFormat
    @NotBlank(message = "Please Enter a time series start date")
    public String startDate;
    @NotNull
    @NotBlank
    @DateTimeFormat
    @NotBlank(message = "Please enter a time series end date")
    public String endDate;
    @NotNull
    @NotBlank
    @NotBlank(message = "Please select a stock ticker")
    public StockTicker stockTicker;
    @NotNull
    @NotBlank
    @NotBlank(message = "Please select an algorithm")
    public Algorithm algorithm;
    @NotNull
    @NotBlank
    @Min(0)
    @Max(1)
    @NotBlank(message = "Please enter stake in decimal form (3% -> .03)")
    public float stake;
    @NotNull
    @NotBlank
    @Min(0)
    @Max(1)
    @NotBlank(message = "Please enter commission in decimal form (3% -> .03)")
    public float commission;
    @NotNull
    @NotBlank
    @Max(100)
    @Min(1)
    @NotBlank(message = "Please enter sma length, 1 < 100")
    public int smaOptChange;
    @NotNull
    @NotBlank
    @Max(100)
    @Min(1)
    @NotBlank(message = "Please enter ema length, 1 < 100")
    public int emaOptChange;
    public BackTraderOptimizeForm(){
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

    public int getSmaOptChange() {
        return smaOptChange;
    }

    public void setSmaOptChange(int smaOptChange) {
        this.smaOptChange = smaOptChange;
    }

    public int getEmaOptChange() {
        return emaOptChange;
    }

    public void setEmaOptChange(int emaOptChange) {
        this.emaOptChange = emaOptChange;
    }

    @Override
    public String toString() {
        return "BackTraderOptimizeForm{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", stockTicker=" + stockTicker +
                ", algorithm=" + algorithm +
                ", stake=" + stake +
                ", commission=" + commission +
                ", smaOptChange=" + smaOptChange +
                ", emaOptChange=" + emaOptChange +
                '}';
    }
}
