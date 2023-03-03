package com.midaswebserver.midasweb.services;
import com.midaswebserver.midasweb.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean add(User user);

    boolean validateUniqueUsername (String username);

    boolean delete(User user);
    boolean deleteAll();

    User getUserByID(Long ID);
    Long getIDByUser(String username);
}
