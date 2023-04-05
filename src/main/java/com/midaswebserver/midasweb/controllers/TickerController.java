package com.midaswebserver.midasweb.controllers;

import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.midaswebserver.midasweb.apiModels.Ticker;
import com.midaswebserver.midasweb.forms.StockDataRequestForm;
import com.midaswebserver.midasweb.models.User.User;
import com.midaswebserver.midasweb.services.TickerService;
import com.midaswebserver.midasweb.services.TickerServiceImp;
import com.midaswebserver.midasweb.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author Aidan Scott
 * @since 0.0.1
 * @version 0.0.1
 * TickerController has endpoints that relate to requesting stock data using forms like {@link StockDataRequestForm}
 */
@Controller
public class TickerController {
    private static final Logger log = LoggerFactory.getLogger(TickerController.class);
    private final TickerService tickerService = new TickerServiceImp();
    @Autowired
    private UserService userService;

    /**
     * @Author Aidan Scott
     * @sinse 0.0.1
     * GetTickerData takes a StockDataRequestForm and returns the equivalent stock data
     * will return a null value if anything went wrong.
     * TODO errors should be returned rather than a null value, the client should understand what went wrong
     * @param stockDataRequestForm {@link StockDataRequestForm}
     * @param result {@link BindingResult}
     * @param session {@link HttpSession}
     * @return previous url the user had, after it adds ticker data to their session
     */
    @Transactional
    @GetMapping("/ticker/data")
    public String getTickerData(@Valid @ModelAttribute StockDataRequestForm stockDataRequestForm, BindingResult result, HttpSession session, Model model, HttpServletRequest request){
        //session/user validation if user is logged in
        Object userId = session.getAttribute("UserId");
        if (userId == null)
            return "redirect:/login";
        if (request.getHeader("referer") == null ){
            log.debug("getTickerData:'{}', Searched for a ticker but didn't have a redirect address", session.getAttribute("UserId"));
            return "redirect:/index";
        }
        String referer = request.getHeader("referer");

        //exposing and setting standard model attributes
        User user = userService.getUserById((Long)(userId));
        model.addAttribute("user", user);

        if(result.hasErrors()){
            log.debug("getTickerData:'{}', form had errors '{}'", session.getAttribute("UserId"), result.getAllErrors());
            return "redirect:" + referer;
        }
        Ticker ticker = tickerService.getTimeSeriesInfo(stockDataRequestForm.getTicker(), stockDataRequestForm.getInterval(), OutputSize.COMPACT);
        //adds called ticker to tickers that have been called before
        if(ticker!=null && session.getAttribute("UserId")!=null) {
            String symbol = ticker.getMetaData().getSymbol();
            user.addTicker(symbol, user);
            log.debug("getTickerData:'{}', Searched Ticker: '{}'", session.getAttribute("UserId"), symbol);
            userService.update(user);
            session.setAttribute("ticker", ticker);
        }
        return "redirect:" + referer;
    }
}
