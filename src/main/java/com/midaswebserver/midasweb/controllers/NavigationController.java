package com.midaswebserver.midasweb.controllers;

import com.midaswebserver.midasweb.forms.StockDataRequestForm;
import com.midaswebserver.midasweb.models.User.Setting;
import com.midaswebserver.midasweb.models.User.User;
import com.midaswebserver.midasweb.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
        String username = "New User";
        if(!session.isNew()){
            User user = userService.getUserById(Long.parseLong(session.getAttribute("UserId").toString()));
            log.debug("index: User '{}' has accessed Index page", session.getAttribute("UserId").toString());
            username = user.getUsername();
        }
        model.addAttribute("username", username);

        return "index";
    }

    /**
     * @Author Aidan Scott
     * @since 0.0.1
     * Routs the user to their home. Uses sessions to provide a custom experience
     * Adds the {@link User} and {@link Setting} to the model
     * @param session {@link HttpSession}
     * @param model
     * @return the "home" template
     */
    @Transactional
    @GetMapping("/user/home")
    public String home(HttpSession session,  Model model) {
        User user = userService.getUserById(Long.parseLong(session.getAttribute("UserId").toString()));
        Setting[] settings = user.getSetting().toArray(new Setting[user.getSetting().size()]);
        model.addAttribute("user", user);
        model.addAttribute("userSettings", settings);
        log.debug("home: User '{}', data added to form ", session.getAttribute("UserId").toString());
        model.addAttribute("stockDataRequestForm", new StockDataRequestForm());
        return "home";
    }
}
