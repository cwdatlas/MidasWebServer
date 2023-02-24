package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.forms.Ticker;
import jakarta.validation.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static org.springframework.test.util.AssertionErrors.*;
@SpringBootTest
public class TickerSerivceImpTest {
    @Autowired
    private TickerService service;

    @Test//This should return true if the data is not null
    public void getTickerInfoNotNullTest(){
        String testSymbol = "IBM";
        String symbol = service.getTickerInfo(testSymbol).getTicker();
        assertFalse("The Ticker with symbol",!symbol.equals("APPL"));
    }

    @Test//this is a proof of concept test using only local logic
    public void getRequestOnURLTest(){
        boolean response = false;
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=XIHOPJON41H6MNHT";
        RestTemplate template = new RestTemplate();
        Ticker form = template.getForObject(url, Ticker.class);
        if(validate(form) == true)
            response = true;
        System.out.println("Ticker  " + form.toString());
        assertTrue("Ticker response was null", response);
    }

    private boolean validate(Ticker form){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Ticker>> violations = validator.validate(form);
        if (!violations.isEmpty()) {
            return false;
            //throw new ConstraintViolationException(violations);
        }
        return true;
    }
}
