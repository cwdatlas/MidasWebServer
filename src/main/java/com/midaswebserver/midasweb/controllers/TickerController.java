package com.midaswebserver.midasweb.controllers;

import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.midaswebserver.midasweb.apiModels.Ticker;
import com.midaswebserver.midasweb.forms.StockDataRequestForm;
import com.midaswebserver.midasweb.forms.UserForm;
import com.midaswebserver.midasweb.services.TickerService;
import com.midaswebserver.midasweb.services.TickerServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TickerController {

    private TickerService tickerService = new TickerServiceImp();

    @GetMapping("/ticker/data")
    public ResponseEntity getTickerData(@Valid @ModelAttribute StockDataRequestForm stockDataRequestForm, BindingResult result, RedirectAttributes attrs){

        Ticker ticker = tickerService.getTimeSeriesInfo("IBM", Interval.SIXTY_MIN, OutputSize.COMPACT);
        return ResponseEntity.ok(ticker);
    }
}
