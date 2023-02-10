package com.midaswebserver.midasweb.services;
import com.midaswebserver.midasweb.forms.UserForm;
import com.midaswebserver.midasweb.models.User;
public interface UserService {
    public boolean addUser (UserForm userForm);

    public boolean deleteUser (User user);
}
