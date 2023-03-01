package com.midaswebserver.midasweb.controllers;

import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.midaswebserver.midasweb.apiModels.Ticker;
import com.midaswebserver.midasweb.services.TickerService;
import com.midaswebserver.midasweb.services.TickerServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TickerController {

    private TickerService tickerService = new TickerServiceImp();

    @GetMapping("/ticker/data")
    public ResponseEntity getTickerData(){
         Ticker ticker = tickerService.getTimeSeriesInfo("IBM", Interval.SIXTY_MIN, OutputSize.COMPACT);
        return ResponseEntity.ok(ticker);
    }
}
