package com.midaswebserver.midasweb.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    //Will include tests for:
    // Get  /login
    // Post /login (I don't Know how I will do this one
    // Get  /loginFailure
    // Get  /loginSuccess
    @Test
    public void ValidateLogin() throws Exception {//this needs to be a more dynamic unit test
        mockMvc.perform(get("/login")).andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void ValidateLoginFailure() throws Exception{
        mockMvc.perform(get("/loginFailure")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void ValidateLoginSuccess() throws Exception{
        mockMvc.perform(get("/loginSuccess")).andDo(print())
                .andExpect(status().isOk());
    }
}