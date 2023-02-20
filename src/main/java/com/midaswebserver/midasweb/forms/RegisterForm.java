package com.midaswebserver.midasweb.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterForm {

    private static final Logger log = LoggerFactory.getLogger(RegisterForm.class);
    public RegisterForm() {
    }
    //this is a bean used for validating incoming data
    public RegisterForm(String username, String password, String confirmPass, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.confirmPass = confirmPass;
        log.info("Created RegisterForm for {}", username);
    }
    @NotNull//validation code
    @Size(min = 6, max = 20, message = "Username must be at least 6 characters long")
    private String username;

    @NotNull
    @Size(min = 8, max = 30, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull
    @Size(min = 8, max = 30, message = "Passwords must be the same")
    private String confirmPass;
    @NotNull(message = "Must include a valid email")
    @Email //I will leave validation to this annotation
    private String email;

    @NotNull
    @Size(min = 10, max = 15, message = "Must use a valid phone number")
    private String phoneNumber;

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
