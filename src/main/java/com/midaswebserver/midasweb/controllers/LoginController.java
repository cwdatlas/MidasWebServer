package com.midaswebserver.midasweb.controllers;

import com.midaswebserver.midasweb.forms.LoginForm;
import com.midaswebserver.midasweb.services.LoginService;
import com.midaswebserver.midasweb.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
 * The LoginController works with all interactions by the client regarding logging in
 * Sending a form to a PostMethod with the client's information is the major use
 */
@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;
    @Autowired
    private UserService userService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * loginGet sends the loginForm with the model to the client
     * @param model
     * @return "login" template
     */
    @GetMapping("/login")
    public String loginGet(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        log.debug("loginGet: Client connected to /login"); //how can I make this better? add to a counter?
        return "login";
    }

    /**
     * loginPost takes the completed form for login, validates, and copies data to a User object
     * @param loginForm
     * @param result
     * @param attrs
     * @return either "loginsuccess" or "login"
     */
    @PostMapping("/login")
    public String loginPost(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, RedirectAttributes attrs, HttpServletRequest request) {
        if (result.hasErrors()) {
            log.debug("LoginPost: Form from '{}' had errors", getClientIp(request));
            return "login";
        }
        if (!loginService.validateUser(loginForm)) {
            result.addError(new ObjectError("globalError", "Username and password do not match known users"));
            log.debug("loginPost: Attempted validation from '{}' with username '{}'", getClientIp(request), loginForm.getUsername());
            return "/login";
        }
        attrs.addAttribute("username", loginForm.getUsername());
        log.debug("loginPost: User '{}' has successfully logged in from '{}'",
                userService.getUserByName(loginForm.getUsername()), getClientIp(request));
        return "redirect:/loginSuccess";
    }

    /**
     * adds or updates persistent cookie
     * TODO rather than adding only username, add object that holds all of whats important
     * TODO much of this needs to be moved to a separate cookie class. We need a centralized location to work with cookies
     * @param session
     * @param model
     * @param username
     * @return the "loginSuccess" template
     */
    @GetMapping("/loginSuccess")
    public String loginSuccess(HttpSession session, Model model, String username) {
        session.setAttribute("UserId", userService.getIdByUsername(username));
        model.addAttribute("username", username);
        log.debug("loginSuccess: User '{}' was given session of ID '{}'", userService.getUserByName(username), session.getId());
        return "loginSuccess";
    }

    /**
     * @return the "loginFailure" template
     * TODO find a solution to this, delete this or replace its functionality as there isnt a loginFailure template
     */

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request) {
        log.debug("logout: User '{}', session ID '{}', location '{}', logged out",
                userService.getUserById((long)session.getAttribute("UserId")), session.getId(), getClientIp(request));
        session.invalidate();
        return "index";
    }

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
