package com.midaswebserver.midasweb.controllers;

import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.midaswebserver.midasweb.apiModels.Ticker;
import com.midaswebserver.midasweb.forms.StockDataRequestForm;
import com.midaswebserver.midasweb.models.User.Symbol;
import com.midaswebserver.midasweb.models.User.User;
import com.midaswebserver.midasweb.services.TickerService;
import com.midaswebserver.midasweb.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

/**
 * @Author Aidan Scott
 * @since 0.0.1
 * @version 0.0.1
 * NavigationController manages the endpoints for general locations
 * This includes the index page and the user/home page
 * In the future general endpoints can be split up into more specific areas (admin, management, and so on)
 * Uses {@link UserService} heavily to get userdata used in logs and sessions
 */
@Controller
public class NavigationController {
    private static final Logger log = LoggerFactory.getLogger(NavigationController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private TickerService tickerService;

    /**
     * @Author Aidan Scott
     * @since 0.0.1
     * Routs to index page
     * @param model
     * @param session {@link HttpSession}
     * @return "index" template
     */
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        User baseUser;
        if(!session.isNew() && session.getAttribute("UserId") != null){
            baseUser = userService.getUserById(Long.parseLong(session.getAttribute("UserId").toString()));
            log.debug("index: User '{}' has accessed Index page", session.getAttribute("UserId").toString());
        }
        else{
            baseUser = new User();
            baseUser.setUsername("New User");
        }
        model.addAttribute("user", baseUser);
        return "index";
    }

    /**
     * @Author Aidan Scott
     * @since 0.0.1
     * Routs the user to their home. Uses sessions to provide a custom experience
     * Adds the {@link User} and {@link Symbol} to the model
     * @param session {@link HttpSession}
     * @param model
     * @return the "home" template
     */
    @Transactional
    @GetMapping("/user/home")
    public String home(HttpSession session,  Model model) {
        //session/user validation if user is logged in
        Object userId = session.getAttribute("UserId");
        if (userId == null)
            return "redirect:/login";

        //exposing and setting standard model attributes
        User user = userService.getUserById((Long)(userId));
        model.addAttribute("user", user);
        //other attributesa
        Set<Symbol> symbols = user.getSymbol();
        model.addAttribute("userSettings", symbols);
        if (session.getAttribute("ticker") != null) {
            model.addAttribute("ticker", session.getAttribute("ticker"));
            log.debug("home: session attribute ticker equals '{}'", session.getAttribute("ticker"));
        }
        else
            log.debug("home: session attribute ticker equals null");
        log.debug("home: User '{}', data added to form ", session.getAttribute("UserId"));
        model.addAttribute("stockDataRequestForm", new StockDataRequestForm());
        return "home";
    }

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
    @PostMapping ("/user/home")
    public String getTickerData(@Valid @ModelAttribute StockDataRequestForm stockDataRequestForm, BindingResult result, HttpSession session, Model model, HttpServletRequest request){
        //session user validation if user is logged in
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
        if(ticker!=null) {
            String symbol = ticker.getMetaData().getSymbol();
            log.debug("getTickerData:'{}', Searched Ticker: '{}'", session.getAttribute("UserId"), symbol);
            userService.update(user);
        }
        return "redirect:" + referer;
    }
}
