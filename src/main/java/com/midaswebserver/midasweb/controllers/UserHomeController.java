package com.midaswebserver.midasweb.controllers;

import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.midaswebserver.midasweb.apiModels.*;
import com.midaswebserver.midasweb.forms.BacktraderForm;
import com.midaswebserver.midasweb.forms.BacktraderOptimizeForm;
import com.midaswebserver.midasweb.forms.StockDataRequestForm;
import com.midaswebserver.midasweb.models.User.Symbol;
import com.midaswebserver.midasweb.models.User.User;
import com.midaswebserver.midasweb.services.BackTesterService;
import com.midaswebserver.midasweb.services.TickerService;
import com.midaswebserver.midasweb.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * UserHomeController manages the endpoints for general locations
 * This includes the user/home page
 * In the future general endpoints can be split up into more specific areas (admin, management, and so on)
 * Features of this page include stock price searches and backtesting and optimization of
 * stock trading.
 * Backtesting is the act of using an old time series and using it to test metrics, stocks, and algorithms to check
 * their validity.
 * Uses {@link UserService} heavily to get userdata used in logs and sessions
 * check out the microservice that makes backtesting work at
 * <a href="https://github.com/cwdatlas/backtraderMicroservice">midas microservice</a>
 *
 * @Author Aidan Scott
 */
@Controller
public class UserHomeController {
    private static final Logger log = LoggerFactory.getLogger(UserHomeController.class);
    private final UserService userService;
    private final TickerService tickerService;
    private final BackTesterService backtesterService;

    /**
     * Injects services into controller
     *
     * @param userService
     * @param tickerService
     * @param backtesterService
     */
    public UserHomeController(UserService userService, TickerService tickerService, BackTesterService backtesterService) {
        this.userService = userService;
        this.tickerService = tickerService;
        this.backtesterService = backtesterService;
    }

    /**
     * Routs the user to their home. Uses sessions to provide a custom experience
     * Adds the {@link User} and {@link Symbol} and {@link BacktradeData} to the model
     *
     * @param session {@link HttpSession}
     * @param model
     * @return the "home" template
     */
    @Transactional
    @GetMapping("/user/home")
    //TODO get home page look and feel (log out) shouldnt be on page if logged in and vise versa
    public String home(HttpSession session, Model model) {
        //session/user validation if user is logged in
        Object userId = session.getAttribute("UserId");
        if (userId == null)
            return "redirect:/login";

        //exposing and setting standard model attributes
        User user = userService.getUserById((Long) (userId));
        model.addAttribute("user", user);
        //other attributes
        //saved symbols to be displayed like UEC, or APPL
        Symbol[] symbols = user.getSymbol().toArray(new Symbol[user.getSymbol().size()]);
        model.addAttribute("userSettings", symbols);
        log.debug("home: User '{}', symbols: '{}' added to form", session.getAttribute("UserId"), symbols);
        //adding forms
        model.addAttribute("stockDataRequestForm", new StockDataRequestForm());
        model.addAttribute("backTraderForm", new BacktraderForm());
        model.addAttribute("backTraderOptimizeForm", new BacktraderOptimizeForm());

        //adding backtradeData to the model and removing it from the session
        model.addAttribute("tradeReturn", session.getAttribute("backtradeData"));
        session.removeAttribute("backtradeData");

        //adding backtradeOptimizeData to the model and removing it from the session
        model.addAttribute("optimizeReturn", session.getAttribute("optimizeBacktradeData"));
        session.removeAttribute("optimizeBacktradeData");

        return "home";
    }

