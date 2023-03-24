package com.midaswebserver.midasweb.models.User;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import com.midaswebserver.midasweb.services.HashService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * @Author Aidan Scott
 * @since 0.0.1
 * @version 0.0.1
 * User is the model object holding basic user data like username, hashedPassword, email ect.
 */
@Entity
@Table(name = "app_user")
public class User {
    private static final long serialVersionUID = 1L;//this is what identifies the data
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
    @OneToMany(mappedBy = "user")//this is bad practice, you WILL change it back.
    @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    private Set<Setting> settings = new HashSet<>();

    public User() {
    }

    /**
     * @Author Aidan Scott
     * @since 0.0.1
     * @param username
     * @param hashedPassword password must be hashed before given.
     */
    public User(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }
    /**
     * @Author Aidan Scott
     * @since 0.0.1
     * @param username
     * @param hashedPassword password must be hashed before given.
     * @param email
     * @param phoneNumber maximum length of phone number is 15
     */
    public User(String username, String hashedPassword, String email, String phoneNumber) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Set<Setting> getSetting() {
        return settings;
    }
    public void setSetting(Set<Setting> settings) {
        this.settings = settings;
    }
    public void addSetting(Setting setting){
        this.settings.add(setting);
    }

    /**
     * @Author Aidan Scott
     * @since 0.0.1
     * @version 0.0.1
     * adds settings to user
     * @param ticker example: "EUC"
     * @param user {@link User}
     */
    public void addTicker(String ticker, User user){
        Setting setting = new Setting(ticker);
        setting.setUserId(user);
        this.settings.add(setting);
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
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
