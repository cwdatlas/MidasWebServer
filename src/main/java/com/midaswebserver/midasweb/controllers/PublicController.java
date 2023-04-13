package com.midaswebserver.midasweb.controllers;

import com.midaswebserver.midasweb.models.User.User;
import com.midaswebserver.midasweb.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PublicController {
    private static final Logger log = LoggerFactory.getLogger(PublicController.class);
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
}
