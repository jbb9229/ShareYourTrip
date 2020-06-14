package com.shareyourtrip.web.system;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class WelcomeMessageTest {

    @Test
    public void lombokTest() {
        //given
        String message = "Welcome";
        int amount = 1;

        //when
        WelcomeMessage welcomeMessage = new WelcomeMessage(message, amount);

        //then
        assertThat(welcomeMessage.getMessage()).isEqualTo(message);
        assertThat(welcomeMessage.getAmount()).isEqualTo(amount);
    }

}