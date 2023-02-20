package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.forms.RegisterForm;
import com.midaswebserver.midasweb.models.User;
import com.midaswebserver.midasweb.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class RegisterUserServiceImpTest {
    @Autowired
    private UserRepository userRepo; //We need a test repo for unit tests. we cant run unit tests on the real database
    @Autowired
    private RegisterUserService registerUserService;
    private final RegisterForm registerForm =
            new RegisterForm("CabbageMan", "password",
                    "password", "friendly@whoareyou.com", "4065556666");
    private final User testUser = new User(registerForm);
    private final String falsePass = "notTheSamePass";
    @BeforeEach//preparing unit tests with a fresh database and varifying the database can be interacted with
    public void beforeTest() {
        assertNotNull("loginRepository must be injected", userRepo);
        assertNotNull("loginService must be injected", registerUserService);
        userRepo.deleteAll();
    }
    @Test //valid input test
    public void registerUserTest() {
        assertTrue("User was unable to Register", registerUserService.registerUser(registerForm));
    }
    @Test //nonValid input
    public void registerUserSameNameTest() {
        userRepo.save(testUser);
        assertFalse("input was Valid, should be invalid", registerUserService.registerUser(registerForm));
    }
    @Test //test without name in database
    public void validateUniqueUsernameTest(){
        assertTrue("Input username not unique", registerUserService.validateUniqueUsername(registerForm.getUsername()));
    }
    @Test //test with name already in database
    public void validateSameUsernameTest(){
        userRepo.save(testUser);
        assertFalse("Username was Unique", registerUserService.validateUniqueUsername(registerForm.getUsername()));
        userRepo.delete(testUser);
    }
    @Test //checking two of the same
    public void validatePasswordsTest(){
        assertTrue("Passwords dont match", registerUserService.validatePasswords(registerForm.getPassword(),
                registerForm.getConfirmPass()));
    }
    @Test //checking two different
    public void validateDifPasswordsTest(){
        assertFalse("Passwords match", registerUserService.validatePasswords(registerForm.getPassword(),
                falsePass));
    }
}
