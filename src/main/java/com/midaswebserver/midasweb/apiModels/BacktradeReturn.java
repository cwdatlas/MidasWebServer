package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BacktradeReturn {

    @JsonProperty
    private int sma;
    @JsonProperty
    private int ema;
    @JsonProperty("ending_value")
    private float endingValue;

    private String errorCode;
    private String message;

    public boolean hasErrors(){
        return (errorCode == null);
    }
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public float getEndingValue() {
        return endingValue;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setEndingValue(float endingValue) {
        this.endingValue = endingValue;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public int getSma() {
        return sma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setSma(int sma) {
        this.sma = sma;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public int getEma() {
        return ema;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public void setEma(int ema) {
        this.ema = ema;
    }

    @Override
    public String toString() {
        return "BacktradeReturn{" +
                "sma=" + sma +
                ", ema=" + ema +
                '}';
    }
}
