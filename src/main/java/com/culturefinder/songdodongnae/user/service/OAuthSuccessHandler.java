package com.culturefinder.songdodongnae.user.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // [ 웹 서버 uri + 토큰 ] 으로 리다이렉트

        // 테스트 코드
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:3000");
        builder.path("auth");
        builder.queryParam("accessToken", "abcdefg");
        response.sendRedirect(builder.build().toString());
    }
}
