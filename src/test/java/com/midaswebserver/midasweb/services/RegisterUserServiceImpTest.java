package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.forms.RegisterUserForm;
import com.midaswebserver.midasweb.models.User;
import com.midaswebserver.midasweb.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class RegisterUserServiceImpTest {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RegisterUserService registerUserService;
    private final RegisterUserForm registerForm =
            new RegisterUserForm("CabbageMan", "password",
                    "password", "friendly@whoareyou.com", "4065556666");
    private final User testUser = new User(registerForm);
    private final String falsePass = "notTheSamePass";
    @BeforeEach//cool annotation that we have the environment set up and ready for each test
    public void beforeTest() {
        assertNotNull("loginRepository must be injected", userRepo);
        assertNotNull("loginService must be injected", registerUserService);
    }
    @Test //valid input test
    public void registerUserTest() {
        assertTrue("User registered: ", registerUserService.registerUser(registerForm));
    }
    @Test //nonValid input
    public void registerUserSameNameTest() {//this needs to be rewriten
        assertFalse("User registered: ", registerUserService.registerUser(registerForm));
    }
    @Test //This needs to be populated with other testing. ASK ABOUT THIS
    public void validateUserTest(){
        assertTrue("User valid: ", registerUserService.validateUser(registerForm));
    }
    @Test //Invalad user
    public void validateInvaladUserTest(){//this will fail as there is no condition that states if a user is generally invalid
        assertFalse("User valid: ", registerUserService.validateUser(registerForm));
    }
    @Test //test without name in database
    public void validateUniqueUsernameTest(){
        assertTrue("Username is Unique: ", registerUserService.validateUniqueUsername(registerForm.getUsername()));
    }
    @Test //test with name already in database
    public void validateSameUsernameTest(){
        userRepo.save(testUser);
        assertFalse("Username is Unique: ", registerUserService.validateUniqueUsername(registerForm.getUsername()));
        userRepo.delete(testUser);
    }
    @Test //checking two of the same
    public void validatePasswordsTest(){
        assertTrue("Passwords match ", registerUserService.validatePasswords(registerForm.getPassword(),
                registerForm.getConfirmPass()));
    }
    @Test //checking two different
    public void validateDifPasswordsTest(){
        assertFalse("Passwords match ", registerUserService.validatePasswords(registerForm.getPassword(),
                falsePass));
    }
}
