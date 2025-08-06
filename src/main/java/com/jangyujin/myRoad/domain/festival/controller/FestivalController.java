package com.jangyujin.myRoad.domain.festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FestivalController {

    @GetMapping("/festival")
    public String festivalPage() {
        return "pages/festival";
    }
}
