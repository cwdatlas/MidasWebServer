package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.models.User.Setting;
import com.midaswebserver.midasweb.models.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class UserServiceImpTest {

    @Autowired
    private UserService userService;
    private final User testUser = new User
                    ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
    private final String falsePass = "notTheSamePass";
    @BeforeEach//preparing unit tests with a fresh database and varifying the database can be interacted with
    public void beforeTest() {
        assertNotNull("loginService must be injected", userService);
        userService.deleteAll();
    }
    @Test //valid input test if user can be registered
    public void registerUserTest() {
        assertTrue("User was unable to Register", userService.add(testUser));
    }
    @Test //nonValid input
    public void registerUserSameNameTest() {
        userService.add(testUser);
        assertFalse("input was Valid, should be invalid", userService.add(testUser));
    }
    @Test //test without name in database
    public void validateUniqueUsernameTest(){
        assertTrue("Input username not unique", userService.validateUniqueUsername(testUser.getUsername()));
    }
    @Test //test with name already in database
    public void validateSameUsernameTest(){
        userService.add(testUser);
        assertFalse("Username was Unique", userService.validateUniqueUsername(testUser.getUsername()));
        userService.delete(testUser);
    }
    @Test //Test if username can be updated (good)
    public void updateUsernameTest(){
        userService.add(testUser);
        //sets updates username
        String newUsername = "MadAtlas2";
        testUser.setUsername(newUsername);
        userService.update(testUser);
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByName(newUsername);
        assertTrue("Username wasnt changed", newUsername.equals(changedUser.getUsername()));
        userService.delete(changedUser);
    }

    @Test
    @Transactional
    public void updateSettingTest(){
        userService.add(testUser);
        //sets updates username
        String ticker = "EUC";
        Setting setting = new Setting(ticker);
        setting.setUserId(testUser);//I shouldnt have to add the user to the setting
        testUser.addSetting(setting);
        userService.update(testUser);
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByName(testUser.getUsername());
        Setting[] setArray = changedUser.getSetting().toArray(new Setting[changedUser.getSetting().size()]);
        List<Setting> setList = new ArrayList<>(changedUser.getSetting());
        assertTrue("Username wasnt changed", setList.get(0).getTicker().equals(ticker));
        userService.delete(changedUser);
    }
}
