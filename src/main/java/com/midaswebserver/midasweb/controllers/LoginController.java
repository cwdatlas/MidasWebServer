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
        log.info("A user has attempted to sign in"); //how can I make this better? add to a counter?
        return "/login";
    }
    @PostMapping("/login")
    public String loginPost(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, RedirectAttributes attrs){
        if (result.hasErrors()){
            log.debug("{} has submitted a form which has errors", loginForm.getUsername());
            return "login";
        }
        if(!loginService.validateUser(loginForm)){
            result.addError(new ObjectError("globalError", "Username and password do not match known users"));
            log.info("{} was used for a login attempt that failed validation", loginForm.getUsername());
            return "/login";
        }
        attrs.addAttribute("username", loginForm.getUsername());
        log.info("{} has successfully logged in", loginForm.getUsername());//adding a time to this could be useful, or a more global logging system
        return "redirect:/loginSuccess";
    }
    @GetMapping("/loginSuccess")
    public String loginSuccess(String username, Model model){
        model.addAttribute("username", username);
        log.info("/loginSuccess has been connected to");
        return "loginSuccess";
    }

    @GetMapping("/loginFailure")
    public String loginFailure(){return "loginFailure";}

}