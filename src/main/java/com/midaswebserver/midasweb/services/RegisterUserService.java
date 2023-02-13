package com.midaswebserver.midasweb.services;
import com.midaswebserver.midasweb.forms.RegisterUserForm;
import com.midaswebserver.midasweb.models.User;
public interface RegisterUserService {

    public boolean registerUser (RegisterUserForm userForm);

    public boolean validateUser (RegisterUserForm userForm);
    public boolean validateUniqueUsername (String username);
    public boolean validatePasswords (String password, String confirmPass);
}
