package com.midaswebserver.midasweb.models;

import com.midaswebserver.midasweb.models.User.Settings;
import com.midaswebserver.midasweb.models.User.User;
import com.midaswebserver.midasweb.services.LoginServiceImp;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Stack;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class SettingsTest {
    private static final Logger log = LoggerFactory.getLogger(SettingsTest.class);
    User testUser = new User();
    Settings userSettings = new Settings();
    @Test
    public void SetSettingsTest(){
        Stack<String> testStack = new Stack<String>();
        testStack.push("AAPL");
        testStack.push("UEC");
        log.debug("String of test stack is: '{}'", testStack.toString());
        userSettings.setTickers(testStack);
        Stack returnedStack = userSettings.getTickers();
        log.debug("String of returned stack is: '{}'", returnedStack.toString());
        assertTrue("Returned data is not equal to given data", returnedStack.equals(testStack));
    }
}
