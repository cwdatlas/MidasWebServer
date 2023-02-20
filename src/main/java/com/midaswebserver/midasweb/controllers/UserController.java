package com.midaswebserver.midasweb.controllers;

import com.midaswebserver.midasweb.forms.UserForm;
import com.midaswebserver.midasweb.services.UserService;
import com.midaswebserver.midasweb.models.User;
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
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    @GetMapping("/register") //map to register
    public String registerGet(Model model) {//still don't know what model is in this case
        model.addAttribute("registerForm", new UserForm());
        return "register";//adds the form to the model and returns it
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute UserForm regForm, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            log.debug("{} Errors in form", regForm.getUsername());
            return "register";
        }
        if (!userService.validateUniqueUsername(regForm.getUsername())){
            result.addError(new ObjectError("globalError", "User already Registered"));
            log.info("{} already registered", regForm.getUsername());
            return "register";
        }
        //Checks if passwords are the same, if not, then user will be returned to register
        if (!regForm.getPassword().equals(regForm.getConfirmPass())){
            result.addError(new ObjectError("globalError", "Passwords do not match"));
            log.info("{} passwords don't match", regForm.getUsername());//this could result in inaccurate logs
            return "register";
        }
        attrs.addAttribute("username", regForm.getUsername()); //im not sure why this is used
        //mapping results from form to user
        User user = new User();
        user.setUsername(regForm.getUsername());
        user.setRawPassword(regForm.getPassword());
        user.setEmail(regForm.getEmail());
        user.setPhoneNumber(regForm.getPhoneNumber());
        userService.add(user);

        log.info("{} been registered", regForm.getUsername());//adding a time to this could be useful, or a more global logging system
        return "redirect:registerSuccess";
    }

    @GetMapping("/registerSuccess")
    public String loginSuccess(String username, Model model) {
        model.addAttribute("username", username);
        log.info("{} logged in", username);
        return "registerSuccess";
    }
}