    /**
     * GetTickerData takes a StockDataRequestForm and displays the data on the displayData page
     * will return user to /user/home if any data validates bad
     *
     * @param stockDataRequestForm {@link StockDataRequestForm}
     * @param result               {@link BindingResult}
     * @param session              {@link HttpSession}
     * @return previous url the user had, after it adds ticker data to their session
     */
    @Transactional
    @PostMapping("/user/home")
    public String getTickerData(@Valid @ModelAttribute("stockDataRequestForm") StockDataRequestForm stockDataRequestForm, BindingResult result, HttpSession session, Model model, HttpServletRequest request) {
        //session user validation if user is logged in
        Object userId = session.getAttribute("UserId");
        if (userId == null)
            return "redirect:/login";

        User user = userService.getUserById((Long) (userId));
        //exposing and setting standard model attributes
        model.addAttribute("user", user);
        Symbol[] symbols = user.getSymbol().toArray(new Symbol[user.getSymbol().size()]);
        model.addAttribute("userSettings", symbols);

        if (result.hasErrors()) {
            log.debug("getTickerData:'{}', form had errors '{}'", session.getAttribute("UserId"), result.getAllErrors());
            return "redirect:/user/home";
        }

        Ticker ticker = tickerService.getTimeSeriesInfo(stockDataRequestForm.getTicker(), stockDataRequestForm.getInterval(), OutputSize.COMPACT);
        //adds called ticker to tickers that have been called before
        if (ticker == null) {
            log.warn("getTickerData:'{}', Searched for ticker, '{}', bad ticker or interval, received bad data", session.getAttribute("UserId"), stockDataRequestForm.getTicker());
            return "home";
        }
        if (ticker.getMetaData() != null || ticker.getMetaData().getSymbol() != null) {
            String symbol = ticker.getMetaData().getSymbol();
            boolean addedTicker = userService.addSymbolToUser(user, symbol);
        } else { // this is if the incoming data isnt valid, this is probably because the wrong ticker was sent to the api
            log.warn("getTickerData:'{}', Searched for ticker, '{}', bad ticker or interval, received bad data", session.getAttribute("UserId"), stockDataRequestForm.getTicker());
            stockDataRequestForm.setTicker("invalid");//I dont know if they will be able to see this
            return "home";
        }

        //convert stock data to data chart.js can use
        List<Map<String, Object>> data = tickerService.tickerToDataPoints(ticker);
        //other attributes
        model.addAttribute("data", data);
        return "dataDisplay";
    }

    /**
     * GetBacktrade receives a {@link BacktraderForm} then validates, and gets results from {@link BackTesterService}
     *
     * @param backTraderForm {@link StockDataRequestForm}
     * @param result         {@link BindingResult}
     * @param session        {@link HttpSession}
     * @return previous url the user had, after it adds ticker data to their session
     */
    @PostMapping("/user/home/backtrade")
    public String getBacktrade(@Valid @ModelAttribute("backTraderForm") BacktraderForm backTraderForm, BindingResult result, HttpSession session, Model model, HttpServletRequest request) {
        //session user validation if user is logged in
        Object userId = session.getAttribute("UserId");
        if (userId == null)
            return "redirect:/login";

        User user = userService.getUserById((Long) (userId));
        //exposing and setting standard model attributes
        model.addAttribute("user", user);
        Symbol[] symbols = user.getSymbol().toArray(new Symbol[user.getSymbol().size()]);
        model.addAttribute("userSettings", symbols);

        //Post form validation
        LocalDate startDate = null;
        try {
            startDate = LocalDate.parse(backTraderForm.getStartDate());
        } catch (DateTimeParseException e) {
            log.debug("getOptBacktrade: StartDate invalid", e);
            result.addError(new FieldError("startDate", "startDate", "Incorrect Format"));
        }
        LocalDate endDate = null;
        try {
            endDate = LocalDate.parse(backTraderForm.getEndDate());
        } catch (DateTimeParseException e) {
            log.debug("getOptBacktrade: EndDate invalid", e);
            result.addError(new FieldError("endDate", "endDate", "Incorrect Format"));
        }
        //copying data so variables used internally do not share variables used externally
        //Only runs if no errors have been previously found, highest chance to return valid date
        if (!result.hasErrors()) {
            BacktradeTest backtrade = new BacktradeTest();
            backtrade.setStartDate(startDate);
            backtrade.setEndDate(endDate);
            backtrade.setSma(backTraderForm.getSma());
            backtrade.setEma(backTraderForm.getEma());
            backtrade.setStockTicker(backTraderForm.getStockTicker());
            backtrade.setStake(backTraderForm.getStake());
            backtrade.setAlgorithm(backTraderForm.getAlgorithm());
            backtrade.setCommission(backTraderForm.getCommission());

            //querying checking if the optimization returns an error, and save return if it doesn't
            BacktradeReturn tradeData = backtesterService.backtrade(backtrade);
            /* Errors are not in java camel case, so a translation must be done to make sure that incoming
             *  invalidators match form values
             *   start_date = startDate
             *   end_date = endDate
             *   stock_ticker = stockTicker
             * */
            if (tradeData.getErrorCode() != null && tradeData.getErrorCode().equals("400")) {
                String validator = switch (tradeData.getInvalidators()) {
                    case "start_date" -> "startDate";
                    case "end_date" -> "end_date";
                    case "stock_ticker" -> "stockTicker";
                    default -> "global";
                };
                log.debug("getBacktrade: '{}'", tradeData.getMessage());
                result.addError(new FieldError(validator, validator, tradeData.getMessage()));
            } else {
                session.setAttribute("backtradeData", tradeData);
                log.debug("getBacktrade:'{}', params gotten from  form '{}'", session.getAttribute("UserId"), backtrade);
            }
        }
        // if there are any errors update form so user can make changes to their request
        if (result.hasErrors()) {
            log.debug("getBacktrade:'{}', form had errors '{}'", session.getAttribute("UserId"), result.getAllErrors());
            return "home";
        }
        return "redirect:/user/home";
    }

