package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class MetaData{
    @JsonIgnoreProperties(ignoreUnknown = true)
    MetaData(){
    }

    @NotNull
    @JsonProperty("1. Information")
    private String info;

    @NotNull
    @JsonProperty("2. Symbol")
    private String symbol;

    @NotNull
    @JsonProperty("3. Last Refreshed")
    private String lastRefreshed;

    @NotNull
    @JsonProperty("4. Interval")
    private String interval;

    @NotNull
    @JsonProperty("5. Output Size")
    private String outputSize;

    @NotNull
    @JsonProperty("6. Time Zone")
    private String timeZone;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLastRefreshed() {
        return lastRefreshed;
    }

    public void setLastRefreshed(String lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(String outputSize) {
        this.outputSize = outputSize;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return "MetaData{" +
                "info='" + info + '\'' +
                ", symbol='" + symbol + '\'' +
                ", lastRefreshed='" + lastRefreshed + '\'' +
                ", interval='" + interval + '\'' +
                ", outputSize='" + outputSize + '\'' +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}

