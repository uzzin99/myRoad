package com.jangyujin.myRoad.domain.auth.dto;

import lombok.Data;


public class AuthDTO {


//    @Data
//    public static class LoginRequest {
//        private String email;
//        private String password;
//    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
