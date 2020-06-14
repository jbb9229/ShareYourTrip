package com.shareyourtrip.web.system;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WelcomeMessage {

    private final String message;
    private final int amount;

}
