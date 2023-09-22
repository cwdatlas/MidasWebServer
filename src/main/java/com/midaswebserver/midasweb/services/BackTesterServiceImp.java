package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.models.trader.Algorithm;
import com.midaswebserver.midasweb.models.trader.StockTicker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Aidan Scott
 * BackTesterService manages api requests and logic for backtesting activities
 * Webull will be queried in the future for realtime stock data
 */
@Service
public class BackTesterServiceImp implements BackTesterService{
    private static final Logger log = LoggerFactory.getLogger(com.midaswebserver.midasweb.services.BackTesterServiceImp.class);

    /**
     * Backtrade consumes a variables, checks how profitable they are trading them in the past, then returns the ending balance
     * @param startDate
     * @param endDate
     * @param smaOptChange
     * @param emaOptChange
     * @param stockticker
     * @param stake
     * @param algorithm
     * @param commission
     * @return [endBalance]
     */
    @Override
    public Map<String, Double> Backtrade(String startDate, String endDate, int smaOptChange, int emaOptChange, StockTicker stockticker, double stake, Algorithm algorithm, double commission) {
        Map<String, Double> optParams = new HashMap<>();
        double balance = 300.90;
        optParams.put("endBalance", balance);
        return optParams;


    }

    /**
     * Optimize takes variables and checks how profitable they would be while varying some variables each test. It will return
     * with the most profitable changing variables. If you plug the output into the Backtrade function, you will get the same
     * balance as you got from this function.
     * @param startDate
     * @param endDate
     * @param sma
     * @param ema
     * @param stockticker
     * @param stake
     * @param algorithm
     * @param commission
     * @return [optSMA, optEMA, optVolume, endBalance]
     */
    @Override
    public Map<String, Double> Optimize(String startDate, String endDate, int sma, int ema, StockTicker stockticker, double stake, Algorithm algorithm, double commission) {
        Map<String, Double> optParams = new HashMap<>();
        double balance = 400.90;
        double optSMA = 30;
        double optEMA = 20;
        double optVolume = 1000;
        optParams.put("optSMA", optSMA);
        optParams.put("optEMA", optEMA);
        optParams.put("optVolume", optVolume);
        optParams.put("endBalance", balance);
        return optParams;
    }
}
