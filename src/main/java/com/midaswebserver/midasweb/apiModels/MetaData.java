package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

/**
 * MetaData is used to store symbol and other information from a stock query
 */
public class MetaData{

    @NotNull
    private String information;
    /**
     * an alias symbol is ticker, which can be seen elsewhere in the application
     */
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

    /**
     * SetData is used to convert a 3rd party object to a native object that can be validated
     * @param information
     * @param symbol
     * @param lastRefreshed
     * @param timeZone
     * @param interval
     * @param outputSize
     */

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

