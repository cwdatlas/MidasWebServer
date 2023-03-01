package com.midaswebserver.midasweb.services;

import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.midaswebserver.midasweb.apiModels.Ticker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class TickerSerivceImpTest {
    @Autowired
    private TickerService service;

    @Test//this is a proof of concept test using only local logic
    public void getRequestOnURLTest() {
        String symbol = "IBM";
        Ticker ticker = service.getTimeSeriesInfo(symbol, Interval.SIXTY_MIN, OutputSize.COMPACT);
        assertTrue("Symbol was not " + symbol, ticker.getMetaData().getSymbol().equals(symbol));
    }

}
