package com.shareyourtrip.web.system;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = WelcomeController.class)
public class WelcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void printWelcomeMessage() throws Exception {
        String welcomeMessage = "Welcome Our Homepage";

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(welcomeMessage));
    }

    @Test
    public void printReactiveWelcomeMessage() throws Exception {
        String message = "Welcome Member";
        int amount = 2;

        mockMvc.perform(get("/welcomemessage")
                            .param("message", message)
                            .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(message)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}