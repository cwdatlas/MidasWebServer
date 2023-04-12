package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.models.User.Symbol;
import com.midaswebserver.midasweb.models.User.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;

@Transactional
@SpringBootTest
public class UserServiceImpTest {

    @Autowired
    private UserService userService;
    private final String falsePass = "notTheSamePass";

    /**
     *arguably we test the validateUniqueUsername using these userAdd tests
     */
    //good test
    //TODO add more valid tests, once validation is added within addUser, add more tests
    @Test
    public void goodAddOneUser1(){
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        assertTrue("Test User Couldn't be added", userService.add(testUser1));
        assertNotNull("", userService.getUserById(testUser1.getId()));
    }
    @Test
    public void goodAddOneUser2(){
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        assertTrue("Test User Couldn't be added", userService.add(testUser2));
    }

    /**
     * Checking if more than one user can be added to the database
     */
    @Test
    public void goodUserAddTwoUsers(){
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        final User testUser3 = new User
                ("LordCabbage", "CabbageMan", "pleaseImSafe@friendly.edu", "23414443333");
        userService.add(testUser2);
        userService.add(testUser3);
        assertTrue("Test User wasn't added", null != userService.getUserById(testUser2.getId()));
        assertTrue("Test User wasn't added", null != userService.getUserById(testUser3.getId()));
    }

