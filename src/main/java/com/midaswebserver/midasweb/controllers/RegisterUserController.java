package com.midaswebserver.midasweb.controllers;

import com.midaswebserver.midasweb.forms.LoginForm;
import com.midaswebserver.midasweb.forms.RegisterUserForm;
import com.midaswebserver.midasweb.services.LoginService;
import com.midaswebserver.midasweb.services.RegisterUserService;
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
public class RegisterUserController {
    private static final Logger log = LoggerFactory.getLogger(RegisterUserController.class);
    private final RegisterUserService regUserService;

    public RegisterUserController(RegisterUserService registerUserService) {this.regUserService = registerUserService;}

    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("registerForm", new RegisterUserForm());
        return "/register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute RegisterUserForm regForm, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            log.debug("{} has submitted a form which has errors", regForm.getUsername());
            return "/register";
        }
        if (!regUserService.validateUser(regForm)) {
            result.addError(new ObjectError("globalError", "Username and password do not match known users"));
            log.info("{} failed validation", regForm.getUsername());
            return "/register";
        }
        attrs.addAttribute("username", regForm.getUsername());
        log.info("{} has successfully logged in", regForm.getUsername());//adding a time to this could be useful, or a more global logging system
        return "redirect:/registerSuccess";
    }

    @GetMapping("/registerSuccess")
    public String loginSuccess(String username, Model model) {
        model.addAttribute("username", username);
        log.info("{} logged in", username);
        return "/login";
    }
}
