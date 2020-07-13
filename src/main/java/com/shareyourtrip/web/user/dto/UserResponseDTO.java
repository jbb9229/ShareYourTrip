package com.shareyourtrip.web.user.dto;

import lombok.Getter;

@Getter
public class UserResponseDTO {

    private Long userNum;
    private String email;
    private String name;
    private String picture;

    public UserResponseDTO(Long userNum, String email, String name, String picture) {
        this.userNum = userNum;
        this.email = email;
        this.name = name;
        this.picture = "https://shareyourtrip-static-resource.s3.ap-northeast-2.amazonaws.com/images/icon/user.png";
        if (picture != null) {
            this.picture = picture;
        }
    }
}
