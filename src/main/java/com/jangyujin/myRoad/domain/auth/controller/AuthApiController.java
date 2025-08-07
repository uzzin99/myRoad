package com.jangyujin.myRoad.domain.auth.controller;

import com.jangyujin.myRoad.config.jwt.JwtToken;
import com.jangyujin.myRoad.domain.auth.dto.AuthDTO;
import com.jangyujin.myRoad.domain.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthApiController {

    private final AuthService authService;

    //생성자 주입
    public AuthApiController(AuthService authService) {
        this.authService = authService;
    }

//    @PostMapping("/sign-in")
//    public ResponseEntity<String> login(@RequestBody AuthDTO.LoginRequest loginRequest) {
//        boolean success = authService.login(loginRequest);
//        if(success) {
//            return ResponseEntity.ok("로그인 성공");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
//        }
//    }

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody AuthDTO.LoginRequest  LoginRequest) {

        String username = LoginRequest.getUsername();
        String password = LoginRequest.getPassword();

        JwtToken jwtToken = authService.signIn(username, password);

        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return jwtToken;
    }
}
