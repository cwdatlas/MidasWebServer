package com.midaswebserver.midasweb.services;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.midaswebserver.midasweb.apiModels.MetaData;
import com.midaswebserver.midasweb.apiModels.Ticker;
import jakarta.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
    //Will return null if data is invalid
    @Override
    public Ticker getTimeSeriesInfo(String symbol, Interval interval, OutputSize outputSize) {
        TimeSeriesResponse timeSeries = AlphaVantage.api()
                .timeSeries()
                .intraday()
                .forSymbol(symbol)
                .interval(interval)
                .outputSize(outputSize)
                .fetchSync();
        Ticker ticker = convertToInternalObject(timeSeries);
        if(!(timeSeries.getErrorMessage() == null)){
            log.info("Stock Query Error '{}'", timeSeries.getErrorMessage());
            ticker = null;
        }
        if(!validateTicker(ticker)){
            log.debug("Stock Query Data Invalad");
            ticker = null;
        }
        return ticker;
    }


    private boolean validateTicker(Ticker ticker) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Ticker>> violations = validator.validate(ticker);
        if (!violations.isEmpty()) {
            log.debug("Violation in ticker");
            return false;
            //throw new ConstraintViolationException(violations);
        }
        return true;
    }
    private Ticker convertToInternalObject(TimeSeriesResponse timeSeries){
        Ticker ticker = new Ticker();
        MetaData metaData = new MetaData();
        ticker.setTimeSeries(timeSeries.getStockUnits());
        com.crazzyghost.alphavantage.timeseries.response.MetaData responseMetaData = timeSeries.getMetaData();
        metaData.setData(responseMetaData.getInformation(), responseMetaData.getSymbol(),
                responseMetaData.getLastRefreshed(), responseMetaData.getTimeZone(),
                responseMetaData.getInterval(), responseMetaData.getOutputSize());
        ticker.setMetaData(metaData);
        return ticker;
    }
}
