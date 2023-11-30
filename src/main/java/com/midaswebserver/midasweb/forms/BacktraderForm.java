package com.midaswebserver.midasweb.forms;

import com.midaswebserver.midasweb.models.trader.Algorithm;
import com.midaswebserver.midasweb.models.trader.StockTicker;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * BacktarderForm validates the majority of incoming backtesting variables
 * formatting validation doesnt validate.
 *
 * @Author Aidan Scott
 */
public class BacktraderForm {

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "Please Enter a time series start date")
    public String startDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "Please enter a time series end date")
    public String endDate;
    @NotNull
    public StockTicker stockTicker;
    @NotNull
    public Algorithm algorithm;
    @NotNull(message = "Please enter stake in percentage form (.03 -> 3%)")
    @Min(0)
    @Max(100)
    public double stake;
    @NotNull(message = "Please enter commission in decimal form (3% -> .03)")
    @Min(0)
    @Max(1)
    public double commission;
    @NotNull(message = "Please enter sma length, 1 < 100")
    @Max(100)
    @Min(1)
    public int sma;
    @NotNull(message = "Please enter ema length, 1 < 100")
    @Max(100)
    @Min(1)
    public int ema;

    public BacktraderForm() {
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

    public int getSma() {
        return sma;
    }

    public void setSma(int sma) {
        this.sma = sma;
    }

    public int getEma() {
        return ema;
    }

    public void setEma(int ema) {
        this.ema = ema;
    }

    @Override
    public String toString() {
        return "BacktraderForm{" +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", stockTicker=" + stockTicker +
                ", algorithm=" + algorithm +
                ", stake=" + stake +
                ", commission=" + commission +
                ", smaLength=" + sma +
                ", emaLength=" + ema +
                '}';
    }
}
