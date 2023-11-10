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

    /**
     * backtrade will consume a BacktradeTest object and check to see the ending value of a trade with those parameters
     * errors will be included in the returned object
     * @return BacktradeReturn (if there are any errors this object will persist them
     */
    BacktradeReturn backtrade(BacktradeTest backtest);

    /**
     * optimize will consume a BacktradeOptimize object and check to see the ending value of a trade with the those parameters
     * The best preforming trade parameters will be returned.
     * errors will be included in the returned object
     * @return BacktradeReturn
     */
    BacktradeReturn optimize(BacktradeOptimize optTest);
}
