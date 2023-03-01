package com.midaswebserver.midasweb.services;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.midaswebserver.midasweb.apiModels.Ticker;

public class test {
    public static void main(String args[]) {
        Config cfg = Config.builder()
                    .key("XIHOPJON41H6MNHT")
                    .timeOut(10)
                    .build();
        AlphaVantage.api().init(cfg);

        String symbol = "IBM";
        //TimeSeriesResponse timeSeries = getTimeSeriesInfo(symbol, Interval.WEEKLY, OutputSize.COMPACT);
        TimeSeriesResponse timeSeries = getTimeSeriesInfo();
        System.out.println(timeSeries.toString());
    }

private static Ticker getTimeSeriesInfo() {
    TimeSeriesResponse timeSeries = AlphaVantage.api()
            .timeSeries()
            .intraday()
            .forSymbol("IBM")
            .interval(Interval.SIXTY_MIN)
            .outputSize(OutputSize.COMPACT)
            .fetchSync();
    Ticker ticker = new Ticker();
    ticker.setTimeSeries(timeSeries.getStockUnits());
    return null;
    }
}

