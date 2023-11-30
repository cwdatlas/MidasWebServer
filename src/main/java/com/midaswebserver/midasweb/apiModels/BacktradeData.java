package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.midaswebserver.midasweb.models.trader.Algorithm;
import com.midaswebserver.midasweb.models.trader.StockTicker;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * BacktradeData is the parent class to store and encode into json
 *
 * @Author Aidan Scott
 */
public class BacktradeData {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("start_date")
    @NotNull
    //start date is used to set the start date to trade from in the midas microservice
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("end_date")
    @NotNull
    // end date is used to set the end date to trade to in the midas microservice
    private LocalDate endDate;
    @JsonProperty("stock_ticker")
    //ticker symbol used to specify which stock data will be tested
    private StockTicker stockTicker;
    @JsonProperty
    //algorithm is used to specify the specific algorithm used during testing
    private Algorithm algorithm;
    @JsonProperty
    //commission is used to specify how much commission each trade incurs, indecimal form
    private double commission;
    @JsonProperty
    //stake is the percentage of total buying power each trade uses, in percentage form
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
