package com.midaswebserver.midasweb.controllers;

import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.midaswebserver.midasweb.apiModels.BacktradeOptimize;
import com.midaswebserver.midasweb.apiModels.BacktradeReturn;
import com.midaswebserver.midasweb.apiModels.BacktradeTest;
import com.midaswebserver.midasweb.apiModels.Ticker;
import com.midaswebserver.midasweb.forms.BackTraderForm;
import com.midaswebserver.midasweb.forms.BackTraderOptimizeForm;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
  * UserHomeController manages the endpoints for general locations
 * This includes the index page and the user/home page
 * In the future general endpoints can be split up into more specific areas (admin, management, and so on)
 * Uses {@link UserService} heavily to get userdata used in logs and sessions
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
     * Adds the {@link User} and {@link Symbol} to the model
     *
     * @param session {@link HttpSession}
     * @param model
     * @return the "home" template
     */
    @Transactional
    @GetMapping("/user/home") //TODO get home page look and feel (log out) shouldnt be on page if logged in and vise versa
    public String home(HttpSession session, Model model) {
        //session/user validation if user is logged in
        Object userId = session.getAttribute("UserId");
        if (userId == null)
            return "redirect:/login";

        //exposing and setting standard model attributes
        User user = userService.getUserById((Long) (userId));
        model.addAttribute("user", user);
        //other attributes
        Symbol[] symbols = user.getSymbol().toArray(new Symbol[user.getSymbol().size()]);
        model.addAttribute("userSettings", symbols);
        log.debug("home: User '{}', symbols: '{}' added to form", session.getAttribute("UserId"), symbols);
        model.addAttribute("stockDataRequestForm", new StockDataRequestForm());
        model.addAttribute("backTraderForm", new BackTraderForm());
        model.addAttribute("backTraderOptimizeForm", new BackTraderOptimizeForm());

        //Checking if optimizeBacktrade or backtrade is in the session
        BacktradeTest backtrade = (BacktradeTest) session.getAttribute("backtrade");
        if (backtrade != null) {
            //TODO make sure that only results are logged
            //notnull is checked in the if statement before this line, so it shouldn't break
            log.debug("home: User '{}', Backtrade params collected from session", session.getAttribute("UserId"));
            BacktradeReturn results = backtesterService.backtrade(backtrade);
            log.debug("home: User '{}', returned backtrade params: '{}'", session.getAttribute("UserId"), backtrade);
            //model.addAttribute("backtrader", backtrade);
            model.addAttribute("tradeReturn", results);
            session.removeAttribute("backtrade");
            return "home";
        }
        //Optimize Section
        BacktradeOptimize backtradeOptimize = (BacktradeOptimize) session.getAttribute("optimizeBacktrade");
        if ( backtradeOptimize !=null) {
            //TODO make sure that only results are logged
            log.debug("home: User '{}', optimizeBacktrade params collected from session", session.getAttribute("UserId"));
            BacktradeReturn results = backtesterService.optimize(backtradeOptimize);
            log.debug("home: User '{}', returned optimizeBacktrade params: '{}'", session.getAttribute("UserId"), backtradeOptimize);
            //model.addAttribute("backtraderOpt", backtradeOptimize);
            model.addAttribute("optimizeReturn", results);
            session.removeAttribute("optimizeBacktrade");
        }
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
            //TODO fix this debug log so it doesnt error out
            //log.debug("getTickerData:'{}', Searched Ticker: '{}', added ticker, '{}'", session.getAttribute("UserId"), symbol, addedTicker);
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
     * GetTickerData takes a StockDataRequestForm and displays the data on the displayData page
     * will return user to /user/home if any data validates bad
     *
     * @param backTraderForm {@link StockDataRequestForm}
     * @param result               {@link BindingResult}
     * @param session              {@link HttpSession}
     * @return previous url the user had, after it adds ticker data to their session
     */
    @PostMapping("/user/home/backtrade")
    public String getBacktrade(@Valid @ModelAttribute("backTraderForm") BackTraderForm backTraderForm, BindingResult result, HttpSession session, Model model, HttpServletRequest request) {
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
            log.debug("getBacktrade:'{}', form had errors '{}'", session.getAttribute("UserId"), result.getAllErrors());
            return "home";
        }
        //TODO make sure that all data will be given to the form. Add fields in thymleaf
        BacktradeTest backtrade = new BacktradeTest();
        backtrade.setStartDate(backTraderForm.getStartDate());
        backtrade.setEndDate(backTraderForm.getEndDate());
        backtrade.setSma(backTraderForm.getSmaLength());
        backtrade.setEma(backTraderForm.getEmaLength());
        backtrade.setStockTicker(backTraderForm.getStockTicker());
        backtrade.setStake(backTraderForm.getStake());
        backtrade.setAlgorithm(backTraderForm.getAlgorithm());
        backtrade.setCommission(backTraderForm.getCommission());
        //adding backtrade information to the form, so it can be gathered and used in the get method
        session.setAttribute("backtrade", backtrade);
        log.debug("getBacktrade:'{}', params gotten from  form '{}'", session.getAttribute("UserId"), backtrade);

        return "redirect:/user/home";
    }

    /**
     * GetTickerData takes a StockDataRequestForm and displays the data on the displayData page
     * will return user to /user/home if any data validates bad
     *
     * @param backTraderOptimizeForm {@link StockDataRequestForm}
     * @param result               {@link BindingResult}
     * @param session              {@link HttpSession}
     * @return previous url the user had, after it adds ticker data to their session
     */
    @PostMapping("/user/home/backtradeOpt")
    public String getOptBacktrade(@Valid @ModelAttribute("backTraderOptimizeForm") BackTraderOptimizeForm backTraderOptimizeForm, BindingResult result, HttpSession session, Model model, HttpServletRequest request) {
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
            log.debug("getOptBacktrade:'{}', form had errors '{}'", session.getAttribute("UserId"), result.getAllErrors());
            return "home";
        }
        //TODO make sure that all data will be given to the form. Add fields in thymleaf
        //copying data so variables used internally do not share variables used externally
        BacktradeOptimize backtradeOptimize = new BacktradeOptimize();
        backtradeOptimize.setStartDate(backTraderOptimizeForm.getStartDate());
        backtradeOptimize.setEndDate(backTraderOptimizeForm.getEndDate());
        backtradeOptimize.setStartSma(backTraderOptimizeForm.getStartSma());
        backtradeOptimize.setEndSma(backTraderOptimizeForm.getEndSma());
        backtradeOptimize.setStartEma(backTraderOptimizeForm.getStartEma());
        backtradeOptimize.setEndEma(backTraderOptimizeForm.getEndEma());
        backtradeOptimize.setStockTicker(backTraderOptimizeForm.getStockTicker());
        backtradeOptimize.setStake(backTraderOptimizeForm.getStake());
        backtradeOptimize.setAlgorithm(backTraderOptimizeForm.getAlgorithm());
        backtradeOptimize.setCommission(backTraderOptimizeForm.getCommission());
        //adding backtrade information to the form, so it can be gathered and used in the get method
        session.setAttribute("optimizeBacktrade", backtradeOptimize);
        log.debug("getOptBacktrade:'{}', params gotten from  form '{}'", session.getAttribute("UserId"), backtradeOptimize);

        return "redirect:/user/home";
    }
}
