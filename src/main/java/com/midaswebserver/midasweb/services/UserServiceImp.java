package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.models.User.User;
import com.midaswebserver.midasweb.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        if (user == null) {
            log.info("Delete: null was passed to method");
            return false;
        }
        if (this.getUserByName(user.getUsername())==null){
            log.debug("Delete: User '{}' was not found in database to delete", user.getUsername());
            return false;
        }
        userRepo.delete(user);
        return true;
    }


    /**
     * Gets User by Id
     *TODO have validation and safe and expected returns;
     * @param ID
     * @return User if user excists, returns null otherwise and if user passes null ID
     */
    @Override
    public User getUserById(Long ID) {
        if(ID == null){
            return null;
        }
        try {
            return userRepo.findById(ID).get();
        }catch(Exception e){
            log.info("getUserByID: id, '{}', didnt have correspoding user", ID);
            log.info("getUserByID: '{}' was thrown", e);
        }
        return null;
    }

    /**
     * Gets user with the corresponding name
     * @param userName
     * @return user with corresponding name, or null if no user was found
     */
    @Override
    public User getUserByName(String userName) {
        if (userName != null) {
            try {
                List<User> users = userRepo.findByUsernameIgnoreCase(userName);
                if (users.size() == 1) {
                    return users.get(0);
                } else {
                    log.warn("GetUserByName: found more than one user with '{}' name", userName);
                }
            } catch (Exception e) {
                log.info("getUserByName: '{}' was thrown", e);
            }
        }
        return null;
    }

    /**
     * Gets Id by username
     * @param username
     * @return the ID of the user or null if there isn't a user in the database with that username or if there are more than one user
     */
    @Override
    public Long getIdByUsername(String username) {
         List<User> users = userRepo.findByUsernameIgnoreCase(username);
         if (username == null){
            log.warn("getIDByUser: passed username was null");
            return null;
        }
         if (users.size() == 0){
             log.info("getIDByUser: no user found with username of '{}'", username);
             return null;
         }
         if(users.size()>1){
             log.warn("getIDByUser: two users with username '{}' have been found", username);
             return null;
         }
        return users.get(0).getId();
    }

    /**
     * Updates the user in database, make sure that the param has the original user ID set as its ID,
     * the function will return null if not
     * @param user
     * @return
     */
    @Override
    public boolean update(User user) {
        if(user==null)
            return false;
        if(this.getUserById(user.getId()) == null){
            log.warn("ID of user '{}' didn't match anything in database", user.getUsername());
            return false;
        }
        log.debug("User '{}' has been updated", user.getUsername());
        userRepo.save(user);
        return true;
    }

}
