package com.shareyourtrip.web.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome Our Homepage";
    }

    @GetMapping("/welcomemessage")
    public WelcomeMessage reactiveMessage(@RequestParam("message") String message,
                                          @RequestParam("amount") int amount) {
        return new WelcomeMessage(message, amount);
    }

}
