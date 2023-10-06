package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.models.trader.Algorithm;
import com.midaswebserver.midasweb.models.trader.StockTicker;

import java.util.Map;

/**
 * @Author Aidan Scott
 * BackTesterService manages api requests and logic for backtesting activities
 * Webull will be queried in the future for realtime stock data
 */
public interface BackTesterService{

    Map<String, Double> Backtrade(String startDate, String endDate, int smaOptChange, int emaOptChange,
                                  StockTicker stockticker, double stake, Algorithm algorithm, double commission);
    Map<String, Double> optimize(String startDate, String endDate, int sma, int ema,
                                 StockTicker stockticker, double stake, Algorithm algorithm, double commission);
}
