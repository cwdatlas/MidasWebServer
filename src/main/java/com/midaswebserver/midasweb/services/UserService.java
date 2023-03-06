package com.midaswebserver.midasweb.services;
import com.midaswebserver.midasweb.models.User.User;

public interface UserService {

    boolean add(User user);

    boolean validateUniqueUsername (String username);

    boolean delete(User user);
    boolean deleteAll();

    User getUserByID(Long ID);
    Long getIDByUser(String username);
}