    /**
     * if two users of the same first name are added, should return false
     */
    @Test
    public void badUserAddSameUserTwice1(){
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser1);
        assertFalse("User was added", userService.add(testUser1));
    }
    @Test
    public void badUserAddSameUserTwice2(){
        final User testUser3 = new User
                ("LordCabbage", "CabbageMan", "pleaseImSafe@friendly.edu", "23414443333");
        userService.add(testUser3);
        assertFalse("User was added", userService.add(testUser3));
    }
    @Disabled
    @Test
    public void badUserAdd3(){ //TODO this requires validation to be done on the user to return false
        final User testUser4 = new User
                ("Cabbage@imdumb.edu", "imDumb", "CabbageMan", "6665556666");
        assertFalse("User was added", userService.add(testUser4));
    }
    @Test
    public void crazyUserAddNull(){
        assertFalse("User was added", userService.add(null));
    }
    @Test
    public void goodDeleteUser1(){
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser1);
        userService.delete(testUser1);
        assertTrue("User wasnt deleted", null == userService.getUserByName(testUser1.getUsername()));
    }

    @Test
    public void goodDeleteUser2(){
        final User testUser3 = new User
                ("LordCabbage", "CabbageMan", "pleaseImSafe@friendly.edu", "23414443333");
        userService.add(testUser3);
        userService.delete(testUser3);
        assertTrue("User wasnt deleted", null == userService.getUserByName(testUser3.getUsername()));
    }

    @Test
    public void badDeleteUser1(){
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser1);
        assertFalse("User was deleted", userService.delete(testUser2));
    }
    @Test
    public void badDeleteUser2(){
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        assertFalse("User was deleted", userService.delete(testUser1));
    }
    @Test
    public void crazyDeleteUser1(){
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser1);
        assertFalse("User was deleted", userService.delete(null));
    }

    @Test
    public void goodGetUserByID1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser1);
        User returnedUser = userService.getUserById(userService.getIdByUsername(testUser1.getUsername()));
        assertTrue("Returned User wasnt same as expected", returnedUser.equals(testUser1));
    }
    @Test
    public void goodGetUserByID2() {
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        User returnedUser = userService.getUserById(userService.getIdByUsername(testUser2.getUsername()));
        assertTrue("Returned User wasnt same as expected", returnedUser.equals(testUser2));
    }
    @Test
    public void badGetUserByID1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser1);
        User returnedUser = userService.getUserById(userService.getIdByUsername(testUser2.getUsername()));
        assertFalse("Returned User was the same then added user", returnedUser != null);
    }

    @Test
    public void badGetUserByID2() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        User returnedUser = userService.getUserById(userService.getIdByUsername(testUser1.getUsername()));
        assertFalse("Returned User was the same then added user", returnedUser != null);
    }

    @Test
    public void crazyGetUserByID1() {
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        User returnedUser = userService.getUserById(null);
        assertTrue("Returned User wasn't null", returnedUser == null);
    }

    @Test
    public void goodGetUserByName1(){
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser1);
        assertTrue("User couldn't be found", null != userService.getUserByName(testUser1.getUsername()));
    }
    @Test
    public void goodGetUserByName2(){
        final User testUser3 = new User
                ("LordCabbage", "CabbageMan", "pleaseImSafe@friendly.edu", "23414443333");
        userService.add(testUser3);
        assertTrue("User couldn't be found", null != userService.getUserByName(testUser3.getUsername()));
    }
    @Test
    public void badGetUserByName1(){
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        assertTrue("User was found where they shouldn't have been", null == userService.getUserByName(testUser1.getUsername()));
    }
    @Test
    public void crazyGetUserByName1(){
        assertTrue("User was found with input value null", null == userService.getUserByName(null));
    }

    /**
     * Checking if data can be updated within the database. This requires getting the object from the database with the
     * specific Id, then changing its data, then using that updated object in the update method
     * TODO: once validation is added, we will have to add in many more tests to make sure invalid update data cant be passed
     */
    @Test //Test if username can be updated (good)
    public void goodUpdateUserTest1(){
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
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
    public void goodUpdateUserTest2(){
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        //sets updates username
        String newUsername = "MadAtlas3";
        User user = userService.getUserByName(testUser2.getUsername());
        user.setUsername(newUsername);
        userService.update(user);
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByName(newUsername);
        assertTrue("Username wasnt changed", newUsername.equals(changedUser.getUsername()));
        userService.delete(changedUser);
    }
    @Test
    public void goodUpdateUserTest3(){
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        //sets updates username
        String newPhonenumber = "4066604455";
        testUser2.setPhoneNumber(newPhonenumber);
        userService.update(testUser2);
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByName(testUser2.getUsername());
        assertTrue("Phone number wasn't changed", newPhonenumber.equals(changedUser.getPhoneNumber()));
    }
    @Test
    public void badUpdateUserTest1(){
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        //sets updates username
        Long newId = (long)40666;
        testUser2.setId(newId);
        assertFalse("User was updated, when they shouldn't have", userService.update(testUser2));
    }
    @Test
    public void crazyUpdateUserTest1(){
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        assertFalse("User was updated, when null was passed", userService.update(null));
    }

    /**
     * There are a couple bugs related to updating settings.
     * User must be added to the setting object, this shouldn't be the case
     * validation must be included to make sure that invalid information isn't used to update
     */
    @Test
    public void goodUpdateSettingTest1(){
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser1);
        //sets updates username
        String ticker = "EUC";
        Symbol symbol = new Symbol(ticker);
        symbol.setUserId(testUser1);//I shouldnt have to add the user to the symbol
        testUser1.addSetting(symbol);
        userService.update(testUser1);
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByName(testUser1.getUsername());
        Symbol[] setArray = changedUser.getSetting().toArray(new Symbol[changedUser.getSetting().size()]);
        List<Symbol> setList = new ArrayList<>(changedUser.getSetting());
        userService.delete(changedUser);
        assertTrue("Returned ticker and set ticker wasn't the same", setList.get(0).getTicker().equals(ticker));
    }
    @Test
    public void goodUpdateSettingTest2(){
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        //sets updates username
        String ticker = "EUC";
        Symbol symbol = new Symbol(ticker);
        symbol.setUserId(testUser2);//I shouldnt have to add the user to the symbol
        testUser2.addSetting(symbol);
        userService.update(testUser2);//<--This is the main function that we are testing
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByName(testUser2.getUsername());
        Symbol[] setArray = changedUser.getSetting().toArray(new Symbol[changedUser.getSetting().size()]);
        List<Symbol> setList = new ArrayList<>(changedUser.getSetting());
        userService.delete(changedUser);
        assertTrue("Returned ticker and set ticker wasn't the same", setList.get(0).getTicker().equals(ticker));
    }
}
