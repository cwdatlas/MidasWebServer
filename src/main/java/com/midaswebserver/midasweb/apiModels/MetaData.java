package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class MetaData{

    @NotNull
    private String information;
    @NotNull
    private String symbol;
    @NotNull
    private String lastRefreshed;
    @NotNull
    private String timeZone;
    @NotNull
    private String interval;
    @NotNull
    private String outputSize;

    public MetaData(){}

    public void setData(String information, String symbol, String lastRefreshed, String timeZone, String interval, String outputSize){
        this.information = information;
        this.symbol = symbol;
        this.lastRefreshed = lastRefreshed;
        this.timeZone = timeZone;
        this.interval = interval;
        this.outputSize = outputSize;
    }
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
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

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MetaData{");
        sb.append("information=" + information);
        sb.append("symbol=" + symbol);
        sb.append("lastRefreshed=" + lastRefreshed);
        sb.append("timeZone=" + timeZone);
        if(interval != null) sb.append("interval=" + interval);
        if(outputSize != null) sb.append("outputSize=" + outputSize);
        sb.append("}");
        return sb.toString();
    }
}

