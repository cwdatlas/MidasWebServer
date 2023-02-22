package com.midaswebserver.midasweb.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
@SpringBootTest
public class TickerSerivceImpTest {
    @Autowired
    private TickerService service;
    @Test//This should return true if the data is not null
    private void getTickerInfoNotNullTest(){
        String symbol = "APPL";
        assertFalse(service.getTickerInfo(symbol).getTicker().equals(symbol));
    }
}
