package com.jangyujin.myRoad.domain.auth.service;

import com.jangyujin.myRoad.config.jwt.JwtToken;
import com.jangyujin.myRoad.config.jwt.JwtTokenProvider;
import com.jangyujin.myRoad.domain.auth.dto.AuthDTO;
import com.jangyujin.myRoad.domain.auth.entity.User;
import com.jangyujin.myRoad.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {

//    private UserRepository userRepository;
//
//    public AuthService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인 처리 메서드
     * @param loginRequest - 로그인 요청 DTO
     * @return true - 로그인 성공, false - 실패
     */
//    public boolean login(AuthDTO.LoginRequest loginRequest) {
//        String email = loginRequest.getUsername();
//        String password = loginRequest.getPassword();
//
//        // 1. 유저가 존재하는지 조회 (가정)
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            return false; // 유저 없음
//        }
//
//        // 2. 비밀번호 검증 (단순 비교, 실제는 암호화 검증 필요)
//        if (!user.getPassword().equals(password)) {
//            return false; // 비밀번호 불일치
//        }
//
//        return true; // 로그인 성공
//    }

    @Transactional
    public JwtToken signIn(String username, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }
}
