package com.midaswebserver.midasweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/linktest")
    public String linkTest() {
        return "linktest";
    }

    @PostMapping("/postTest")
    public String postTest(){
        return null;
    }
}
