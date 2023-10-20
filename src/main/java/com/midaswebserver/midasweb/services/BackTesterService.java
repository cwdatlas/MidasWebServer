package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.apiModels.BacktradeOptimize;
import com.midaswebserver.midasweb.apiModels.BacktradeTest;
import com.midaswebserver.midasweb.apiModels.BacktradeReturn;

import java.util.Map;

/**
 * @Author Aidan Scott
 * BackTesterService manages api requests and logic for backtesting activities
 * Webull will be queried in the future for realtime stock data
 */
public interface BackTesterService{

    BacktradeReturn backtrade(BacktradeTest backtest);
    BacktradeReturn optimize(BacktradeOptimize optTest);
}
