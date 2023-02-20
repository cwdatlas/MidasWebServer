package com.midaswebserver.midasweb.services;
import com.midaswebserver.midasweb.models.User;

public interface UserService {

    public boolean registerUser (User user);

    public boolean validateUniqueUsername (String username);
}
