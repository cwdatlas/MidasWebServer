package com.midaswebserver.midasweb.services;
import com.midaswebserver.midasweb.models.User;

public interface UserService {

    public boolean add(User user);

    public boolean validateUniqueUsername (String username);

    public boolean delete(User user);
    public boolean deleteAll();
}
