package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.apiModels.BacktradeOptimize;
import com.midaswebserver.midasweb.apiModels.BacktradeTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

;

/**
 * @Author Aidan Scott
 * BackTesterService manages api requests and logic for backtesting activities
 * Webull will be queried in the future for realtime stock data
 */
@Service
public class BackTesterServiceImp implements BackTesterService {
    private static final Logger log = LoggerFactory.getLogger(com.midaswebserver.midasweb.services.BackTesterServiceImp.class);

    /**
     * Optimize takes variables and checks how profitable they would be while varying some variables each test. It will return
     * with the most profitable changing variables. If you plug the output into the Backtrade function, you will get the same
     * balance as you got from this function.
     * @param opimization_params
     * @return [optSMA, optEMA, optVolume, endBalance]
     */

    //TODO move data to a custom object for easy data movement (IMPORTANT)

    /**
     * @param params
     * @return
     */
    @Override
    public BacktradeTest backtrade(BacktradeTest params) {
        if (params != null) {
            String url =
                    "http://localhost:5000/backtrade?" +
                            " endDate= " + params.getEndDate() +
                            " startDate= " + params.getStartDate() +
                            " &sma= " + params.getSma() +
                            " &ema= " + params.getEma() +
                            " &stockticker= " + params.getStockTicker().toString() +
                            " &stake= " + params.getStake() +
                            " &algorithm= " + params.getAlgorithm().toString() +
                            " &commission= " + params.getCommission();

            RestTemplate restTemplate = new RestTemplate();
            BacktradeTest result = restTemplate.getForObject(url, BacktradeTest.class);
            log.info("BacktradeTest: returned object '{}'", result);
            params.endValue = result.getEndValue();
            return params;
        }
        return params;
    }

    /**
     * @param params
     * @return
     */

    public BacktradeOptimize optimize(BacktradeOptimize params) {
        if (params != null) {
            String url =
                    "http://localhost:5000/optimize?" +
                            "endDate= " + params.getEndDate() +
                            "startDate= " + params.getStartDate() +
                            "&startSma= " + params.getStartSma() +
                            "&endSma= " + params.getEndSma() +
                            "&startEma= " + params.getStartEma() +
                            "&endEma= " + params.getEndEma() +
                            "&stockticker= " + params.getStockTicker().toString() +
                            "&stake= " + params.getStake() +
                            "&algorithm= " + params.getAlgorithm().toString() +
                            "&commission= " + params.getCommission();

            RestTemplate restTemplate = new RestTemplate();
            BacktradeOptimize result = restTemplate.getForObject(url, BacktradeOptimize.class);
            log.info("BacktradeOptimize: returned object '{}'", result);
            return result;
        }
        return params;
    }

}