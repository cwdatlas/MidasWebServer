package com.midaswebserver.midasweb.models;

import com.midaswebserver.midasweb.forms.RegisterUserForm;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
    private static final Logger log = LoggerFactory.getLogger(User.class);
    private static final long serialVersionUID = 1L;//this is what identifies the data
    public User() {
    }
    public User(RegisterUserForm userForm){
        log.info("User Object created with RegForm");
        this.username = userForm.getUsername();
        setRawPassword(userForm.getPassword());
        this.email = userForm.getEmail();
        this.phoneNumber = userForm.getPhoneNumber();
    }

    public User(String username, String rawPassword) {
        log.info("User Object has been created with name of {}", username);
        this.username = username;
        setRawPassword(rawPassword);
    }

    public User(String username, String rawPassword, String email, String phoneNumber) {
        log.info("User Object has been created with name of {}", username);
        this.username = username;
        setRawPassword(rawPassword);
        this.email = email;
        this.phoneNumber = phoneNumber;

    }
    public void setRawPassword(String rawPassword) {
        // XXX - This should *NEVER* be done in a real project

        this.hashedPassword = Integer.toString(rawPassword.hashCode());// im going to assume the negetive part of this action is hashing
        log.info("Password has been hashed {}", this.hashedPassword!=null);// I would like to create a service that hashes passwords
        //with the java hash and doing it in a way that isnt salted
    }
    @Id//primary key!! this is what makes it primary! (can be composite)
    @GeneratedValue
    private Long id;//must be an Integer so the ID isnt 0 if the object hasnt been set (should use Long)

    @Column(name = "username", nullable = false, unique = true)//these parts on the end are for data verification
    private String username;

    @Column(name = "password", nullable = false)
    private String hashedPassword;

    @Column(name = "email", nullable = true)
    private String email;
    @Column(name = "phonenumber", nullable = true)
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    private static final String EOL = System.lineSeparator();
    private static final String TAB = "\t";

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Login @ ").append(super.toString()).append("[").append(EOL);
        builder.append(TAB).append("username=").append(username).append(EOL);
        builder.append(TAB).append("hashedPassword=").append("****").append(EOL);
        builder.append("]").append(EOL);
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final User login = (User)o;
        return username.equals(login.username) && hashedPassword.equals(login.hashedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, hashedPassword);
    }
}
