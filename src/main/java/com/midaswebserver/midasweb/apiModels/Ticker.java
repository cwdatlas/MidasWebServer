package com.midaswebserver.midasweb.apiModels;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Ticker {
    public Ticker() {
    }

    @NotNull
    private MetaData metaData;

    @NotNull
    private List<StockUnit> timeSeries = new ArrayList<StockUnit>();

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public List<StockUnit> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(List<StockUnit> timeSeries) {
        this.timeSeries = timeSeries;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "metaData=" + metaData +
                ", timeSeries=" + timeSeries +
                '}';
    }
}

