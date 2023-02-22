package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.models.User;
import com.midaswebserver.midasweb.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);

    private final UserRepository userRepo;

    public UserServiceImp(UserRepository userRepo) {
        this.userRepo = userRepo;
        log.info("User Repository initialized in UserService");
    }
    @Override
    public boolean add(User user) {
        if(!validateUniqueUsername(user.getUsername())) {
            log.error("{} has the same name in database", user.getUsername());
            return false;
        }
        userRepo.save(user);
        return true;
    }

    @Override
    public boolean validateUniqueUsername(String username) {
        log.debug("validateUser: user '{}' attempted login", username);
        // Always do the lookup in a case-insensitive manner (lower-casing the data).
        List<User> users = userRepo.findByUsernameIgnoreCase(username);

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (users.size() != 0) {
            log.debug("validateUser: found {} users", users.size());
            return false;
        }
        return true;//i see this everywhere in Nates code, so i need to ask if its ok, or something we should do
    }

    @Override
    public boolean delete(User user) {
        if (user == null)
            return false;
        userRepo.delete(user);
        return true;
    }

    @Override
    public boolean deleteAll() {
        userRepo.deleteAll();
        log.warn("all users have been deleted");
        if(userRepo.count()>0)
            return false;
        return true;
    }

}
