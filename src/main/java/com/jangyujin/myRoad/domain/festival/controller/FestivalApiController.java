package com.jangyujin.myRoad.domain.festival.controller;

import com.jangyujin.myRoad.domain.festival.dto.FestivalDTO;
import com.jangyujin.myRoad.domain.festival.dto.FestivalPagedResult;
import com.jangyujin.myRoad.domain.festival.service.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/festival")
public class FestivalApiController {

    @Autowired
    private FestivalService festivalService;

//    @GetMapping("/list")
//    public FestivalDTO getFestivalList() {
//        return festivalService.getFestivals();
//    }

    @GetMapping("/list")
    public ResponseEntity<List<FestivalDTO.Response.Body.Items.Item>> getFestivals() {
        return festivalService.getFestivals();
    }
}
