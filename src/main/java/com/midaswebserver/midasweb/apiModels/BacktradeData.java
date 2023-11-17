package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.midaswebserver.midasweb.models.trader.Algorithm;
import com.midaswebserver.midasweb.models.trader.StockTicker;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class BacktradeData {

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @NotNull
    @Past
    private LocalDate startDate;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @NotNull
    @Past
    private LocalDate endDate;
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
    public LocalDate getStartDate() {
        return startDate;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public LocalDate getEndDate() {
        return endDate;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setEndDate(LocalDate endDate) {
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
