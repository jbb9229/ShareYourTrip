package com.shareyourtrip.web.system;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WelcomeControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void welcomePage() {
        //when
        String body = this.template.getForObject("/", String.class);

        //then
        assertThat(body).isNotNull();
    }

}