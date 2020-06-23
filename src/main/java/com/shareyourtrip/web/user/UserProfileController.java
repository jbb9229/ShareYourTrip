package com.shareyourtrip.web.user;

import com.shareyourtrip.web.config.auth.LoginUser;
import com.shareyourtrip.web.config.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {

    @GetMapping("/user/profile")
    public String getUserProfile(@LoginUser SessionUser user
                                , Model model) {
        model.addAttribute("user", user);
        return "user/profile";
    }

}
