package com.culturefinder.songdodongnae.user.service;

import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:3000");
        builder.path("auth");
        response.sendRedirect(builder.build().toString());
    }

    private void loginSuccess(HttpServletRequest request, HttpServletResponse response, Long id) {
        String accessToken = jwtService.createAccessToken(id);
        String refreshToken = jwtService.createRefreshToken();

        Cookie accessTokenCookie = createCookie("Authorization", accessToken);
        Cookie refreshTokenCookie = createCookie("Authorization-refresh", refreshToken);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(id, refreshToken);
    }

    private Cookie createCookie(String key, String Value) {
        Cookie cookie = new Cookie(key, Value);
        cookie.setDomain("localhost"); // 로컬 테스트 코드
        cookie.setPath("/");
        return cookie;
    }

    private String extractProviderId(Authentication authentication) {
        return authentication.getName().replaceAll("[{}]|id=", "");
    }
}
