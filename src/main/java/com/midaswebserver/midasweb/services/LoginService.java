package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.forms.LoginForm;

public interface LoginService {
    boolean validateUser(LoginForm form);//change to two paramaters rather than the form
}
