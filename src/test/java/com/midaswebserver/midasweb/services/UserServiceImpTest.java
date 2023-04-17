package com.midaswebserver.midasweb.services;

import com.midaswebserver.midasweb.models.User.Symbol;
import com.midaswebserver.midasweb.models.User.User;
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

    /**
     * arguably we test the validateUniqueUsername using these userAdd tests
     */
    //good test
    //TODO add more valid tests, once validation is added within addUser, add more tests
    @Test
    public void goodAddOneUser1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        assertTrue("Test User Couldn't be added", userService.add(testUser1));
        assertNotNull("User was not found in database", userService.getUserById(testUser1.getId()));
    }

    @Test
    public void goodAddOneUser2() {
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        assertTrue("Test User Couldn't be added", userService.add(testUser2));
        assertNotNull("User was not found in database", userService.getUserById(testUser2.getId()));
    }

    /**
     * Checking if more than one user can be added to the database
     */
    @Test
    public void goodUserAddTwoUsers() {
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        final User testUser3 = new User
                ("LordCabbage", "CabbageMan", "pleaseImSafe@friendly.edu", "23414443333");
        assertTrue("TestUser2 wasn't added in add method", userService.add(testUser2));
        assertTrue("TestUser3 wasn't added in add method", userService.add(testUser3));
        assertTrue("TestUser2 was added but not found", null != userService.getUserByUsername(testUser2.getUsername()));
        assertTrue("TestUser3 was added but not found", null != userService.getUserByUsername(testUser3.getUsername()));
    }

    /**
     * if two users of the same first name are added, should return false
     */
    @Test
    public void badUserAddSameUserTwice1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser1);
        assertFalse("User was added", userService.add(testUser1));
        assertTrue("Test User wasn't added", null != userService.getUserById(testUser1.getId()));
    }

    @Test
    public void badUserAddSameUserTwice2() {
        final User testUser3 = new User
                ("LordCabbage", "CabbageMan", "pleaseImSafe@friendly.edu", "23414443333");
        userService.add(testUser3);
        assertFalse("User was added", userService.add(testUser3));
        assertTrue("Test User wasn't added", null != userService.getUserById(testUser3.getId()));
    }

    @Test
    public void crazyUserAddNull() {
        assertFalse("User was added", userService.add(null));
    }

    @Test
    public void crazyUserAddBlank() {
        assertFalse("User was added", userService.add(new User()));
    }

    @Test
    public void goodDeleteUserAndCheck1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser1);
        assertTrue("Test User wasn't added", null != userService.getUserById(testUser1.getId()));
        userService.delete(testUser1);
        assertTrue("User wasnt deleted", null == userService.getUserById(testUser1.getId()));
    }

    @Test
    public void goodDeleteUserAndCheck2() {
        final User testUser3 = new User
                ("LordCabbage", "CabbageMan", "pleaseImSafe@friendly.edu", "23414443333");
        userService.add(testUser3);
        assertTrue("Test User wasn't added", null != userService.getUserById(testUser3.getId()));
        userService.delete(testUser3);
        assertTrue("User wasnt deleted", null == userService.getUserById(testUser3.getId()));
    }

    @Test
    public void goodAddTwoUsersDeleteOne3() {
        final User testUser3 = new User
                ("LordCabbage", "CabbageMan", "pleaseImSafe@friendly.edu", "23414443333");
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser3);
        userService.add(testUser1);
        assertTrue("Test User wasn't added", null != userService.getUserById(testUser3.getId()));
        assertTrue("Test User wasn't added", null != userService.getUserById(testUser1.getId()));
        userService.delete(testUser3);
        assertTrue("User wasnt deleted", null == userService.getUserById(testUser3.getId()));
        assertTrue("Test User wasn't added", null != userService.getUserById(testUser1.getId()));
    }

    @Test
    public void BadAddTwoUsersDeleteWrongOne3() {
        final User testUser3 = new User
                ("LordCabbage", "CabbageMan", "pleaseImSafe@friendly.edu", "23414443333");
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser3);
        userService.add(testUser1);
        assertTrue("Test User wasn't added", null != userService.getUserById(testUser3.getId()));
        assertTrue("Test User wasn't added", null != userService.getUserById(testUser1.getId()));
        userService.delete(testUser3);
        assertTrue("User wasnt deleted", null == userService.getUserById(testUser3.getId()));
        assertTrue("Test User wasn't added", null != userService.getUserById(testUser1.getId()));
    }

    @Test
    public void badDeleteUnknownUserUser1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser1);
        assertFalse("User was deleted", userService.delete(testUser2));
        assertFalse("User was deleted", null == userService.getUserById(testUser1.getId()));
    }

    @Test
    public void badDeleteUnknownUser2() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        assertFalse("User was deleted", userService.delete(testUser1));
        assertTrue("User was deleted", null != userService.getUserById(testUser2.getId()));
    }

    @Test
    public void crazyDeleteUserWithNull1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser1);
        assertFalse("User was deleted", userService.delete(null));
        assertTrue("User was deleted", null != userService.getUserById(testUser1.getId()));
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
    public void goodGetUserByName1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser1);
        assertTrue("User couldn't be found", null != userService.getUserByUsername(testUser1.getUsername()));
    }

    @Test
    public void goodGetUserByName2() {
        final User testUser3 = new User
                ("LordCabbage", "CabbageMan", "pleaseImSafe@friendly.edu", "23414443333");
        userService.add(testUser3);
        assertTrue("User couldn't be found", null != userService.getUserByUsername(testUser3.getUsername()));
    }

    @Test
    public void badGetUserByName1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        assertTrue("User was found where they shouldn't have been", null == userService.getUserByUsername(testUser1.getUsername()));
    }

    @Test
    public void crazyGetUserByName1() {
        assertTrue("User was found with input value null", null == userService.getUserByUsername(null));
    }

    /**
     * Checking if data can be updated within the database. This requires getting the object from the database with the
     * specific Id, then changing its data, then using that updated object in the update method
     * TODO: once validation is added, we will have to add in many more tests to make sure invalid update data cant be passed
     */
    @Test //Test if username can be updated (good)
    public void goodUpdateUserTest1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser1);
        //sets updates username
        String newUsername = "MadAtlas2";
        testUser1.setUsername(newUsername);
        userService.update(testUser1);
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByUsername(newUsername);
        assertTrue("Username wasnt changed", newUsername.equals(changedUser.getUsername()));
        userService.delete(changedUser);
    }

    @Test
    public void goodUpdateUserTest2() {
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        //sets updates username
        String newUsername = "MadAtlas3";
        User user = userService.getUserByUsername(testUser2.getUsername());
        user.setUsername(newUsername);
        userService.update(user);
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByUsername(newUsername);
        assertTrue("Username wasnt changed", newUsername.equals(changedUser.getUsername()));
        userService.delete(changedUser);
    }

    @Test
    public void goodUpdateUserTest3() {
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        //sets updates username
        String newPhonenumber = "4066604455";
        testUser2.setPhoneNumber(newPhonenumber);
        userService.update(testUser2);
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByUsername(testUser2.getUsername());
        assertTrue("Phone number wasn't changed", newPhonenumber.equals(changedUser.getPhoneNumber()));
    }

    @Test
    public void badUpdateUserTest1() {
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        //sets updates username
        Long newId = (long) 40666;
        testUser2.setId(newId);
        assertFalse("User was updated, when they shouldn't have", userService.update(testUser2));
    }

    @Test
    public void crazyUpdateUserTest1() {
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
    public void goodUpdateSymbolTest1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        userService.add(testUser1);
        //sets updates username
        String ticker = "EUC";
        Symbol symbol = new Symbol(ticker);
        symbol.setUserId(testUser1);//I shouldnt have to add the user to the symbol
        testUser1.addSymbol(symbol);
        userService.update(testUser1);
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByUsername(testUser1.getUsername());
        Symbol[] setArray = changedUser.getSymbol().toArray(new Symbol[changedUser.getSymbol().size()]);
        List<Symbol> setList = new ArrayList<>(changedUser.getSymbol());
        userService.delete(changedUser);
        assertTrue("Returned ticker and set ticker wasn't the same", setList.get(0).getTicker().equals(ticker));
    }

    @Test
    public void goodUpdateSymbolTest2() {
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        userService.add(testUser2);
        //sets updates username
        String ticker = "EUC";
        Symbol symbol = new Symbol(ticker);
        symbol.setUserId(testUser2);//I shouldnt have to add the user to the symbol
        testUser2.addSymbol(symbol);
        userService.update(testUser2);//<--This is the main function that we are testing
        //retrieving the user and seeing if the name was saved
        User changedUser = userService.getUserByUsername(testUser2.getUsername());
        Symbol[] setArray = changedUser.getSymbol().toArray(new Symbol[changedUser.getSymbol().size()]);
        List<Symbol> setList = new ArrayList<>(changedUser.getSymbol());
        userService.delete(changedUser);
        assertTrue("Returned ticker and set ticker wasn't the same", setList.get(0).getTicker().equals(ticker));
    }

    @Test
    public void goodAddSymbolInvalidSymbol1() {
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        final String symbol = "UECCC";
        assertTrue("User wasn't added to database", userService.add(testUser2));
        assertFalse("Input symbol was valid, should have been invalid", userService.addSymbolToUser(testUser2, symbol));
    }

    @Test
    public void goodAddSymbolInvalidSymbol2() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        final String symbol = "APPLE";
        assertTrue("User wasn't added to database", userService.add(testUser1));
        assertFalse("Input symbol was valid, should have been invalid", userService.addSymbolToUser(testUser1, symbol));
    }

    @Test
    public void goodAddSymbolValidSymbol1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        final String symbol = "AAPL";
        assertTrue("User wasn't added to database", userService.add(testUser1));
        assertTrue("Input symbol was invalid, should have been valid", userService.addSymbolToUser(testUser1, symbol));
    }

    @Test
    public void goodAddSymbolValidSymbol2() {
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        final String symbol = "UEC";
        assertTrue("User wasn't added to database", userService.add(testUser2));
        assertTrue("Input symbol was invalid, should have been valid", userService.addSymbolToUser(testUser2, symbol));
    }

    @Test
    public void goodAddSymbolUserNotExist1() {
        final String symbol = "UEC";
        assertFalse("Input symbol was valid, should have been invalid", userService.addSymbolToUser(null, symbol));
    }

    @Test
    public void goodAddSymbolUserNotExist2() {
        final String symbol = "AAPL";
        assertFalse("Input symbol was valid, should have been invalid", userService.addSymbolToUser(null, symbol));
    }

    @Test
    public void goodAddSymbolPreviouslySaved1() {
        final User testUser1 = new User
                ("CabbageMan", "password", "riblueliany@docomo.ne.jp", "4065556666");
        final String symbol = "AAPL";
        assertTrue("User wasn't added to database", userService.add(testUser1));
        assertTrue("Input symbol and or user was invalid, should have been valid", userService.addSymbolToUser(testUser1, symbol));
        assertTrue("Input symbol and or user was invalid, second add should have returned true", userService.addSymbolToUser(testUser1, symbol));

        List<String> dupedTickers = new ArrayList<>();
        for (Symbol symbolSaved : testUser1.getSymbol()) {
            if (symbolSaved.getTicker().equalsIgnoreCase(symbol)) {
                dupedTickers.add(symbolSaved.getTicker());
            }
        }
        assertTrue("Ticker was duplicated, found more than one instance", dupedTickers.size() == 1);
    }

    @Test
    public void goodAddSymbolPreviouslySaved2() {
        final User testUser2 = new User
                ("MonkeyKing", "PassW0rd", "一个@阿育王。印度", "861065529988");
        final String symbol = "UEC";
        assertTrue("User wasn't added to database", userService.add(testUser2));
        assertTrue("Input symbol and or user was invalid, should have been invalid", userService.addSymbolToUser(testUser2, symbol));
        assertTrue("Input symbol and or user was invalid, second add should have returned true", userService.addSymbolToUser(testUser2, symbol));

        List<String> dupedTickers = new ArrayList<>();
        for (Symbol symbolSaved : testUser2.getSymbol()) {
            if (symbolSaved.getTicker().equalsIgnoreCase(symbol)) {
                dupedTickers.add(symbolSaved.getTicker());
            }
        }
        assertTrue("Ticker was duplicated, non-one amount of instances found", dupedTickers.size() == 1);
    }

    @Test
    public void crazyNullInputs() {
        final User testUser1 = new User();
        assertFalse("Input symbol was invalid, should have been valid", userService.addSymbolToUser(testUser1, null));
    }
}
