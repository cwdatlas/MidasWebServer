package com.midaswebserver.midasweb.services;
import com.midaswebserver.midasweb.forms.RegisterForm;

public interface RegisterUserService {

    public boolean registerUser (RegisterForm userForm);

    public boolean validateUniqueUsername (String username);
    public boolean validatePasswords (String password, String confirmPass);
}
