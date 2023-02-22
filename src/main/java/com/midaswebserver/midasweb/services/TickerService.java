package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.models.Ticker;
public interface TickerService{
    Ticker getTickerInfo(String symbol);

}
