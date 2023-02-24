package com.midaswebserver.midasweb.forms;

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
    private TimeSeries[] timeSeries;

    @Override
    public String toString() {
        return "Ticker{" +
                "metaData=" + metaData +
                '}';
    }
}

