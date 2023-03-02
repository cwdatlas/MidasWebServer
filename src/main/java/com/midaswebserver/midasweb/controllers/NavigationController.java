package com.midaswebserver.midasweb.controllers;

import com.midaswebserver.midasweb.forms.StockDataRequestForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import com.midaswebserver.midasweb.forms.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * NavigationController manages the endpoints for general locations
 * this includes the index page and the user/home page
 */
@Controller
public class NavigationController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user/home")
    public String home(@CookieValue(value = "username", defaultValue = "defaultUsername") String username,  Model model) {
        model.addAttribute("username", username);
        model.addAttribute("stockDataRequestForm", new StockDataRequestForm());
        return "home";
    }
}
