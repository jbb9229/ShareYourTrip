package com.shareyourtrip.web.config.auth.dto;

import com.shareyourtrip.web.user.User;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

    private Long userNum;
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.userNum = user.getUserNum();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
