package com.midaswebserver.midasweb.controllers;

import com.midaswebserver.midasweb.forms.TickerForm;
import com.midaswebserver.midasweb.models.Ticker;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class TickerRestController {
    public Ticker getTickerData(String function){
        RestTemplate template = new RestTemplate();
        TickerForm tickForm = template.getForObject("https://www.alphavantage.co/query?{}", TickerForm.class);
        Ticker ticker = new Ticker();
        ticker.setTicker(tickForm.getTicker());
        return ticker;
    }
}

