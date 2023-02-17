package com.midaswebserver.midasweb.controllers;

import com.midaswebserver.midasweb.forms.RegisterUserForm;
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

    @GetMapping("/register") //map to register
    public String registerGet(Model model) {//still don't know what model is in this case
        model.addAttribute("registerForm", new RegisterUserForm());
        return "/register";//adds the form to the model and returns it
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute RegisterUserForm regForm, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            log.debug("Errors in form", regForm.getUsername());
            return "register";
        }
        if (!regUserService.validateUniqueUsername(regForm.getUsername())){
            result.addError(new ObjectError("globalError", "User already Registered"));
            log.info("{} already registered", regForm.getUsername());
            return "register";
        }
        if (!regUserService.validatePasswords(regForm.getPassword(), regForm.getConfirmPass())){
            result.addError(new ObjectError("globalError", "Passwords do not match"));
            log.info("{} passwords don't match", regForm.getUsername());//this could result in inaccurate logs
            return "register";
        }
        attrs.addAttribute("username", regForm.getUsername());
        log.info("{} been registered", regForm.getUsername());//adding a time to this could be useful, or a more global logging system
        return "redirect:registerSuccess";
    }

    @GetMapping("/registerSuccess")
    public String loginSuccess(String username, Model model) {
        model.addAttribute("username", username);
        log.info("{} logged in", username);
        return "login";
    }
}
