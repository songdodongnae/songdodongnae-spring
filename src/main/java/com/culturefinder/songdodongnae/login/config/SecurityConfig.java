package com.culturefinder.songdodongnae.login.config;

import com.culturefinder.songdodongnae.login.service.OAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final OAuthService oAuthService;

    public SecurityConfig(OAuthService oAuthService){
        this.oAuthService = oAuthService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login", "/index.html", "/oauth2/**").permitAll()  // 로그인 페이지 및 OAuth 경로는 인증 없이 접근 가능
                        .anyRequest().authenticated()  // 그 외의 경로는 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuthService)
                        )
                );
        return http.build();
    }

}
