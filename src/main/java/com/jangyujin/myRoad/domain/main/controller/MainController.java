package com.jangyujin.myRoad.domain.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/", "/main"})
    public String mainPage(Model model) {
        return "pages/main";
    }

    @GetMapping("/profile")
    public String profilePage() {
        return "pages/profile";
    }


}
