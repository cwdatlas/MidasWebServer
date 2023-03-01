package com.midaswebserver.midasweb.forms;

import com.crazzyghost.alphavantage.parameters.Interval;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * StocDataRequestForm is used in any situation when a client calls for stock data
 */
public class StockDataRequestForm {
    public StockDataRequestForm(){
    }
    @NotNull
    @Size(min = 1, max = 4)
    public String ticker;
    @NotNull
    public Interval interval;

    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public Interval getInterval() {
        return interval;
    }
    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "StockDataRequestForm{" +
                "ticker='" + ticker + '\'' +
                ", interval=" + interval +
                '}';
    }
}
