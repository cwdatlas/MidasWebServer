package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.forms.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.midaswebserver.midasweb.repositories.UserRepository;
import com.midaswebserver.midasweb.models.User.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * LoginServiceImp validates user data relating to login
 * TODO consolidate loginService and UserService methods
 */
@Service
public class LoginServiceImp implements LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImp.class);
    private final UserRepository loginRepo;
    @Autowired
    private HashService hashService;

    @Autowired
    private UserService userService;
    public LoginServiceImp(UserRepository loginRepo) {
        this.loginRepo = loginRepo;
    }
    /**
     * Given a loginForm, determine if the information provided is valid, and the user exists in the system.
     * @param loginForm - Data containing user login information, such as username and password.
     * @return true if data exists and matches what's on record, false otherwise
     */
    @Override
    public boolean validateUser(LoginForm loginForm) {
        log.debug("validateUser:'{}' attempted login", userService.getUserByName(loginForm.getUsername()));
        // Always do the lookup in a case-insensitive manner (lower-casing the data).
        List<User> users = loginRepo.findByUsernameIgnoreCase(loginForm.getUsername());

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (users.size() != 1) {
            log.debug("validateUser: Found '{}' user(s), invalid user", users.size());
            return false;
        }
        User u = users.get(0);
        final String userProvidedHash = hashService.getHash(loginForm.getPassword());//blowfish is a 8-9 so do that
        if (!u.getHashedPassword().equals(userProvidedHash)) {
            log.debug("validateUser: Given password doesnt match stored password");
            return false;
        }

        // User exists, and the provided password matches the hashed password in the database.
        log.debug("validateUser: User '{}', successful login", userService.getUserByName(loginForm.getUsername()));
        return true;
    }
}

