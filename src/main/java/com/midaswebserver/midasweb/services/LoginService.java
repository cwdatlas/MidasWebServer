package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.forms.LoginForm;
/**
 * @Author Aidan Scott
 * @since 0.0.1
 * @version 0.0.1
 * LoginServiceImp validates user data relating to login
 * TODO consolidate loginService and UserService methods
 */
public interface LoginService {
    /**
     * @Author Aidan Scott
     * @since 0.0.1
     * @version 0.0.1
     * Given a loginForm, determine if the information provided is valid, and the user exists in the system.
     * @param form - Data containing user login information, such as username and password.
     * @return true if data exists and matches what's on record, false otherwise
     */
    boolean validateUser(LoginForm form);//change to two paramaters rather than the form
}
