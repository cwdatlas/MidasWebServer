package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class Ticker {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public Ticker() {
    }

    @NotNull
    @JsonProperty("Meta Data")
    private MetaData metaData;

    @NotNull
    @JsonProperty("Time Series (5min)")
    private TimeSeries timeSeries;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public TimeSeries getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
    }
}

