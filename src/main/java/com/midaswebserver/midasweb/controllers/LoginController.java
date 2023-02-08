package com.midaswebserver.midasweb.controllers;

import com.midaswebserver.midasweb.forms.LoginForm;
import com.midaswebserver.midasweb.services.LoginService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;
    public LoginController(LoginService loginService) {this.loginService = loginService;}

    @GetMapping("/login")
    public String logingGet(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "/login";
    }
    @PostMapping("/login")
    public String loginPost(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, RedirectAttributes attrs){
        if (result.hasErrors()){
            return "login";
        }
        if(!loginService.validateUser(loginForm)){
            result.addError(new ObjectError("globalError", "Username and password do not match known users"));
            return "/login";
        }
        attrs.addAttribute("username", loginForm.getUsername());
        return "redirect:/loginSuccess";
    }
    @GetMapping("/loginSuccess")
    public String loginSuccess(String username, Model model){
        model.addAttribute("username", username);
        return "loginSuccess";
    }

    @GetMapping("/loginFailure")
    public String loginFailure(){return "loginFailure";}

}
