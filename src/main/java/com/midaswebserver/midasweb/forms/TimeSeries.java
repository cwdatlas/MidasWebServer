package com.midaswebserver.midasweb.forms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class TimeSeries {
    @JsonIgnoreProperties(ignoreUnknown = true)
    TimeSeries(){

    }
    @NotNull
    @JsonProperty("1. open")
    private long open;

    @NotNull
    @JsonProperty("2. high")
    private long high;

    @NotNull
    @JsonProperty("3. low")
    private long low;

    @NotNull
    @JsonProperty("4. close")
    private long close;

    @NotNull
    @JsonProperty("5. volume")
    private long volume;

    public long getOpen() {
        return open;
    }

    public void setOpen(long open) {
        this.open = open;
    }

    public long getHigh() {
        return high;
    }

    public void setHigh(long high) {
        this.high = high;
    }

    public long getLow() {
        return low;
    }

    public void setLow(long low) {
        this.low = low;
    }

    public long getClose() {
        return close;
    }

    public void setClose(long close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "TimeSeries{" +
                "open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                '}';
    }
}
