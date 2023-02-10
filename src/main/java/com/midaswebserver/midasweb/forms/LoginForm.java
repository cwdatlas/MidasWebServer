package com.midaswebserver.midasweb.forms;

import com.midaswebserver.midasweb.services.LoginServiceImp;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginForm {
    private static final Logger log = LoggerFactory.getLogger(LoginForm.class);
    public LoginForm() {
    }
    //this is a bean used for validating incoming data
    public LoginForm(String username, String password) {
        this.username = username;
        this.password = password;
        log.info("Created LoginForm for {}", username);
    }
    @NotNull//validation code
    @Size(min = 6, message = "Username must be at least 6 characters long")
    private String username;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

