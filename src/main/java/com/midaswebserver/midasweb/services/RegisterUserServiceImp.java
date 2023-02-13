package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.forms.RegisterUserForm;
import com.midaswebserver.midasweb.models.User;
import com.midaswebserver.midasweb.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterUserServiceImp implements RegisterUserService {

    private static final Logger log = LoggerFactory.getLogger(RegisterUserServiceImp.class);

    private final UserRepository userRepo;

    public RegisterUserServiceImp(UserRepository userRepo) {
        this.userRepo = userRepo;
        log.info("User Repository initialized in RegisterUserService");
    }
    @Override
    public boolean registerUser(RegisterUserForm userForm) {
        log.info("{} verified", userForm.getUsername());
        return true;
    }

    @Override
    public boolean validateUser(RegisterUserForm userForm) {
        userRepo.save(new User(userForm));
        log.info("{} registered", userForm.getUsername());
        return true;
    }

    @Override
    public boolean validateUniqueUsername(String username) {
        log.info("validateUser: user '{}' attempted login", username);
        // Always do the lookup in a case-insensitive manner (lower-casing the data).
        List<User> users = userRepo.findByUsernameIgnoreCase(username);

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (users.size() != 1) {
            log.debug("validateUser: found {} users", users.size());
            return false;
        }
        return true;//i see this everywhere in Nates code, so i need to ask if its ok, or something we should do
    }

    @Override
    public boolean validatePasswords(String password, String confirmPass) {
        if(password != confirmPass){
            log.info("User's passwords are different");
            return false;
        }
        log.info("Passwords are the same");
        return true;
    }
}
