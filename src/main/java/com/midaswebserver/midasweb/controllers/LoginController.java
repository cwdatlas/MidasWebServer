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
 * @Author Aidan Scott
 * @sinse 0.0.1
 * @version 0.0.1
 * The LoginController works with all interactions by the client regarding logging in, logging out
 * uses {@link LoginService} and {@link UserService} heavily to interact with user repository and for business logic
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
     * @Author Aidan Scott
     * @sinse 0.0.1
     * loginGet sends the loginForm with the model to the client
     * @param model
     * @return "login" template, which houses all info needed for login
     */
    @GetMapping("/login")
    public String loginGet(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        log.debug("loginGet: Client connected to /login"); //how can I make this better? add to a counter?
        return "login";
    }

    /**
     * @Author Aidan Scott
     * @sinse 0.0.1
     * loginPost takes the completed form for login, validates, and copies data to a User object
     * @param loginForm {@link LoginForm} returned loginform with filled out user data
     * @param result {@link BindingResult} if loginForm data is valid, what errors it contains
     * @param attrs
     * @param request {@link HttpServletRequest}
     * @return either "loginsuccess" or "login" templates
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
     * @Author Aidan Scott
     * @sinse 0.0.1
     * adds or updates persistent cookie
     * TODO rather than adding only username, add object that holds all of whats important
     * TODO much of this needs to be moved to a separate cookie class. We need a centralized location to work with cookies
     * @param session {@link HttpSession}User session
     * @param model {@link Model}
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

    /**@Author Aidan Scott
     * @sinse 0.0.1
     * logs out user, invalidates session
     * @param request {@link HttpServletRequest}
     * @param session {@link HttpSession}
     * @return the "index" template
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request) {
        log.debug("logout: User '{}', session ID '{}', location '{}', logged out",
                userService.getUserById((long)session.getAttribute("UserId")), session.getId(), getClientIp(request));
        session.invalidate();
        return "index";
    }

    /**
     * @Author Aidan Scott
     * @since 0.0.1
     * takes request and finds the user's Ip. Used in place of user id when logging
     * TODO centralize getClientIp into one method {See UserController} to see other method
     * @param request {@link HttpServletRequest} takes the servlet request received from a post method
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
