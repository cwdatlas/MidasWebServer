package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.controllers.TickerRestController;
import com.midaswebserver.midasweb.models.Ticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TickerServiceImp implements TickerService{
    @Autowired
    private TickerRestController tickCon;
    private final String apiKey = "XIHOPJON41H6MNHT"; //I think this should be secret and have some logic behind it so it isnt found

    @Override
    public Ticker getTickerInfo(String symbol) {
        Ticker ticker = tickCon.getTickerData("function=TIME_SERIES_INTRADAY&symbol="+symbol+"&interval=5min&apikey=" + apiKey);
        return ticker;
    }
}
