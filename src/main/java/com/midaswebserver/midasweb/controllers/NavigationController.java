package com.midaswebserver.midasweb.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NavigationController {
    private static final Logger log = LoggerFactory.getLogger(NavigationController.class);
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user/home")
    public String home() { return "home"; }


}
