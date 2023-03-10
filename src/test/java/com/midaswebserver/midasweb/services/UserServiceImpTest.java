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
    private final User testUser1 = new User
            ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
    private final User testUser2 = new User
            ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
    private final User testUser3 = new User
            ("LordCabbage", "CabbageMan", "pleaseImSafe@friendly.edu", "23414443333");
    private final User testUser4 = new User
            ("Cabbage@imdumb.edu", "imDumb", "CabbageMan", "6665556666");
    private final String falsePass = "notTheSamePass";
    @BeforeEach//preparing unit tests with a fresh database and varifying the database can be interacted with
    public void beforeTest() {
        assertNotNull("loginService must be injected", userService);
        userService.deleteAll();
    }

    /**
     *arguably we test the validateUniqueUsername using these userAdd tests
     */
    //good test
    //TODO add more valid tests, once validation is added within addUser, add more tests
    @Test
    public void goodUserAdd1(){
        assertTrue("Test User Couldn't be added", userService.add(testUser1));
    }
    @Test
    public void goodUserAdd2(){
        assertTrue("Test User Couldn't be added", userService.add(testUser2));
    }
    @Test
    public void goodUserAdd3(){
        assertTrue("Test User Couldn't be added", userService.add(testUser3));
    }
    /**
     * Checking if more than one user can be added to the database
     */
    @Test
    public void goodUserAdd4(){
        userService.add(testUser2);
        assertTrue("Test User Couldn't be added", userService.add(testUser3));
    }
    @Test
    public void goodUserAdd5(){
        userService.add(testUser1);
        userService.add(testUser2);
        assertTrue("Test User Couldn't be added", userService.add(testUser3));
    }

    /**
     * if two users of the same first name are added, should return false
     */
    @Test
    public void badUserAdd1(){
        userService.add(testUser1);
        assertFalse("User was added", userService.add(testUser1));
    }
    @Test
    public void badUserAdd2(){
        userService.add(testUser3);
        assertFalse("User was added", userService.add(testUser3));
    }
    @Test
    public void badUserAdd3(){ //TODO this requires validation to be done on the user to return false
        assertFalse("User was added", userService.add(testUser4));
    }
    @Test
    public void crazyUserAdd1(){
        assertFalse("User was added", userService.add(null));
    }
    @Test
    public void goodDeleteUser1(){
        userService.add(testUser1);
        userService.delete(testUser1);
        assertTrue("User wasnt deleted", null == userService.getUserByName(testUser1.getUsername()));
    }

    @Test
    public void goodDeleteUser2(){
        userService.add(testUser3);
        userService.delete(testUser3);
        assertTrue("User wasnt deleted", null == userService.getUserByName(testUser3.getUsername()));
    }

    @Test
    public void badDeleteUser1(){
        userService.add(testUser1);
        assertFalse("User was deleted", userService.delete(testUser2));
    }
    @Test
    public void badDeleteUser2(){
        userService.add(testUser2);
        assertFalse("User was deleted", userService.delete(testUser1));
    }
    @Test
    public void crazyDeleteUser1(){
        userService.add(testUser1);
        assertFalse("User was deleted", userService.delete(null));
    }

    /**
     * I have no idea how to create do bad or crazy deleteAll tests
     */
    @Test
    public void goodDeleteAll1(){
        userService.add(testUser1);
        userService.deleteAll();
        assertTrue("User wasn't deleted", null == userService.getUserByName(testUser1.getUsername()));
    }
    @Test
    public void goodDeleteAll2(){
        userService.add(testUser1);
        userService.add(testUser2);
        userService.deleteAll();
        assertTrue("User wasn't deleted", null == userService.getUserByName(testUser1.getUsername()) &&
                null == userService.getUserByName(testUser2.getUsername()));
    }
    @Test
    public void goodDeleteAll3(){
        userService.add(testUser1);
        userService.add(testUser2);
        userService.add(testUser2);
        userService.deleteAll();
        assertTrue("User wasn't deleted", null == userService.getUserByName(testUser1.getUsername()) &&
                null == userService.getUserByName(testUser2.getUsername()) &&
                null == userService.getUserByName(testUser3.getUsername()));
    }
    @Test
    public void goodDeleteAll4(){
        userService.add(testUser1);
        userService.add(testUser2);
        userService.add(testUser2);
        assertTrue("User was deleted", userService.deleteAll());
    }
    @Test
    public void goodGetUserByID1() {
        userService.add(testUser1);
        User returnedUser = userService.getUserByID(userService.getIDByUser(testUser1.getUsername()));
        assertTrue("Returned User wasnt same as expected", returnedUser.equals(testUser1));
    }
    @Test
    public void goodGetUserByID2() {
        userService.add(testUser2);
        User returnedUser = userService.getUserByID(userService.getIDByUser(testUser2.getUsername()));
        assertTrue("Returned User wasnt same as expected", returnedUser.equals(testUser2));
    }
    @Test
    public void badGetUserByID1() {
        userService.add(testUser1);
        User returnedUser = userService.getUserByID(userService.getIDByUser(testUser2.getUsername()));
        assertFalse("Returned User was the same then added user", returnedUser.equals(testUser2));
    }

    @Test
    public void badGetUserByID2() {
        userService.add(testUser2);
        User returnedUser = userService.getUserByID(userService.getIDByUser(testUser1.getUsername()));
        assertFalse("Returned User was the same then added user", returnedUser.equals(testUser1));
    }

    @Test
    public void crazyGetUserByID1() {
        userService.add(testUser2);
        User returnedUser = userService.getUserByID(null);
        assertTrue("Returned User wasn't null", returnedUser == null);
    }

    @Test //Test if username can be updated (good)
    public void updateUsernameTest(){
        userService.add(testUser1);
        //sets updates username
        String newUsername = "MadAtlas2";
        testUser1.setUsername(newUsername);
        userService.update(testUser1);
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByName(newUsername);
        assertTrue("Username wasnt changed", newUsername.equals(changedUser.getUsername()));
        userService.delete(changedUser);
    }

    @Test
    @Transactional
    public void updateSettingTest(){
        userService.add(testUser1);
        //sets updates username
        String ticker = "EUC";
        Setting setting = new Setting(ticker);
        setting.setUserId(testUser1);//I shouldnt have to add the user to the setting
        testUser1.addSetting(setting);
        userService.update(testUser1);
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByName(testUser1.getUsername());
        Setting[] setArray = changedUser.getSetting().toArray(new Setting[changedUser.getSetting().size()]);
        List<Setting> setList = new ArrayList<>(changedUser.getSetting());
        userService.delete(changedUser);
        assertTrue("Username wasnt changed", setList.get(0).getTicker().equals(ticker));
    }
}
