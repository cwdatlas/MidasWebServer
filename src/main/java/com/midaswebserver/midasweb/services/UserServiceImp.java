package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.models.User;
import com.midaswebserver.midasweb.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * UserServiceImp is the CRUD service for Users. the majority of user logic goes through the
 * UserServiceImp
 */
@Service
public class UserServiceImp implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);
    private final UserRepository userRepo;

    public UserServiceImp(UserRepository userRepo) {
        this.userRepo = userRepo;
        log.debug("User Repository initialized in UserService");
    }

    /**
     * Adds user to the database
     * @param user
     * @return true TODO this needs to say true or false depending on success
     */
    @Override
    public boolean add(User user) {
        if(!validateUniqueUsername(user.getUsername())) {
            log.error("{} has the same name in database", user.getUsername());
            return false;
        }
        userRepo.save(user);
        return true;
    }

    /**
     * ValidateUniqueUsername checks to see if there is another person in the database with the same username
     * @param username
     * @return boolean
     */
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
        return true;
    }

    /**
     * delete deletes a user from the database
     * TODO make sure the process succeeded
     * @param user
     * @return boolean
     */
    @Override
    public boolean delete(User user) {
        if (user == null)
            return false;
        userRepo.delete(user);
        return true;
    }

    /**
     * deleteALL deletes all users in database
     * @return boolean
     */
    @Override
    public boolean deleteAll() {
        userRepo.deleteAll();
        log.warn("all users have been deleted");
        if(userRepo.count()>0)
            return false;
        return true;
    }

    /**
     * Gets User by Id
     *TODO have validation and safe and expected returns
     * @param ID
     * @return
     */
    @Override
    public User getUserByID(Long ID) {
        return userRepo.findById(ID).get();
    }

    /**
     * Gets Id by username
     * @param username
     * @return
     */
    @Override
    public Long getIDByUser(String username) {
        Long userId = userRepo.findByUsernameIgnoreCase(username).get(0).getId();
        return userId;
    }

}
