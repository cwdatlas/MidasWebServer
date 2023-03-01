package com.midaswebserver.midasweb.services;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.midaswebserver.midasweb.apiModels.Ticker;
import jakarta.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Set;

@Service
public class TickerServiceImp implements TickerService{
    private static final Logger log = LoggerFactory.getLogger(TickerServiceImp.class);
    private static final String apiKey = "XIHOPJON41H6MNHT"; //I think this should be secret and have some logic behind it so it isnt found

    public TickerServiceImp(){
        Config cfg = Config.builder()
                .key(apiKey)
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
    }
    @Override
    public TimeSeriesResponse getTimeSeriesInfo(String symbol, Interval interval, OutputSize outputSize) {
        TimeSeriesResponse response = AlphaVantage.api()
                .timeSeries()
                .intraday()
                .forSymbol(symbol)
                .interval(interval)
                .outputSize(outputSize)
                .fetchSync();
        return response;
    }


//    private boolean validateData(@Valid @RequestBody Object[] tickerform) {
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<ConstraintViolation<Object[]>> violations = validator.validate(tickerform);
//        if (!violations.isEmpty()) {
//            return false;
//            //throw new ConstraintViolationException(violations);
//        }
//        return true;
//    }
}
