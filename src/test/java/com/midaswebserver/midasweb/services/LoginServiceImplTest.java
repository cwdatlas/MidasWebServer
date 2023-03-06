package com.midaswebserver.midasweb.services;


import com.midaswebserver.midasweb.forms.LoginForm;
import com.midaswebserver.midasweb.models.User.User;
import com.midaswebserver.midasweb.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class LoginServiceImplTest {
    //create test variables, usernames and passwords/other data that will be used for the test that doesnt change
    private static final String username = "testuser";
    private static final String password = "testpass";
//get services and other required objects to test or to aid the testing
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository loginRepo;

    private final User fakeUser = new User(username, password);//login (the name) might want to be changed as it is a little confusing

    @BeforeEach
    public void beforeTest() {
        assertNotNull("UserRepository must be injected", loginRepo);
        assertNotNull("loginService must be injected", loginService);

        // Ensure dummy record is in the DB
        final List<User> users =
                loginRepo.findByUsernameIgnoreCase(username);
        if (users.isEmpty())
            loginRepo.save(fakeUser);
    }

    @Test
    public void validateUserSuccessTest() {
        final LoginForm form = new LoginForm(username, password);
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserExistingUserInvalidPasswordTest() {
        final LoginForm form = new LoginForm(username, password + "extra");
        assertFalse("validateUserExistingUserInvalidPasswordTest: should fail using a valid user, invalid pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserValidPasswordTest() {
        final LoginForm form = new LoginForm(username + "not", password);
        assertFalse("validateUserInvalidUserValidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserInvalidPasswordTest() {
        final LoginForm form = new LoginForm(username + "not", password + "extra");
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(form));
    }
}