package com.midaswebserver.midasweb.services;
import com.midaswebserver.midasweb.forms.RegisterForm;
import com.midaswebserver.midasweb.models.User;

public interface RegisterUserService {

    public boolean registerUser (User user);

    public boolean validateUniqueUsername (String username);
}
