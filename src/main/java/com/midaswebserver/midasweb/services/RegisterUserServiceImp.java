package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.forms.RegisterUserForm;
import com.midaswebserver.midasweb.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterUserServiceImp implements RegisterUserService {

    private static final Logger log = LoggerFactory.getLogger(RegisterUserServiceImp.class);

    private final UserRepository userRepo;

    public RegisterUserServiceImp(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public boolean registerUser(RegisterUserForm userForm) {
        return false;
    }

    @Override
    public boolean validateUser(RegisterUserForm userForm) {
        return false;
    }
}