    /**
     * GetOptBacktrade receives a {@link BacktraderOptimizeForm} then validates, and gets results from {@link BackTesterService}
     *
     * @param backTraderOptimizeForm {@link StockDataRequestForm}
     * @param result                 {@link BindingResult}
     * @param session                {@link HttpSession}
     * @return previous url the user had, after it adds ticker data to their session
     */
    @PostMapping("/user/home/backtradeOpt")
    public String getOptBacktrade(@Valid @ModelAttribute("backTraderOptimizeForm") BacktraderOptimizeForm backTraderOptimizeForm, BindingResult result, HttpSession session, Model model, HttpServletRequest request) {
        //session user validation if user is logged in
        Object userId = session.getAttribute("UserId");
        if (userId == null)
            return "redirect:/login";

        User user = userService.getUserById((Long) (userId));
        //exposing and setting standard model attributes
        model.addAttribute("user", user);
        Symbol[] symbols = user.getSymbol().toArray(new Symbol[user.getSymbol().size()]);
        model.addAttribute("userSettings", symbols);

        //Extra post form validation
        LocalDate startDate = null;
        try {
            startDate = LocalDate.parse(backTraderOptimizeForm.getStartDate());
        } catch (DateTimeParseException e) {
            log.error("getOptBacktrade: StartDate invalid", e);
            result.addError(new FieldError("startDate", "startDate", "Incorrect Format"));
        }
        LocalDate endDate = null;
        try {
            endDate = LocalDate.parse(backTraderOptimizeForm.getEndDate());
        } catch (DateTimeParseException e) {
            log.error("getOptBacktrade: EndDate invalid", e);
            result.addError(new FieldError("endDate", "endDate", "Incorrect Format"));
        }
        //copying data so variables used internally do not share variables used externally
        //Only runs if no errors have been previously found, highest chance to return valid date
        if (!result.hasErrors()) {
            BacktradeOptimize backtradeOptimize = new BacktradeOptimize();
            backtradeOptimize.setStartDate(startDate);
            backtradeOptimize.setEndDate(endDate);
            backtradeOptimize.setStartSma(backTraderOptimizeForm.getStartSma());
            backtradeOptimize.setEndSma(backTraderOptimizeForm.getEndSma());
            backtradeOptimize.setStartEma(backTraderOptimizeForm.getStartEma());
            backtradeOptimize.setEndEma(backTraderOptimizeForm.getEndEma());
            backtradeOptimize.setStockTicker(backTraderOptimizeForm.getStockTicker());
            backtradeOptimize.setStake(backTraderOptimizeForm.getStake());
            backtradeOptimize.setAlgorithm(backTraderOptimizeForm.getAlgorithm());
            backtradeOptimize.setCommission(backTraderOptimizeForm.getCommission());

            //querying checking if the optimization returns an error, and save return if it doesn't
            BacktradeReturn tradeData = backtesterService.optimize(backtradeOptimize);
            /* Errors are not in java camel case, so a translation must be done to make sure that incoming
             *  invalidators match form values
             *   start_date = startDate
             *   end_date = endDate
             *   stock_ticker = stockTicker
             *   start_sma = startSma
             *   end_sma = endSma
             *   start_ema = startEma
             *   end_ema = endEma
             * */
            if (tradeData.getErrorCode() != null && tradeData.getErrorCode().equals("400")) {
                String validator = switch (tradeData.getInvalidators()) {
                    case "start_date" -> "startDate";
                    case "end_date" -> "endDate";
                    case "stock_ticker" -> "stockTicker";
                    case "start_sma" -> "startSma";
                    case "end_sma" -> "endSma";
                    case "start_ema" -> "startEma";
                    case "end_ema" -> "endEma";
                    default -> "global";
                };
                log.error("getOptBacktrade: '{}', validator: '{}'", tradeData.getMessage(), validator);
                result.addError(new FieldError(validator, validator, tradeData.getMessage()));
            } else {
                session.setAttribute("optimizeBacktradeData", tradeData);
                log.debug("getOptBacktrade:'{}', params gotten from  form '{}'", session.getAttribute("UserId"), backtradeOptimize);
            }
        }
        // if there are any errors update form so user can make changes to their request
        if (result.hasErrors()) {
            log.debug("getOptBacktrade:'{}', form had errors '{}'", session.getAttribute("UserId"), result.getAllErrors());
            return "home";
        }
        return "redirect:/user/home";
    }
}
