package com.shareyourtrip.web.system;

import com.shareyourtrip.web.config.auth.LoginUser;
import com.shareyourtrip.web.config.auth.dto.SessionUser;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class WelcomeController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String welcome(Model model,
                        // 세션에서 가져온 정보를 담은 SessionUser
                        @LoginUser SessionUser user) {

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "welcome";
    }

}
