package com.shareyourtrip.web.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/layout")
@Controller
public class LayoutController {

    @GetMapping("/headercheck")
    public String getHeader() {
        return "layout/header";
    }

    @GetMapping("/footercheck")
    public String getFooter() {
        return "layout/footer";
    }

    @GetMapping("/carouselcheck")
    public String getCarousel() {
        return "layout/carousel";
    }

}
