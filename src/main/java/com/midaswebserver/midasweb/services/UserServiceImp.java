package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.models.User.Setting;
import com.midaswebserver.midasweb.models.User.User;
import com.midaswebserver.midasweb.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
     * If User is already in the database (same username) then the method will return false
     * @param user
     * @return true if user could be added, if there was a user of the same name or user is null, false
     */
    @Override
    public boolean add(User user) {
        if(user == null){
            log.warn("'add': User is null");
            return false;
        }
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

    @Override
    public User getUserByName(String userName) {
        if (userName != null) {
            List<User> users = userRepo.findByUsernameIgnoreCase(userName);
            if (users.size() == 1) {
                return users.get(0);
            }
        }
        return null;
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

    /**
     * Updates the user in database
     * @param user
     * @return
     */
    @Override
    //@Transactional
    public boolean update(User user) {
        if(user==null)
            return false;
        log.debug("User '{}' has been updated", user.getUsername());
        List<Setting> settings = new ArrayList<>(user.getSetting());
        log.debug("User's settings data is: '{}'", settings.get(0).getTicker());
        userRepo.save(user);
        return true;
    }

}
