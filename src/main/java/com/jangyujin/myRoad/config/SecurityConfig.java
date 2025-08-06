package com.jangyujin.myRoad.config;


import com.jangyujin.myRoad.config.jwt.JwtAuthenticationFilter;
import com.jangyujin.myRoad.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/sign-in").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtTokenProvider jwtTokenProvider;
//    //private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
//   // private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
//
////    @Value("${base.url}")
////    private String baseUrl;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                //.cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 관련 설정
//                .authorizeHttpRequests(authorize -> authorize
////                        .requestMatchers("/login", "/", "/error","/join", "/user/find-id", "/email/api/send-code", "/auth/api/").authenticated()
//                                .requestMatchers("/login", "/", "/error", "/email/api/", "/api/auth/sign-in").authenticated()
//                                .anyRequest().permitAll()
//                )
////                .formLogin(auth -> auth
////                        .usernameParameter("username")
////                        .passwordParameter("password")
////                        .loginPage("/loginForm")
////                        .loginProcessingUrl("/loginProcess")
////                        .successHandler(jwtAuthenticationSuccessHandler)
////                )
//                //.userDetailsService(principalDetailsService)
//                .formLogin(login -> login.disable())
//                .httpBasic(basic -> basic.disable())
////                .oauth2Login(oauth2 -> oauth2
////                        //.loginPage("/loginForm")
////                        .userInfoEndpoint(userInfo -> userInfo
////                                .userService(principalOauth2UserService)
////                        )
////                        .successHandler(jwtAuthenticationSuccessHandler)
////                        .failureHandler(jwtAuthenticationFailureHandler)
////                )
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // BCrypt Encoder 사용
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//
//
//}
