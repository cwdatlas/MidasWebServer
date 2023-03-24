package com.midaswebserver.midasweb.controllers;

import com.midaswebserver.midasweb.forms.UserForm;
import com.midaswebserver.midasweb.services.HashService;
import com.midaswebserver.midasweb.services.UserService;
import com.midaswebserver.midasweb.models.User.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @Author Aidan Scott
 * @since 0.0.1
 * @version 0.0.1
 * UserController is the CRUD interface for users
 * this controller is built to serve users adding themselves and admin management
 * uses {@link HashService} and {@link UserService} heavily to interact with user repository and for business logic
 * TODO develope admin management
 */
@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private final UserService userService;
    @Autowired
    private HashService hashService;

    public UserController(UserService userService) {this.userService = userService;}

    /**
     * @Author Aidan Scott
     * @since 0.0.1
     * @version 0.0.1
     * Attaching register form to the model
     * @param model
     * @return register template
     */
    @GetMapping("/register") //map to register
    public String registerGet(Model model) {//still don't know what model is in this case
        model.addAttribute("registerForm", new UserForm());
        return "register";//adds the form to the model and returns it
    }

    /**
     * @Author Aidan Scott
     * @since 0.0.1
     * registerPost takes userForm, validates, checks if the user already in the database,
     * and checks if the passwords match
     * @param userForm {@link UserForm}
     * @param result {@link BindingResult}
     * @param attrs
     * @return redirects user to "registerSuccess" template
     */
    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute UserForm userForm, BindingResult result, RedirectAttributes attrs, HttpServletRequest request) {
        if (result.hasErrors()) {
            log.debug("registerPost: Form from '{}' had errors", getClientIp(request));
            return "register";
        }
        if (!userService.validateUniqueUsername(userForm.getUsername())){
            result.addError(new ObjectError("globalError", "User already Registered"));
            log.debug("registerPost: Attempted validation from '{}' with username '{}'", getClientIp(request), userForm.getUsername());
            return "register";
        }
        //Checks if passwords are the same, if not, then user will be returned to register
        if (!userForm.getPassword().equals(userForm.getConfirmPass())){
            result.addError(new ObjectError("globalError", "Passwords do not match"));
            log.debug("loginPost: Passwords from '{}' do not match", getClientIp(request));
            return "register";
        }
        attrs.addAttribute("username", userForm.getUsername()); //im not sure why this is used
        //mapping results from form to user
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setHashedPassword(hashService.getHash(userForm.getPassword()));
        user.setEmail(userForm.getEmail());
        user.setPhoneNumber(userForm.getPhoneNumber());
        userService.add(user);

        log.info("loginPost:'{}' been registered", userService.getUserByName(userForm.getUsername()));//adding a time to this could be useful, or a more global logging system
        return "redirect:registerSuccess";
    }

    /**
     * logs that the user has been authenticated
     * @param username
     * @param model
     * @return "registerSuccess" template
     */
    @GetMapping("/registerSuccess")
    public String loginSuccess(String username, Model model) {
        model.addAttribute("username", username);
        return "registerSuccess";
    }
    /**
     * @Author Aidan Scott
     * @since 0.0.1
     * takes request and finds the user's Ip. Used in place of user id when logging
     * TODO centralize getClientIp into one method {See LoginController} to see other method
     * @param request {link HttpServletRequest} takes the servlet request received from a post method
     * @return remote IP address
     */
    private static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
}
