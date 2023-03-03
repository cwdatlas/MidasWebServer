package com.midaswebserver.midasweb.controllers;

import com.midaswebserver.midasweb.forms.StockDataRequestForm;
import com.midaswebserver.midasweb.models.User;
import com.midaswebserver.midasweb.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger log = LoggerFactory.getLogger(NavigationController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        String username = "New User";
        if(!session.isNew()){
            log.debug("Logged User has id of: '{}'", session.getAttribute("UserId").toString());
            User user = userService.getUserByID(Long.parseLong(session.getAttribute("UserId").toString()));
            log.debug("'{}' has accessed Index page", user.getUsername());
            username = user.getUsername();
        }
        model.addAttribute("username", username);

        return "index";
    }

    @GetMapping("/user/home")
    public String home(HttpSession session,  Model model) {
        User user = userService.getUserByID(Long.parseLong(session.getAttribute("UserId").toString()));
        model.addAttribute("username", user.getUsername());
        model.addAttribute("stockDataRequestForm", new StockDataRequestForm());
        return "home";
    }
}
