package com.culturefinder.songdodongnae;

import com.culturefinder.songdodongnae.user.repository.UserRepository;
import com.culturefinder.songdodongnae.user.service.JwtAuthenticationProcessingFilter;
import com.culturefinder.songdodongnae.user.service.JwtService;
import com.culturefinder.songdodongnae.user.service.OAuthService;
import com.culturefinder.songdodongnae.user.service.OAuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthService oAuthService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final String[] whiteList = {
            "/api/login",
            "/oauth2/**",
            "/admin/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/**", // 개발용 코드
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(whiteList).permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuthService))
                        .successHandler(oAuthSuccessHandler)
                )
                .addFilterBefore(jwtAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, userRepository);
    }
}
