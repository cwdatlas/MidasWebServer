package com.midaswebserver.midasweb.services;

import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.midaswebserver.midasweb.apiModels.Ticker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 * The lack of total queries that can be done using AlphaVantage limits total tests that can be done to 5/min
 * more tests are expected by a multiple of 3-5 to increase in the future.
 * webull tests will have more tests
 */
@SpringBootTest
public class TickerSerivceImpTest {
    @Autowired
    private TickerService service;

    /**
     * getRequestOnURLGoodTest1: goodInputTest
     * interval 60 min, outputsize compact
     */
    @Test
    public void getRequestOnURLGoodTest1() {
        String symbol = "IBM";
        Ticker ticker = service.getTimeSeriesInfo(symbol, Interval.SIXTY_MIN, OutputSize.COMPACT);
        assertTrue(symbol + " was null", ticker.getMetaData().getSymbol()!=null);
    }

    /**
     * getRequestONURLGoodTest2: GoodInputTest
     * interval 1min, outputsize full
     */
    @Test
    public void getRequestOnURLGoodTest2() {
        String symbol = "AAPL";
        Ticker ticker = service.getTimeSeriesInfo(symbol, Interval.ONE_MIN, OutputSize.FULL);
        assertTrue(symbol + " was null", ticker.getMetaData().getSymbol()!=null);
    }
    /**
     * getRequestOnURLBadTest: badInputTest
     * Interval MONTHLY, outputsize compact (both options work)
     * only interval is bad
     */
    @Test
    public void getRequestOnURLBadTest1() {
        String symbol = "IBM";
        Ticker ticker = service.getTimeSeriesInfo(symbol, Interval.MONTHLY, OutputSize.COMPACT);
        assertFalse(symbol + " wasn't null", ticker != null);
    }
    /**
     * getRequestOnURLBadTest: badInputTest
     * only symbol is bad
     */
    @Test
    public void getRequestOnURLBadTest2() {
        String symbol = "ZOOM";
        Ticker ticker = service.getTimeSeriesInfo(symbol, Interval.SIXTY_MIN, OutputSize.COMPACT);
        assertFalse(symbol + " wasn't null", ticker != null);
    }
    /**
     * getRequestOnURLBadTest: badInputTest
     * both symbol and interval are bad
     */
    @Test
    public void getRequestOnURLCrazyTest() {
        String symbol = null;
        Ticker ticker = service.getTimeSeriesInfo(symbol, Interval.DAILY, OutputSize.COMPACT);
        assertFalse(symbol + " wasn't null", ticker != null);
    }
}
