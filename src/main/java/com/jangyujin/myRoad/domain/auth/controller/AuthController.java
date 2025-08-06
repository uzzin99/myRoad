package com.jangyujin.myRoad.domain.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/sign-in")
    public String signIn() {
        return "pages/signIn";
    }
}
