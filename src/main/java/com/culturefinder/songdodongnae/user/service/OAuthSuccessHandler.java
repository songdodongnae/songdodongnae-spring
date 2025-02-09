package com.culturefinder.songdodongnae.user.service;

import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String provider = oauthToken.getAuthorizedClientRegistrationId();
        String providerId = extractProviderId(authentication);

        Optional<User> user = userRepository.findByProviderIdAndProvider(providerId, provider);

        user.ifPresent(u -> loginSuccess(request, response, u.getId()));

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://www.songdodongnae.n-e.kr/");
        builder.path("auth");
        response.sendRedirect(builder.build().toString());
    }

    private void loginSuccess(HttpServletRequest request, HttpServletResponse response, Long id) {
        String accessToken = jwtService.createAccessToken(id);
        String refreshToken = jwtService.createRefreshToken();

        ResponseCookie accessCookie = getRefreshTokenCookie("Authorization", accessToken);
        ResponseCookie refreshCookie = getRefreshTokenCookie("Authorization-refresh", refreshToken);

        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(id, refreshToken);
    }

    private ResponseCookie getRefreshTokenCookie(String key, String token) {
        ResponseCookie cookie = ResponseCookie.from(key, token)
                .maxAge(2000)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None") // sameSite 정책을 None 으로 설정
                .build();
        return cookie;
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain("songdodongnae.n-e.kr");
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    private String extractProviderId(Authentication authentication) {
        return authentication.getName().replaceAll("[{}]|id=", "");
    }
}
