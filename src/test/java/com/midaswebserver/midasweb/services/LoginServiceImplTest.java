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
//get services and other required objects to test or to aid the testing
    @Autowired
    private LoginService loginService;
    @Test
    public void validateUserSuccessTest() {
        String username = "testuser";
        String password = "testpass";
        final LoginForm form = new LoginForm(username, password);
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserExistingUserInvalidPasswordTest() {
        String username = "testuser";
        String password = "testpass";
        final LoginForm form = new LoginForm(username, password + "extra");
        assertFalse("validateUserExistingUserInvalidPasswordTest: should fail using a valid user, invalid pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserValidPasswordTest() {
        String username = "testuser";
        String password = "testpass";
        final LoginForm form = new LoginForm(username + "not", password);
        assertFalse("validateUserInvalidUserValidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserInvalidPasswordTest() {
        String username = "testuser";
        String password = "testpass";
        final LoginForm form = new LoginForm(username + "not", password + "extra");
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(form));
    }
}