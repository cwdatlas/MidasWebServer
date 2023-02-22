package com.midaswebserver.midasweb.forms;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TickerForm{
    @JsonIgnoreProperties(ignoreUnknown = true)
    public TickerForm(){
    }
    @NotNull
    @Size(min = 1, max = 4, message = "Ticker Size Invalid")
    @JsonAlias("Symbol")
    private String ticker;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
