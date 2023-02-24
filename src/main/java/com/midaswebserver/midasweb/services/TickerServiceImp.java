package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.forms.Ticker;
import jakarta.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Service
public class TickerServiceImp implements TickerService{
    private static final Logger log = LoggerFactory.getLogger(TickerServiceImp.class);
    private final String apiKey = "XIHOPJON41H6MNHT"; //I think this should be secret and have some logic behind it so it isnt found

    @Override
    public Ticker getTickerInfo(String symbol) {
        Ticker ticker = getTickerData("function=TIME_SERIES_INTRADAY&symbol="+symbol+"&interval=5min&apikey=" + apiKey);
        log.debug("ticker {} has been queried", ticker.getTicker());
        return ticker;
    }

    private Ticker getTickerData(String function){
        RestTemplate template = new RestTemplate();
        Object[] tickForm = template.getForObject("https://www.alphavantage.co/query?"+ function, Object[].class);
        if (!validateData(tickForm)){
            log.info("validation for ticker failed");//need more information to be included within the log
            return null;
        }
        Ticker ticker = new Ticker();
//        ticker.setTicker(tickForm.getTicker());
        return ticker;
    }
    private boolean validateData(@Valid @RequestBody Object[] tickerform){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object[]>> violations = validator.validate(tickerform);
        if (!violations.isEmpty()) {
            return false;
            //throw new ConstraintViolationException(violations);
        }
        return true;
    }
}
